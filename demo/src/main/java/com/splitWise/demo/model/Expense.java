package com.splitWise.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SplitType splitType;

    @ManyToOne
    @JoinColumn(name = "paid_by_user_id", nullable = false)
    private User paidBy;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Split> splits;

    private boolean settled = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Expense() {}

    public Long getId() { return id; }
    public String getDescription() { return description; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public SplitType getSplitType() { return splitType; }
    public User getPaidBy() { return paidBy; }
    public Group getGroup() { return group; }
    public List<Split> getSplits() { return splits; }
    public boolean isSettled() { return settled; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setDescription(String description) { this.description = description; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setSplitType(SplitType splitType) { this.splitType = splitType; }
    public void setPaidBy(User paidBy) { this.paidBy = paidBy; }
    public void setGroup(Group group) { this.group = group; }
    public void setSplits(List<Split> splits) {
        this.splits = splits;
        if (splits != null) {
            for (Split s : splits) s.setExpense(this);
        }
    }
    public void setSettled(boolean settled) { this.settled = settled; }
}
