package com.splitWise.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "splits")
public class Split {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    @JsonBackReference

    private Expense expense;

    @Column(nullable = false)
    private BigDecimal amount;

    public Split() {}

    public Split(User user, Expense expense, BigDecimal amount) {
        this.user = user;
        this.expense = expense;
        this.amount = amount;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Expense getExpense() {
        return expense;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
