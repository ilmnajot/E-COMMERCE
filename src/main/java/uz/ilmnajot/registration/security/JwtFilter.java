package uz.ilmnajot.registration.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import uz.ilmnajot.registration.service.impl.CustomUserDetailsImpl;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsImpl customUserDetails;
    private final HandlerExceptionResolver resolver;

    public JwtFilter(
            JwtProvider jwtProvider,
            CustomUserDetailsImpl customUserDetails,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtProvider = jwtProvider;
        this.customUserDetails = customUserDetails;
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.replace("Bearer ", "");
                boolean validateToken = jwtProvider.validateToken(token);
                if(validateToken){
                    String username = jwtProvider.getUsernameFromToken(token);
                    UserDetails userDetails = customUserDetails.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
            }
            filterChain.doFilter(request, response);
    }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
    IllegalArgumentException | IOException | ServletException e){
        resolver.resolveException(request, response, null, e);
    }
}
}

