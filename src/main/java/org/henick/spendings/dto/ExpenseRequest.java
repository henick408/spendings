package org.henick.spendings.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    private Double amount;
    private String name;
    private String description;
    private String paymentMethod;
    private Long categoryId;

}
