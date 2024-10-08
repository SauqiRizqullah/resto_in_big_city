package com.upgrade.resto.service;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.RestaurantLoginRequest;
import com.upgrade.resto.dto.request.RestaurantRegisterRequest;
import com.upgrade.resto.dto.response.RestaurantLoginResponse;
import com.upgrade.resto.dto.response.RestaurantRegisterResponse;

public interface RestaurantAuthService {
    RestaurantRegisterResponse register(RestaurantRegisterRequest registerRequest);
    RestaurantLoginResponse login(RestaurantLoginRequest loginRequest);
}
