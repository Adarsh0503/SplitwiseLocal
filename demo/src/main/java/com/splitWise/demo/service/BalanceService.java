package com.splitWise.demo.service;

import com.splitWise.demo.dto.Payment;
import com.splitWise.demo.exception.ResourceNotFoundException;
import com.splitWise.demo.model.Expense;
import com.splitWise.demo.model.Split;
import com.splitWise.demo.repository.ExpenseRepository;
import com.splitWise.demo.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class BalanceService {

    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;

    public BalanceService(ExpenseRepository expenseRepository, GroupRepository groupRepository) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }

    public List<Payment> simplifyGroupBalances(Long groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

        List<Expense> expenses = expenseRepository.findByGroupIdAndSettledFalse(groupId);

        // Step 1: Compute net balance per user
        // Positive = owed money, Negative = owes money
        Map<Long, BigDecimal> netBalance = new HashMap<>();

        for (Expense expense : expenses) {
            Long paidById = expense.getPaidBy().getId();
            BigDecimal total = expense.getTotalAmount();

            // Payer is owed the full amount
            netBalance.merge(paidById, total, BigDecimal::add);

            // Each split participant owes their share
            for (Split split : expense.getSplits()) {
                Long userId = split.getUser().getId();
                netBalance.merge(userId, split.getAmount().negate(), BigDecimal::add);
            }
        }

        // Step 2: Separate into creditors (positive) and debtors (negative)
        // Max-heap for creditors (highest credit first)
        PriorityQueue<long[]> creditors = new PriorityQueue<>(
                (a, b) -> Long.compare(b[1], a[1]));
        // Min-heap for debtors (most negative first)
        PriorityQueue<long[]> debtors = new PriorityQueue<>(
                Comparator.comparingLong(a -> a[1]));

        for (Map.Entry<Long, BigDecimal> entry : netBalance.entrySet()) {
            // Convert to cents to avoid floating point issues in comparisons
            long cents = entry.getValue().multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_UP).longValue();
            if (cents > 0) {
                creditors.offer(new long[]{entry.getKey(), cents});
            } else if (cents < 0) {
                debtors.offer(new long[]{entry.getKey(), cents});
            }
        }

        // Step 3: Greedy settlement — always match largest creditor with largest debtor
        List<Payment> payments = new ArrayList<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()) {
            long[] creditor = creditors.poll();
            long[] debtor = debtors.poll();

            long settle = Math.min(creditor[1], -debtor[1]);

            BigDecimal amount = BigDecimal.valueOf(settle)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            payments.add(new Payment(debtor[0], creditor[0], amount));

            creditor[1] -= settle;
            debtor[1] += settle;

            if (creditor[1] > 0) creditors.offer(creditor);
            if (debtor[1] < 0) debtors.offer(debtor);
        }

        return payments;
    }
}
