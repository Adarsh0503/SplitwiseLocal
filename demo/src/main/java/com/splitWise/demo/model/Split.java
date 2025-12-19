package com.splitWise.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Split {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Expense expense;   // 👈 ADD THIS

    private BigDecimal amount;

    public Split() {}

    public Split(User user, Expense expense, BigDecimal amount) {
        this.user = user;
        this.expense = expense;
        this.amount = amount;
    }

    // getters & setters
}
