package com.upgrade.resto.service;

import com.upgrade.resto.entity.Waiter;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface WaiterService extends UserDetailsService {

    Waiter getByUserId(String waiterId);

    Waiter getByContext();
}
