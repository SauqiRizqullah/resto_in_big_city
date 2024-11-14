package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.CustomerRequest;
import com.upgrade.resto.dto.request.SearchCustomerRequest;
import com.upgrade.resto.dto.response.CustomerResponse;
import com.upgrade.resto.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest);

    Customer getById(String customerId);

    Page<CustomerResponse> getAll(SearchCustomerRequest searchCustomerRequest);

    String updateById(String customerId, String phoneNo);

    String deleteById(String customerId);

    Integer updatePoin(String customerId);
}
