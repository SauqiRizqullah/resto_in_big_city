package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.RestaurantRequest;
import com.upgrade.resto.dto.request.SearchRestaurantRequest;
import com.upgrade.resto.dto.response.RestaurantResponse;
import com.upgrade.resto.entity.Restaurant;
import com.upgrade.resto.repository.RestaurantRepository;
import com.upgrade.resto.service.RestaurantService;
import com.upgrade.resto.specification.RestaurantSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;


    @Override
    public RestaurantResponse create(RestaurantRequest restaurantRequest) {

        Restaurant restaurant = Restaurant.builder()
                .outlet(restaurantRequest.getOutlet())
                .city(restaurantRequest.getCity())
                .province(restaurantRequest.getProvince())
                .contact(restaurantRequest.getContact())
                .build();

        restaurantRepository.saveAndFlush(restaurant);



        return RestaurantResponse.builder()
                .restoId(restaurant.getRestoId())
                .outlet(restaurant.getOutlet())
                .city(restaurant.getCity())
                .province(restaurant.getProvince())
                .contact(restaurant.getContact())
                .build();
    }

    @Override
    public RestaurantResponse getById(String restoId) {

        Restaurant restaurant = restaurantRepository.findById(restoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id was not found!!!"));

        return RestaurantResponse.builder()
                .restoId(restaurant.getRestoId())
                .outlet(restaurant.getOutlet())
                .city(restaurant.getCity())
                .province(restaurant.getProvince())
                .contact(restaurant.getContact())
                .build();
    }

    @Override
    public Page<RestaurantResponse> getAll(SearchRestaurantRequest searchRestaurantRequest) {
        // 1. Ketika nilai halaman 0, maka buatlah menjadi 1
        if(searchRestaurantRequest.getPage() <= 0){
            searchRestaurantRequest.setPage(1);
        }

        // 2. Membuat validasi pengurutan halaman dengan kolom - kolom yang tersedia
        String validSortBy;
        if("city".equalsIgnoreCase(searchRestaurantRequest.getSortBy()) || "city".equalsIgnoreCase(searchRestaurantRequest.getSortBy())) {
            validSortBy = searchRestaurantRequest.getSortBy();
        } else {
            validSortBy = "restoId";
        }

        // 3. Membuat aturan sortBy dengan objek sort
        Sort sort = Sort.by(Sort.Direction.fromString(searchRestaurantRequest.getDirection()), validSortBy);

        //4. Membuat objek halaman Pageable untuk membuat sebuah halaman
        Pageable pageable = PageRequest.of(searchRestaurantRequest.getPage() - 1, searchRestaurantRequest.getSize(), sort);

        //5. Menyelaraskan dengan rule query dari specification milik objek itu
        Specification<Restaurant> specification = RestaurantSpecification.getSpecification(searchRestaurantRequest);
        return restaurantRepository.findAll(specification,pageable);
    }

    @Override
    public String updateById(String restoId, String contact) {
        Restaurant restaurant = restaurantRepository.findById(restoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id was not found!!!"));

        restaurant.setContact(contact);

        restaurantRepository.saveAndFlush(restaurant);
        return "Restaurant data has been updated";
    }

    @Override
    public String deleteById(String restoId) {

        restaurantRepository.deleteById(restoId);

        return restoId + "'s data has been deleted";
    }

    @Override
    public long count() {
        return 0;
    }
}
