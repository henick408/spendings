package org.henick.spendings.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;
    private Double amount;
    private String name;
    private String description;
    private String paymentMethod;
    private CategoryResponse category;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExpenseResponse(
            Double amount,
            String name,
            String description,
            String paymentMethod,
            CategoryResponse category,
            Long userId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
            ) {
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.category = category;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
