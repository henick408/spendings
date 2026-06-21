package org.henick.spendings.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Data
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Expense() {}

    public Expense(Long id, Double amount, String name, String description, PaymentMethod paymentMethod, Category category) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }

    public Expense(Double amount, String name, String description, PaymentMethod paymentMethod, Category category) {
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.category = category;
    }


}
