package com.skj.skjapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/employee/create",
                        "employee/manager",
                        "/employee/view-all"
                )
                .hasAnyAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers
                        (
                                "/",
                                "/shop",
                                "/shop/all-products",
                                "/shop/add-clothing",
                                "/shop/equipment-page",
                                "/shop/clothing-page",
                                "/shop/add-equipment",
                                "/messages"
                        )
                .hasAnyAuthority("ADMIN", "EMPLOYEE")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login/error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .build();
    }
}
