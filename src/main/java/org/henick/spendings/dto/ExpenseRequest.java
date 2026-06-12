package org.henick.spendings.dto;

import lombok.*;
import org.henick.spendings.model.PaymentMethod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    private Double amount;
    private String name;
    private String description;
    private PaymentMethod paymentMethod;
    private Long categoryId;

}
