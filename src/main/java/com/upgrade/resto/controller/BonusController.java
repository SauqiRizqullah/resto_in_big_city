package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.BonusRequest;
import com.upgrade.resto.dto.request.SearchBonusRequest;
import com.upgrade.resto.dto.response.BonusResponse;
import com.upgrade.resto.dto.response.CommonResponse;
import com.upgrade.resto.dto.response.PagingResponse;
import com.upgrade.resto.service.BonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(APIUrl.BONUS)
public class BonusController {

    private final BonusService bonusService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<BonusResponse>> createNewBonus (
            @RequestBody BonusRequest bonusRequest){

        // 1. Membuat objek menu dari service
        BonusResponse newBonus = bonusService.createNewBonus(bonusRequest);

        // 2. Membuat objek CommonResponse untuk melakukan Response

        CommonResponse<BonusResponse> response = CommonResponse.<BonusResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("You have created new bonus succesfully!!!")
                .data(newBonus)
                .build();

        // 3. Mengembalikan Response Entity

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping(path = APIUrl.PATH_VAR_BONUS_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<BonusResponse>> getBonusById(
            @PathVariable String bonusId
    ) {
        // 1. Membuat objek Menu Response
        BonusResponse bonus = bonusService.getById(bonusId);

        // 2. Membuat objek Common Response untuk mengisi data response
        CommonResponse<BonusResponse> response = CommonResponse.<BonusResponse >builder()
                .statusCode(HttpStatus.OK.value())
                .message(bonusId + "'s data was already retrieved")
                .data(bonus)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<Page<BonusResponse>>> getAllBonus (
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "bonusId") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "bonusName", required = false) String bonusName
    ){
        // 1. Membuat objek SearchMenuRequest untuk mencari Menu semuanya
        SearchBonusRequest searchBonusRequest = SearchBonusRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .bonusName(bonusName)
                .build();

        // 2. Membuat objek Page Menu
        Page<BonusResponse> allBonus = bonusService.getAllBonus(searchBonusRequest);

        // 3. Membuat objek paging
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(allBonus.getPageable().getPageNumber() + 1)
                .size(allBonus.getPageable().getPageSize())
                .totalPages(allBonus.getTotalPages())
                .totalElements(allBonus.getTotalElements())
                .hasNext(allBonus.hasNext())
                .hasPrevious(allBonus.hasPrevious())
                .build();

        // 4. Membuat objek Common Response untuk response
        CommonResponse<Page<BonusResponse>> response = CommonResponse.<Page<BonusResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Retrieved all data successfully")
                .data(allBonus)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIUrl.PATH_VAR_BONUS_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> updatePoinById(
            @PathVariable String bonusId,
            @RequestParam(name = "menuPrice") Integer newPoinCost
    ){

        // 1. Memanggil service untuk melakukan update data menu
        String dataUpdate = bonusService.updatePoinById(bonusId, newPoinCost);

        // 2. Membuat Common Response

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bonusId + "'s price has been updated successfully")
                .data(dataUpdate)
                .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping(path = APIUrl.PATH_VAR_MENU_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> deleteBonusById (
            @PathVariable String bonusId
    ){
        // 1. Memanggil service untuk menghapus objek menu
        String dataDelete = bonusService.deleteById(bonusId);

        // 2. Membuat Common Response
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(bonusId + "'s data has been deleted from menu list")
                .data(dataDelete)
                .build();

        return ResponseEntity.ok(response);
    }
}
