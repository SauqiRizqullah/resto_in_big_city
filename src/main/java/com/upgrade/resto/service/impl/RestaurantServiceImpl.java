package com.upgrade.resto.service.impl;

import com.upgrade.resto.entity.RestaurantAccount;
import com.upgrade.resto.repository.RestaurantAccountRepository;
import com.upgrade.resto.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantAccountRepository restaurantAccountRepository;


    @Override
    public RestaurantAccount getByUserId(String id) {
        return restaurantAccountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "restaurant account not found")
        );

    }

    @Override
    public RestaurantAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return restaurantAccountRepository.findByUsername(authentication.getPrincipal().toString()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "restaurant account found from context")
        );

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return restaurantAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

    }
}
