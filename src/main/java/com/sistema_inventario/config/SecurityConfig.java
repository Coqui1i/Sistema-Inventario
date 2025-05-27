package com.sistema_inventario.config;

import com.sistema_inventario.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/bootstrap/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/lang").permitAll()
                .requestMatchers("/?lang=**").permitAll()
                .requestMatchers("/admin/users").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/home").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers("/categories/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers("/products/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .exceptionHandling((exception) -> exception
                .accessDeniedPage("/error/403")
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            );

        return http.build();
    }
}