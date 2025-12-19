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

    @Enumerated(EnumType.STRING)
    private SplitType splitType;   // ✅ ADD HERE

    @ManyToOne
    @JoinColumn(name = "paid_by_user_id")
    private User paidBy;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<Split> splits;

    private boolean settled;

    public Expense() {}

    // getters and setters
}
