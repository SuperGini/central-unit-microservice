package com.gini.security.filter;

import com.gini.security.authentication.ValidationServiceAuthentification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class BasicValidationServceFilter extends OncePerRequestFilter {

   // @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String authorization = request.getHeader("Authorization");

        if(authorization == null){
            throw new BadCredentialsException("Missing basic Authorization credentials");
        }

        Authentication authentication = new ValidationServiceAuthentification(authorization, null);
        Authentication fullyAuthenticated = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(fullyAuthenticated);
        filterChain.doFilter(request, response);

    }

}
