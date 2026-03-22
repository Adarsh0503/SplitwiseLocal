package com.splitWise.demo.dto;

import com.splitWise.demo.model.SplitType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

public class AddExpenseRequest {

    @NotNull(message = "Group ID is required")
    private Long groupId;

    @NotNull(message = "Paid by user ID is required")
    private Long paidByUserId;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Split type is required")
    private SplitType splitType;

    @NotEmpty(message = "Splits map cannot be empty")
    private Map<Long, BigDecimal> splits;

    private String description;

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public Long getPaidByUserId() { return paidByUserId; }
    public void setPaidByUserId(Long paidByUserId) { this.paidByUserId = paidByUserId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public SplitType getSplitType() { return splitType; }
    public void setSplitType(SplitType splitType) { this.splitType = splitType; }
    public Map<Long, BigDecimal> getSplits() { return splits; }
    public void setSplits(Map<Long, BigDecimal> splits) { this.splits = splits; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
