package uz.ilmnajot.registration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.ilmnajot.registration.service.impl.CustomUserDetailsImpl;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsImpl customUserDetails;

    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailsImpl customUserDetails) {
        this.jwtProvider = jwtProvider;
        this.customUserDetails = customUserDetails;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            String usernameFromToken = jwtProvider.getUsernameFromToken(token);
            if (usernameFromToken!=null){
                UserDetails userDetails = customUserDetails.loadUserByUsername(usernameFromToken);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
            }
        }
filterChain.doFilter(request, response);
    }
}

