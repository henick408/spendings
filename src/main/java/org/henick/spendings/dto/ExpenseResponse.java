package org.henick.spendings.dto;

import lombok.*;

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

    public ExpenseResponse(Double amount, String name, String description, String paymentMethod, CategoryResponse category) {
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }

}
