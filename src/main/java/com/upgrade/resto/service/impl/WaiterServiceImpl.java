package com.upgrade.resto.service.impl;

import com.upgrade.resto.entity.Waiter;
import com.upgrade.resto.repository.WaiterRepository;
import com.upgrade.resto.service.WaiterService;
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
public class WaiterServiceImpl implements WaiterService {

    WaiterRepository waiterRepository;

    @Override
    public Waiter getByUserId(String waiterId) {
        return waiterRepository.findById(waiterId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter data not found")
        );
    }

    @Override
    public Waiter getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return waiterRepository.findByUsername(authentication.getPrincipal().toString()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Waiter data not found from context")
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return waiterRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found")
        );
    }
}
