package com.splitWise.demo.service;

import com.splitWise.demo.dto.Payment;
import com.splitWise.demo.model.Expense;
import com.splitWise.demo.model.Split;
import com.splitWise.demo.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BalanceService {

    private final ExpenseRepository expenseRepository;

    public BalanceService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Payment> simplifyGroupBalances(Long groupId) {

        List<Expense> expenses =
                expenseRepository.findByGroupIdAndSettledFalse(groupId);
    
        Map<Long, BigDecimal> netBalance = new HashMap<>();
    
        // 1️⃣ Calculate net balances
        for (Expense expense : expenses) {
            Long paidBy = expense.getPaidBy().getId();
            BigDecimal total = expense.getTotalAmount();
    
            netBalance.put(
                    paidBy,
                    netBalance.getOrDefault(paidBy, BigDecimal.ZERO).add(total)
            );
    
            for (Split split : expense.getSplits()) {
                Long userId = split.getUser().getId();
                netBalance.put(
                        userId,
                        netBalance.getOrDefault(userId, BigDecimal.ZERO)
                                .subtract(split.getAmount())
                );
            }
        }
    
        // 2️⃣ Priority queues
        PriorityQueue<Map.Entry<Long, BigDecimal>> creditors =
                new PriorityQueue<>((a, b) -> b.getValue().compareTo(a.getValue()));
    
        PriorityQueue<Map.Entry<Long, BigDecimal>> debtors =
                new PriorityQueue<>((a, b) -> a.getValue().compareTo(b.getValue()));
    
        for (Map.Entry<Long, BigDecimal> entry : netBalance.entrySet()) {
            if (entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(entry);
            } else if (entry.getValue().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(entry);
            }
        }
    
        // 3️⃣ Simplify payments
        List<Payment> payments = new ArrayList<>();
    
        while (!creditors.isEmpty() && !debtors.isEmpty()) {
    
            Map.Entry<Long, BigDecimal> creditor = creditors.poll();
            Map.Entry<Long, BigDecimal> debtor = debtors.poll();
    
            BigDecimal amount =
                    creditor.getValue().min(debtor.getValue().abs());
    
            payments.add(
                    new Payment(
                            debtor.getKey(),
                            creditor.getKey(),
                            amount
                    )
            );
    
            creditor.setValue(creditor.getValue().subtract(amount));
            debtor.setValue(debtor.getValue().add(amount));
    
            if (creditor.getValue().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(creditor);
            }
            if (debtor.getValue().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(debtor);
            }
        }
    
        return payments;
    }
    
}
