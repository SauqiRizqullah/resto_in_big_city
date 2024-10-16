package com.upgrade.resto.service.impl;

import com.upgrade.resto.entity.UserAccount;
import com.upgrade.resto.repository.RestaurantAccountRepository;
import com.upgrade.resto.repository.UserAccountRepository;
import com.upgrade.resto.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount getByUserId(String id) {
        return userAccountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id account not found")
        );
    }

    @Override
    public UserAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userAccountRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User account not found in context")
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

    }
}
