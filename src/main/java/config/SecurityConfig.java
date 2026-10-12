
package com.ems.employee_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/departments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/departments/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/employees/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
