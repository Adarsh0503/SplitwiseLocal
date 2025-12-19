package com.splitWise.demo.repository;

import com.splitWise.demo.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Used by BalanceService
    List<Expense> findByGroupIdAndSettledFalse(Long groupId);
}
