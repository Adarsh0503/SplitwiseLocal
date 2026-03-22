package com.splitWise.demo.controller;

import com.splitWise.demo.dto.AddExpenseRequest;
import com.splitWise.demo.model.Expense;
import com.splitWise.demo.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense addExpense(@Valid @RequestBody AddExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @GetMapping("/group/{groupId}")
    public List<Expense> getExpensesByGroup(@PathVariable Long groupId) {
        return expenseService.getExpensesByGroup(groupId);
    }

    @PatchMapping("/{expenseId}/settle")
    public Expense settleExpense(@PathVariable Long expenseId) {
        return expenseService.settleExpense(expenseId);
    }
}
