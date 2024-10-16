package com.upgrade.resto.service;

import com.upgrade.resto.dto.response.JwtClaims;
import com.upgrade.resto.entity.Customer;
import com.upgrade.resto.entity.Restaurant;
import com.upgrade.resto.entity.Waiter;

public interface JwtService {

    String generateWaiterToken(Waiter waiter);

    boolean verifyJwtToken(String token);

    JwtClaims getClaimsByToken(String token);
}
