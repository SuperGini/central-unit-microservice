package com.gini.security;

import com.gini.security.authentication.ValidationServiceAuthentification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String authorization = authentication.getName();


        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte [] decodeCredentials = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodeCredentials, StandardCharsets.UTF_8);

        String username = credentials.split(":")[0];
      //  String password = credentials.split(":")[1];

        UserDetails u = userDetailsService.loadUserByUsername(username);

                                        //this iz zeeeeeee password -> don't want this shit to go String pool
            if(passwordEncoder.matches(credentials.split(":")[1], u.getPassword())){
                //returnam un fully authenticated object
                return new ValidationServiceAuthentification(username, null, u.getAuthorities());
            }
        throw new BadCredentialsException("Error username/password incorrect");
    }


    @Override
    public boolean supports(Class<?> authentication) { //type of Authentication
        return ValidationServiceAuthentification.class.equals(authentication); // acest tip de autentificare e folosit de basic auth filter
    }
}
