package com.splitWise.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal totalAmount;

    @ManyToOne
    private User paidBy;

    @ManyToOne
    private Group group;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Split> splits;

    private boolean settled;

    public Expense() {}

    // getters & setters
}
