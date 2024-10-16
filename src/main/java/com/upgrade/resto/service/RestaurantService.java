package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.RestaurantRequest;
import com.upgrade.resto.dto.request.SearchRestaurantRequest;
import com.upgrade.resto.dto.response.RestaurantResponse;
import com.upgrade.resto.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RestaurantService{

    RestaurantResponse create(RestaurantRequest restaurantRequest);

    RestaurantResponse getById(String restoId);

    Page<RestaurantResponse> getAll(SearchRestaurantRequest searchRestaurantRequest);

    String updateById(String restoId, String contact);

    String deleteById(String restoId);

    long count();
}
