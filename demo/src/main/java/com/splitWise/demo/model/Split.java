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

    private BigDecimal amount;

    public Split() {}

    public Split(User user, BigDecimal amount) {
        this.user = user;
        this.amount = amount;
    }

    // getters & setters
}
