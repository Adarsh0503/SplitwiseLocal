package com.splitWise.demo.service;

import com.splitWise.demo.dto.AddExpenseRequest;
import com.splitWise.demo.model.*;
import com.splitWise.demo.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository,
            GroupRepository groupRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Expense addExpense(AddExpenseRequest request) {

        User paidBy = userRepository.findById(request.getPaidByUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setTotalAmount(request.getTotalAmount());
        expense.setPaidBy(paidBy);
        expense.setGroup(group);
        expense.setSplitType(request.getSplitType());
        expense.setSettled(false);

        List<Split> splits = calculateSplits(
                expense,
                request.getSplitType(),
                request.getTotalAmount(),
                request.getSplits()
        );

        expense.setSplits(splits);

        return expenseRepository.save(expense);
    }

    private List<Split> calculateSplits(
            Expense expense,
            SplitType splitType,
            BigDecimal totalAmount,
            Map<Long, BigDecimal> inputSplits) {

        List<Split> splits = new ArrayList<>();

        if (splitType == SplitType.EQUAL) {

            BigDecimal share = totalAmount.divide(
                    BigDecimal.valueOf(inputSplits.size()),
                    2,
                    BigDecimal.ROUND_HALF_UP
            );

            for (Long userId : inputSplits.keySet()) {
                User user = userRepository.findById(userId)
                        .orElseThrow();
                splits.add(new Split(user, expense, share));
            }

        } else if (splitType == SplitType.EXACT) {

            for (Map.Entry<Long, BigDecimal> entry : inputSplits.entrySet()) {
                User user = userRepository.findById(entry.getKey())
                        .orElseThrow();
                splits.add(new Split(user, expense, entry.getValue()));
            }

        } else if (splitType == SplitType.PERCENTAGE) {

            for (Map.Entry<Long, BigDecimal> entry : inputSplits.entrySet()) {
                User user = userRepository.findById(entry.getKey())
                        .orElseThrow();

                BigDecimal amount = totalAmount
                        .multiply(entry.getValue())
                        .divide(BigDecimal.valueOf(100));

                splits.add(new Split(user, expense, amount));
            }
        }

        return splits;
    }
}
