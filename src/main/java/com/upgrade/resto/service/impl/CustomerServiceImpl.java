package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.CustomerRequest;
import com.upgrade.resto.dto.request.SearchCustomerRequest;
import com.upgrade.resto.dto.response.CustomerResponse;
import com.upgrade.resto.dto.response.MembershipResponse;
import com.upgrade.resto.entity.Customer;
import com.upgrade.resto.entity.Membership;
import com.upgrade.resto.entity.Menu;
import com.upgrade.resto.entity.Waiter;
import com.upgrade.resto.repository.CustomerRepository;
import com.upgrade.resto.repository.MembershipRepository;
import com.upgrade.resto.service.CustomerService;
import com.upgrade.resto.service.MembershipService;
import com.upgrade.resto.service.WaiterService;
import com.upgrade.resto.specification.CustomerSpecification;
import com.upgrade.resto.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final MembershipService membershipService;

    private final WaiterService waiterService;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {

        Waiter waiter = waiterService.getByContext();

        Membership none = membershipService.getById("MEMBER001");

        Customer customer = Customer.builder()
                .waiter(waiter)
                .restoId(waiter.getRestaurant().getRestoId())
                .customerName(customerRequest.getCustomerName())
                .phoneNo(customerRequest.getPhoneNo())
                .membership(none)
                .poin(0)
                .build();

        customerRepository.saveAndFlush(customer);

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .waiterId(customer.getWaiter().getWaiterId())
                .restoId(customer.getRestoId())
                .customerName(customer.getCustomerName())
                .phoneNo(customer.getPhoneNo())
                .membershipId(customer.getMembership().getMembershipId())
                .build();
    }

    @Override
    public Customer getById(String customerId) {

        return customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The data was not in database"));
    }

    @Override
    public Page<CustomerResponse> getAll(SearchCustomerRequest searchCustomerRequest) {

        // 1. Ketika nilai halaman 0, maka buatlah menjadi 1
        if(searchCustomerRequest.getPage() <= 0){
            searchCustomerRequest.setPage(1);
        }

        // 2. Membuat validasi pengurutan halaman dengan kolom - kolom yang tersedia
        String validSortBy;
        if("customerName".equalsIgnoreCase(searchCustomerRequest.getSortBy())) {
            validSortBy = searchCustomerRequest.getSortBy();
        } else {
            validSortBy = "customerId";
        }

        // 3. Membuat aturan sortBy dengan objek sort
        Sort sort = Sort.by(Sort.Direction.fromString(searchCustomerRequest.getDirection()), validSortBy);

        //4. Membuat objek halaman Pageable untuk membuat sebuah halaman
        Pageable pageable = PageRequest.of(searchCustomerRequest.getPage() - 1, searchCustomerRequest.getSize(), sort);

        //5. Menyelaraskan dengan rule query dari specification milik objek itu
        Specification<Customer> specification = CustomerSpecification.getSpecification(searchCustomerRequest);

        return customerRepository.findAll(specification, pageable);
    }

    @Override
    public String updateById(String customerId, String phoneNo) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The data was not in database"));

        customer.setPhoneNo(phoneNo);

        customerRepository.saveAndFlush(customer);

        return customerId + "'s data was successfully updated";
    }

    @Override
    public String deleteById(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The data was not in database"));
        customerRepository.deleteById(customer.getCustomerId());

        return customerId + "'s data was successfully deleted";
    }
}
