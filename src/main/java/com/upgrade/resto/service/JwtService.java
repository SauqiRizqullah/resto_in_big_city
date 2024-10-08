package com.upgrade.resto.service;

import com.upgrade.resto.dto.response.JwtClaims;
import com.upgrade.resto.entity.Customer;
import com.upgrade.resto.entity.RestaurantAccount;
import com.upgrade.resto.entity.Waiter;

public interface JwtService {
    String generateRestaurantToken(RestaurantAccount restaurantAccount);

    String generateWaiterToken(Waiter waiter);

    String generateCustomerToken(Customer customer);

    boolean verifyJwtToken(String token);

    JwtClaims getClaimsByToken(String token);
}
