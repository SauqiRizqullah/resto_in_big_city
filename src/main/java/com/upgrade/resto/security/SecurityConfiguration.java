package com.upgrade.resto.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
//						.httpBasic(new Customizer<HttpBasicConfigurer<HttpSecurity>>() {
//							@Override
//							public void customize(HttpBasicConfigurer<HttpSecurity> httpSecurityHttpBasicConfigurer) {
//
//							}
//						}); // belum jadi lamda
//						.httpBasic(security -> {
//							security.disable();
//						});
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // disable cookie
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
//                        .requestMatchers("/api/v1/restoAuth/**").permitAll()
                        .requestMatchers("/api/v1/waiterAuth/register").hasAnyRole("WAITER")
                        .requestMatchers("/api/v1/waiterAuth/login").permitAll()
//                        .requestMatchers("/api/v1/customerAuth/register").hasAnyRole("WAITER")
//                        .requestMatchers("/api/v1/customerAuth/login").permitAll()
                        .requestMatchers("api/v1/**").hasAnyRole("WAITER")
                        .requestMatchers("api/v1/customers/**").hasAnyRole("WAITER")
                        .requestMatchers("api/v1/waiters/**").hasAnyRole("WAITER")
                        .requestMatchers("api/v1/transactions/**").hasAnyRole("WAITER")
                        .requestMatchers("api/v1/trades/**").hasAnyRole("WAITER")
//                        .requestMatchers("api/v1/menus/**").hasAnyRole("CAPTAIN")
//                        .requestMatchers("api/v1/roles/**").hasAnyRole("CAPTAIN")
//                        .requestMatchers("api/v1/memberships/**").hasAnyRole("CAPTAIN")
//                        .requestMatchers("api/v1/bonus/**").hasAnyRole("CAPTAIN")
//                        .requestMatchers("api/v1/resto/**").hasAnyRole("CAPTAIN")
                        .requestMatchers(HttpMethod.GET, "api/v1/**").permitAll()
                        .anyRequest().authenticated()
                )
                // menambahkan filter dari AuthenticationFilter
                .addFilterBefore(
                        authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();

    }
}
