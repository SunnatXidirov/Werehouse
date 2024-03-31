package uz.dev.foodstorage.companents;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import uz.dev.foodstorage.config.UserDetails;
import uz.dev.foodstorage.domain.User;


import java.util.Optional;

@Component
public class Auditing implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return Optional.ofNullable(user.user().getId());
    }
}
