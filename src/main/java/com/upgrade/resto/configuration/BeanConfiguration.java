package com.upgrade.resto.configuration;

import com.upgrade.resto.security.AuthenticationFilter;
import com.upgrade.resto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UserService userService;
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Nonaktifkan CSRF karena kita menggunakan JWT
                .csrf(csrf -> csrf.disable())

                // Atur manajemen sesi ke stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Atur aturan otorisasi
                .authorizeHttpRequests(auth -> auth
                        // Izinkan akses ke endpoint login tanpa autentikasi
                        .requestMatchers("/api/v1/restoAuth/login").permitAll()

                        // Izinkan akses ke endpoint publik lainnya jika ada
                        // .requestMatchers("/public/**").permitAll()

                        // Semua permintaan lainnya memerlukan autentikasi
                        .anyRequest().authenticated()
                )

                // Atur penyedia autentikasi
                .authenticationProvider(authenticationProvider())

                // Tambahkan filter autentikasi sebelum filter UsernamePasswordAuthenticationFilter
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public org.springframework.security.authentication.dao.DaoAuthenticationProvider authenticationProvider() {
        org.springframework.security.authentication.dao.DaoAuthenticationProvider authProvider =
                new org.springframework.security.authentication.dao.DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
