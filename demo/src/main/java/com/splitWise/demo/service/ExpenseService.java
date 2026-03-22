package com.splitWise.demo.service;

import com.splitWise.demo.dto.AddExpenseRequest;
import com.splitWise.demo.exception.ResourceNotFoundException;
import com.splitWise.demo.model.*;
import com.splitWise.demo.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository,
                          GroupRepository groupRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Expense addExpense(AddExpenseRequest request) {
        User paidBy = userRepository.findById(request.getPaidByUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + request.getPaidByUserId()));

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group not found with id: " + request.getGroupId()));

        // Validate paidBy user is a member of the group
        boolean isMember = group.getMembers().stream()
                .anyMatch(m -> m.getId().equals(paidBy.getId()));
        if (!isMember) {
            throw new IllegalArgumentException(
                    "User " + paidBy.getId() + " is not a member of group " + group.getId());
        }

        validateSplits(request);

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(request.getTotalAmount());
        expense.setPaidBy(paidBy);
        expense.setGroup(group);
        expense.setSplitType(request.getSplitType());
        expense.setSettled(false);

        List<Split> splits = calculateSplits(
                expense, request.getSplitType(),
                request.getTotalAmount(), request.getSplits());

        expense.setSplits(splits);
        return expenseRepository.save(expense);
    }

    public Expense settleExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with id: " + expenseId));

        if (expense.isSettled()) {
            throw new IllegalArgumentException("Expense " + expenseId + " is already settled");
        }

        expense.setSettled(true);
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByGroup(Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Group not found with id: " + groupId));
        return expenseRepository.findByGroupId(groupId);
    }

    // ── Validation ──────────────────────────────────────────────────────────

    private void validateSplits(AddExpenseRequest request) {
        BigDecimal total = request.getTotalAmount();
        Map<Long, BigDecimal> splits = request.getSplits();

        switch (request.getSplitType()) {
            case EXACT -> {
                BigDecimal sum = splits.values().stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (sum.compareTo(total) != 0) {
                    throw new IllegalArgumentException(
                            "EXACT splits must sum to total amount " + total + " but got " + sum);
                }
            }
            case PERCENTAGE -> {
                BigDecimal sum = splits.values().stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (sum.compareTo(new BigDecimal("100")) != 0) {
                    throw new IllegalArgumentException(
                            "PERCENTAGE splits must sum to 100 but got " + sum);
                }
            }
            case EQUAL -> { /* no extra validation needed */ }
        }
    }

    // ── Split calculation ────────────────────────────────────────────────────

    private List<Split> calculateSplits(Expense expense, SplitType splitType,
                                         BigDecimal totalAmount, Map<Long, BigDecimal> inputSplits) {
        List<Split> splits = new ArrayList<>();

        switch (splitType) {
            case EQUAL -> {
                BigDecimal share = totalAmount.divide(
                        BigDecimal.valueOf(inputSplits.size()), 2, RoundingMode.HALF_UP);
                for (Long userId : inputSplits.keySet()) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
                    splits.add(new Split(user, expense, share));
                }
            }
            case EXACT -> {
                for (Map.Entry<Long, BigDecimal> entry : inputSplits.entrySet()) {
                    User user = userRepository.findById(entry.getKey())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + entry.getKey()));
                    splits.add(new Split(user, expense, entry.getValue()));
                }
            }
            case PERCENTAGE -> {
                for (Map.Entry<Long, BigDecimal> entry : inputSplits.entrySet()) {
                    User user = userRepository.findById(entry.getKey())
                            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + entry.getKey()));
                    BigDecimal amount = totalAmount
                            .multiply(entry.getValue())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    splits.add(new Split(user, expense, amount));
                }
            }
        }

        return splits;
    }
}
