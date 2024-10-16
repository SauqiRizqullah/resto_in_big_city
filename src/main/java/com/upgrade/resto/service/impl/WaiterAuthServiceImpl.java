package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.WaiterLoginRequest;
import com.upgrade.resto.dto.request.WaiterRegisterRequest;
import com.upgrade.resto.dto.response.RestaurantResponse;
import com.upgrade.resto.dto.response.WaiterLoginResponse;
import com.upgrade.resto.dto.response.WaiterRegisterResponse;
import com.upgrade.resto.entity.Restaurant;
import com.upgrade.resto.entity.Role;
import com.upgrade.resto.entity.Waiter;
import com.upgrade.resto.repository.WaiterRepository;
import com.upgrade.resto.service.JwtService;
import com.upgrade.resto.service.RestaurantService;
import com.upgrade.resto.service.RoleService;
import com.upgrade.resto.service.WaiterAuthService;
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
public class WaiterAuthServiceImpl implements WaiterAuthService {

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final WaiterRepository waiterRepository;

    private final RestaurantService restaurantService;

    @Override
    public WaiterRegisterResponse registerAccount(WaiterRegisterRequest request) {

//        Restaurant restaurant = restaurantService.getByContext();
        RestaurantResponse restaurantResponse = restaurantService.getById(request.getRestoId());

        Restaurant resto = Restaurant.builder()
                .contact(restaurantResponse.getContact())
                .restoId(restaurantResponse.getRestoId())
                .city(restaurantResponse.getCity())
                .province(restaurantResponse.getProvince())
                .outlet(restaurantResponse.getOutlet())
                .build();

        Role waiterRole = roleService.getById("ROLE002");

        Waiter waiter = Waiter.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .waiterName(request.getWaiterName())
                .phoneNo(request.getPhoneNumber())
                .role(List.of(waiterRole))
                .restaurant(resto)
                .build();

        waiterRepository.saveAndFlush(waiter);

        return WaiterRegisterResponse.builder()
                .username(waiter.getUsername())
                .roles(waiter.getRole())
                .build();
    }

    @Override
    public WaiterLoginResponse login(WaiterLoginRequest loginRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authenticated =  authenticationManager.authenticate(authentication);

        Waiter waiter = (Waiter) authenticated.getPrincipal();
        String token = jwtService.generateWaiterToken(waiter);
        return WaiterLoginResponse
                .builder()
                .username(waiter.getUsername())
                .token(token)
                .roles(waiter.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
