package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.RestaurantLoginRequest;
import com.upgrade.resto.dto.request.RestaurantRegisterRequest;
import com.upgrade.resto.dto.response.CommonResponse;
import com.upgrade.resto.dto.response.RestaurantLoginResponse;
import com.upgrade.resto.dto.response.RestaurantRegisterResponse;
import com.upgrade.resto.service.RestaurantAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.RESTO_AUTH)
public class RestaurantAuthController {

    private final RestaurantAuthService restaurantAuthService;

    @PostMapping(path = "/register")
    public ResponseEntity<CommonResponse<?>> registerUser(@RequestBody RestaurantRegisterRequest request){
        RestaurantRegisterResponse register = restaurantAuthService.register(request);
        CommonResponse<RestaurantRegisterResponse> response = CommonResponse.<RestaurantRegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("successfully save data")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<?>> login(@RequestBody RestaurantLoginRequest request){
        RestaurantLoginResponse loginResponse = restaurantAuthService.login(request);
        CommonResponse<RestaurantLoginResponse> response = CommonResponse.<RestaurantLoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("login successfully")
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
