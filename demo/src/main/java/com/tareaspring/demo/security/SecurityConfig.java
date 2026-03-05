package com.tareaspring.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/req/signup","/css/**","/js/**").permitAll();
                auth.anyRequest().authenticated();
                // hasRole("ADMIN")
            })
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/usuarios", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return httpSecurity.build();
    }
}
