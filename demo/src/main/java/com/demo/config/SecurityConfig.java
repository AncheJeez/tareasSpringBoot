package com.demo.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.demo.entidad.enumerado.Rol;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // H2 console iframes
    http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

    http.authorizeHttpRequests(auth -> auth
      .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
      .requestMatchers("/", "/saluda").permitAll()

      // Solo ADMIN para dashboard
      .requestMatchers("/admin/**").hasRole(Rol.ADMIN.toString())

      // H2 solo ADMIN
      .requestMatchers(PathRequest.toH2Console()).hasRole(Rol.ADMIN.toString())
      .requestMatchers("/h2-console/**", "/h2/**").hasRole(Rol.ADMIN.toString())

      // usuarios autenticados
      .anyRequest().authenticated()
    );

    http.csrf(csrf -> csrf
      .ignoringRequestMatchers(PathRequest.toH2Console())
      .ignoringRequestMatchers("/h2-console/**", "/h2/**")
    );

    // login form
    http.formLogin(form -> form
      .defaultSuccessUrl("/games", true)
      .permitAll()
    );

    // logout
    http.logout(logout -> logout.permitAll());

    return http.build();
  }
}