package org.henick.spendings.security;

import org.henick.spendings.model.User;
import org.henick.spendings.model.UserRole;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public AuthUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken
                || !(authentication.getPrincipal() instanceof AuthUser authUser)
        ) {
            throw new IllegalStateException("User is not authenticated");
        }
        return authUser;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public boolean hasRole(UserRole role) {
        return getCurrentUser().getRole() == role;
    }

    public UserRole getCurrentUserRole() {
        return getCurrentUser().getRole();
    }

    public boolean isCurrentUser(User user) {
        return getCurrentUser().getId().equals(user.getId());
    }

}
