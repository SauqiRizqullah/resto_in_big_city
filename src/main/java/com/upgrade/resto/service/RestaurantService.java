package com.upgrade.resto.service;

import com.upgrade.resto.entity.RestaurantAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RestaurantService extends UserDetailsService {

    RestaurantAccount getByUserId(String id);

    RestaurantAccount getByContext();
}
