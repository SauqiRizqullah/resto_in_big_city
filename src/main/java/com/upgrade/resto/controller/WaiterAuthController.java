package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.RestaurantLoginRequest;
import com.upgrade.resto.dto.request.RestaurantRegisterRequest;
import com.upgrade.resto.dto.request.WaiterLoginRequest;
import com.upgrade.resto.dto.request.WaiterRegisterRequest;
import com.upgrade.resto.dto.response.*;
import com.upgrade.resto.service.WaiterAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.WAITER_AUTH)
public class WaiterAuthController {

    private final WaiterAuthService waiterAuthService;

    @PostMapping(path = "/register")
    public ResponseEntity<CommonResponse<WaiterRegisterResponse>> registerUser(@RequestBody WaiterRegisterRequest request){
        WaiterRegisterResponse register = waiterAuthService.registerAccount(request);
        CommonResponse<WaiterRegisterResponse> response = CommonResponse.<WaiterRegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("successfully save data")
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<WaiterLoginResponse>> login(@RequestBody WaiterLoginRequest request){
        WaiterLoginResponse loginResponse = waiterAuthService.login(request);
        CommonResponse<WaiterLoginResponse> response = CommonResponse.<WaiterLoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("login successfully")
                .data(loginResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
