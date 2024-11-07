package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.CustomerRequest;
import com.upgrade.resto.dto.request.MenuRequest;
import com.upgrade.resto.dto.request.SearchCustomerRequest;
import com.upgrade.resto.dto.request.SearchMenuRequest;
import com.upgrade.resto.dto.response.CommonResponse;
import com.upgrade.resto.dto.response.CustomerResponse;
import com.upgrade.resto.dto.response.MenuResponse;
import com.upgrade.resto.dto.response.PagingResponse;
import com.upgrade.resto.entity.Customer;
import com.upgrade.resto.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<CustomerResponse>> createCustomer (
            @RequestBody CustomerRequest customerRequest){

        // 1. Membuat objek menu dari service
        CustomerResponse newCustomer = customerService.create(customerRequest);

        // 2. Membuat objek CommonResponse untuk melakukan Response

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("You have created new customer succesfully!!!")
                .data(newCustomer)
                .build();

        // 3. Mengembalikan Response Entity

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping(path = APIUrl.PATH_VAR_CUSTOMER_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<Customer>> getCustomerById(
            @PathVariable String customerId
    ) {
        // 1. Membuat objek Menu Response
        Customer customer = customerService.getById(customerId);

        // 2. Membuat objek Common Response untuk mengisi data response
        CommonResponse<Customer> response = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message(customerId + "'s data was already retrieved")
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<Page<CustomerResponse>>> getAllMenusV2 (
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "menuId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "customerName", required = false) String customerName
    ){
        // 1. Membuat objek SearchMenuRequest untuk mencari Menu semuanya
        SearchCustomerRequest searchCustomerRequest = SearchCustomerRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .customerName(customerName)
                .build();

        // 2. Membuat objek Page Menu
        Page<CustomerResponse> allCustomers = customerService.getAll(searchCustomerRequest);

        // 3. Membuat objek paging
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(allCustomers.getPageable().getPageNumber() + 1)
                .size(allCustomers.getPageable().getPageSize())
                .totalPages(allCustomers.getTotalPages())
                .totalElements(allCustomers.getTotalElements())
                .hasNext(allCustomers.hasNext())
                .hasPrevious(allCustomers.hasPrevious())
                .build();

        // 4. Membuat objek Common Response untuk response
        CommonResponse<Page<CustomerResponse>> response = CommonResponse.<Page<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved all data successfully")
                .data(allCustomers)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIUrl.PATH_VAR_CUSTOMER_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> updateByMenuName(
            @PathVariable String customerId,
            @RequestParam(name = "phoneNo") String phoneNo
    ){

        // 1. Memanggil service untuk melakukan update data menu
        String dataUpdate = customerService.updateById(customerId, phoneNo);

        // 2. Membuat Common Response

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(customerId + "'s data has been updated successfully")
                .data(dataUpdate)
                .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping(path = APIUrl.PATH_VAR_CUSTOMER_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> deleteMenuById (
            @PathVariable String customerId
    ){
        // 1. Memanggil service untuk menghapus objek menu
        String dataDelete = customerService.deleteById(customerId);

        // 2. Membuat Common Response
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(customerId + "'s data has been deleted from databases")
                .data(dataDelete)
                .build();

        return ResponseEntity.ok(response);
    }
}
