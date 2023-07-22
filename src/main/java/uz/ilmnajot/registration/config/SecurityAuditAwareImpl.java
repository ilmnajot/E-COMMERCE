package uz.ilmnajot.registration.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.ilmnajot.registration.entity.CustomUserDetails;

import java.util.Optional;

public class SecurityAuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
            return Optional.of(user.getUsername());
        }
        return Optional.empty();
    }
}
