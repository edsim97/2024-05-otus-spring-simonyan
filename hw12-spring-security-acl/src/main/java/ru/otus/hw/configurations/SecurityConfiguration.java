package ru.otus.hw.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login").permitAll()
                .requestMatchers("/books/edit").hasAuthority("DBWriter")
                .requestMatchers(HttpMethod.GET).hasAuthority("DBReader")
                .requestMatchers(HttpMethod.POST).hasAuthority("DBWriter")
                .requestMatchers(HttpMethod.DELETE).hasAuthority("DBWriter")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .userDetailsService(userDetailsService)
            .rememberMe(configurer -> configurer.key("LibraryAuthToken").tokenValiditySeconds(600));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
