package com.splitWise.demo.controller;

import com.splitWise.demo.dto.AddExpenseRequest;
import com.splitWise.demo.model.Expense;
import com.splitWise.demo.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense addExpense(@RequestBody AddExpenseRequest request) {
        return expenseService.addExpense(request);
    }
}
