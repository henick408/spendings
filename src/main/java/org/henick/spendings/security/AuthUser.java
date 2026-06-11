package org.henick.spendings.security;

import lombok.Getter;
import lombok.Setter;
import org.henick.spendings.model.UserRole;

@Getter
@Setter
public class AuthUser {

    private Long id;
    private String email;
    private UserRole role;

    public AuthUser(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

}
