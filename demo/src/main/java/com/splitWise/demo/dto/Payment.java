package com.splitWise.demo.dto;

import java.math.BigDecimal;

public class Payment {

    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;

    public Payment(Long fromUserId, Long toUserId, BigDecimal amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }

    // getters
    public Long getFromUserId() { return fromUserId; }
public Long getToUserId() { return toUserId; }
public BigDecimal getAmount() { return amount; }

}
