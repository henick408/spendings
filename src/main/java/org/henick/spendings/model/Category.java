package org.henick.spendings.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @PrePersist
    @PreUpdate
    void normalize() {
        name = name.substring(0, 1).toUpperCase().concat(name.substring(1));
    }

    public Category(String name) {
        this.name = name;
    }

}
