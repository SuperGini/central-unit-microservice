package com.gini.config.security;

import com.gini.security.CustomAuthenticationProvider;
import com.gini.security.filter.BasicValidationServceFilter;
import com.gini.error.handler.security.RestAccessDeniedHandler;
import com.gini.error.handler.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;

    @Value("${user.roles}")
    private String roles;

    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Bean
    public UserDetailsService userDetailsService() {
        var usd = new InMemoryUserDetailsManager();

        var u = User.withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles(roles)
                .build();
        usd.createUser(u);
        return usd;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(new CustomAuthenticationProvider(userDetailsService(), passwordEncoder()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(new BasicValidationServceFilter(authenticationManagerBean()), BasicAuthenticationFilter.class);
        http
                .authorizeRequests()
                .mvcMatchers("/v1/**").hasAnyRole(roles)
                .anyRequest()
                .authenticated();
        http
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
