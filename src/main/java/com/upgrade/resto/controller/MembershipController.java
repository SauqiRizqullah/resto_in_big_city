package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.MembershipRequest;
import com.upgrade.resto.dto.response.CommonResponse;
import com.upgrade.resto.dto.response.MembershipResponse;
import com.upgrade.resto.entity.Membership;
import com.upgrade.resto.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(APIUrl.MEMBERSHIP)
public class MembershipController {
    private final MembershipService membershipService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<MembershipResponse>> createNewMembership (
            @RequestBody MembershipRequest membershipRequest){

        // 1. Membuat objek menu dari service
        MembershipResponse newMembership = membershipService.createNewMembership(membershipRequest);

        // 2. Membuat objek CommonResponse untuk melakukan Response

        CommonResponse<MembershipResponse> response = CommonResponse.<MembershipResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("You have created new membership succesfully!!!")
                .data(newMembership)
                .build();

        // 3. Mengembalikan Response Entity

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping(path = APIUrl.PATH_VAR_MEMBERSHIP_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<MembershipResponse>> getMembershipById(
            @PathVariable String membershipId
    ) {
        // 1. Membuat objek Menu Response
        MembershipResponse membership = membershipService.getById(membershipId);

        // 2. Membuat objek Common Response untuk mengisi data response
        CommonResponse<MembershipResponse> response = CommonResponse.<MembershipResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(membershipId + "'s data was already retrieved")
                .data(membership)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<List<Membership>>> getAllMemberships (){
        List<Membership> memberships = membershipService.getAllMemberships();

        CommonResponse<List<Membership>> response = CommonResponse.<List<Membership>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrieved all roles data")
                .data(memberships)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIUrl.PATH_VAR_MEMBERSHIP_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> updateByMembershipId(
            @PathVariable String membershipId,
            @RequestParam(name = "roleName") String benefit
    ){

        // 1. Memanggil service untuk melakukan update data menu
        String dataUpdate = membershipService.updateBenefitById(membershipId, benefit);

        // 2. Membuat Common Response

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(membershipId + "'s data has been updated successfully")
                .data(dataUpdate)
                .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping(path = APIUrl.PATH_VAR_MEMBERSHIP_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> deleteMembershipById (
            @PathVariable String membershipId
    ){
        // 1. Memanggil service untuk menghapus objek menu
        String dataDelete = membershipService.deleteById(membershipId);

        // 2. Membuat Common Response
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(membershipId + "'s data has been deleted from memberships list")
                .data(dataDelete)
                .build();

        return ResponseEntity.ok(response);
    }
}
