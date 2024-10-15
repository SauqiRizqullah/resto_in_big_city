package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.WaiterLoginRequest;
import com.upgrade.resto.dto.request.WaiterRegisterRequest;
import com.upgrade.resto.dto.response.WaiterLoginResponse;
import com.upgrade.resto.dto.response.WaiterRegisterResponse;

public interface WaiterAuthService {
    WaiterRegisterResponse registerAccount(WaiterRegisterRequest request);
    WaiterLoginResponse login(WaiterLoginRequest request);
}
