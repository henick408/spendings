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
    private Long categoryId;

}
