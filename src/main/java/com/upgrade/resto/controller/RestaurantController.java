package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.RestaurantRequest;
import com.upgrade.resto.dto.request.SearchRestaurantRequest;
import com.upgrade.resto.dto.response.CommonResponse;
import com.upgrade.resto.dto.response.PagingResponse;
import com.upgrade.resto.dto.response.RestaurantResponse;
import com.upgrade.resto.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.RESTO)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<RestaurantResponse>> createNewResto (
            @RequestBody RestaurantRequest restaurantRequest){

        // 1. Membuat objek menu dari service
        RestaurantResponse restaurantResponse = restaurantService.create(restaurantRequest);

        // 2. Membuat objek CommonResponse untuk melakukan Response

        CommonResponse<RestaurantResponse> response = CommonResponse.<RestaurantResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("You have created new resto succesfully!!!")
                .data(restaurantResponse)
                .build();

        // 3. Mengembalikan Response Entity

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping(path = APIUrl.PATH_VAR_RESTO_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<RestaurantResponse>> getById(
            @PathVariable String restoId
    ) {
        // 1. Membuat objek Menu Response
        RestaurantResponse restaurantResponse = restaurantService.getById(restoId);

        // 2. Membuat objek Common Response untuk mengisi data response
        CommonResponse<RestaurantResponse> response = CommonResponse.<RestaurantResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(restoId + "'s data was already retrieved")
                .data(restaurantResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<Page<RestaurantResponse>>> getAllMenusV2 (
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "restoId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "city", required = false) String city
    ){
        // 1. Membuat objek SearchMenuRequest untuk mencari Menu semuanya
        SearchRestaurantRequest searchRestaurantRequest = SearchRestaurantRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .city(city)
                .build();

        // 2. Membuat objek Page Menu
        Page<RestaurantResponse> restaurantResponses = restaurantService.getAll(searchRestaurantRequest);

        // 3. Membuat objek paging
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(restaurantResponses.getPageable().getPageNumber() + 1)
                .size(restaurantResponses.getPageable().getPageSize())
                .totalPages(restaurantResponses.getTotalPages())
                .totalElements(restaurantResponses.getTotalElements())
                .hasNext(restaurantResponses.hasNext())
                .hasPrevious(restaurantResponses.hasPrevious())
                .build();

        // 4. Membuat objek Common Response untuk response
        CommonResponse<Page<RestaurantResponse>> response = CommonResponse.<Page<RestaurantResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved all data successfully")
                .data(restaurantResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIUrl.PATH_VAR_RESTO_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> updateByMenuName(
            @PathVariable String menuId,
            @RequestParam(name = "contact") String newContact
    ){

        // 1. Memanggil service untuk melakukan update data menu
        String dataUpdate = restaurantService.updateById(menuId, newContact);

        // 2. Membuat Common Response

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(menuId + "'s contact has been updated successfully")
                .data(dataUpdate)
                .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping(path = APIUrl.PATH_VAR_RESTO_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> deleteMenuById (
            @PathVariable String restoId
    ){
        // 1. Memanggil service untuk menghapus objek menu
        String dataDelete = restaurantService.deleteById(restoId);

        // 2. Membuat Common Response
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(restoId + "'s data has been deleted from resto list")
                .data(dataDelete)
                .build();

        return ResponseEntity.ok(response);
    }
}
