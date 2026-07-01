package org.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // for your custom POST /login flow
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**"                  ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // disable Spring's default login page
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
