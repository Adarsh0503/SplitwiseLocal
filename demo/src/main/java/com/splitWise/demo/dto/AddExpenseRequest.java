package com.splitWise.demo.dto;

import com.splitWise.demo.model.SplitType;
import java.math.BigDecimal;
import java.util.Map;

public class AddExpenseRequest {

    private Long groupId;
    private Long paidByUserId;
    private BigDecimal totalAmount;
    private SplitType splitType;
    private Map<Long, BigDecimal> splits;
    private String description;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getPaidByUserId() {
        return paidByUserId;
    }

    public void setPaidByUserId(Long paidByUserId) {
        this.paidByUserId = paidByUserId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }

    public Map<Long, BigDecimal> getSplits() {
        return splits;
    }

    public void setSplits(Map<Long, BigDecimal> splits) {
        this.splits = splits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
