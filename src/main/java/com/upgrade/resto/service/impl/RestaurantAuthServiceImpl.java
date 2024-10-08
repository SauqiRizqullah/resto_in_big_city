package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.RestaurantLoginRequest;
import com.upgrade.resto.dto.request.RestaurantRegisterRequest;
import com.upgrade.resto.dto.response.RestaurantLoginResponse;
import com.upgrade.resto.dto.response.RestaurantRegisterResponse;
import com.upgrade.resto.dto.response.RoleResponse;
import com.upgrade.resto.entity.RestaurantAccount;
import com.upgrade.resto.entity.Role;
import com.upgrade.resto.repository.RestaurantAccountRepository;
import com.upgrade.resto.service.JwtService;
import com.upgrade.resto.service.RestaurantAuthService;
import com.upgrade.resto.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantAuthServiceImpl implements RestaurantAuthService {

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RestaurantAccountRepository restaurantAccountRepository;

    @Override
    public RestaurantRegisterResponse register(RestaurantRegisterRequest registerRequest) {
        Role captain = roleService.getById("ROLE003");

        RestaurantAccount restaurantAccount = RestaurantAccount.builder()
                .outlet(registerRequest.getOutlet())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .city(registerRequest.getCity())
                .province(registerRequest.getProvince())
                .contact(registerRequest.getContact())
                .role(List.of(captain))
                .build();

        restaurantAccountRepository.saveAndFlush(restaurantAccount);

        return RestaurantRegisterResponse.builder()
                .username(restaurantAccount.getUsername())
                .roles(restaurantAccount.getRole())
                .build();
    }

    @Override
    public RestaurantLoginResponse login(RestaurantLoginRequest loginRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authenticated =  authenticationManager.authenticate(authentication);

        RestaurantAccount restaurantAccount = (RestaurantAccount) authenticated.getPrincipal();
        String token = jwtService.generateRestaurantToken(restaurantAccount);
        return RestaurantLoginResponse.builder()
                .token(token)
                .username(restaurantAccount.getUsername())
                .roles(restaurantAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
