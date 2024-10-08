package com.upgrade.resto.controller;

import com.upgrade.resto.constant.APIUrl;
import com.upgrade.resto.dto.request.RoleRequest;
import com.upgrade.resto.dto.response.CommonResponse;
import com.upgrade.resto.dto.response.RoleResponse;
import com.upgrade.resto.entity.Role;
import com.upgrade.resto.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(APIUrl.ROLE)
public class RoleController {
    private final RoleService roleService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<RoleResponse>> createNewRole (
            @RequestBody RoleRequest roleRequest){

        // 1. Membuat objek menu dari service
        RoleResponse newRole = roleService.createNewRole(roleRequest);

        // 2. Membuat objek CommonResponse untuk melakukan Response

        CommonResponse<RoleResponse> response = CommonResponse.<RoleResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("You have created new role succesfully!!!")
                .data(newRole)
                .build();

        // 3. Mengembalikan Response Entity

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @GetMapping(path = APIUrl.PATH_VAR_ROLE_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<Role>> getRoleById(
            @PathVariable String roleId
    ) {
        // 1. Membuat objek Menu Response
        Role role = roleService.getById(roleId);

        // 2. Membuat objek Common Response untuk mengisi data response
        CommonResponse<Role> response = CommonResponse.<Role>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roleId + "'s data was already retrieved")
                .data(role)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CommonResponse<List<Role>>> getAllRoles (){
        List<Role> roles = roleService.getAllRoles();

        CommonResponse<List<Role>> response = CommonResponse.<List<Role>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success retrieved all roles data")
                .data(roles)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIUrl.PATH_VAR_ROLE_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> updateByMenuName(
            @PathVariable String roleId,
            @RequestParam(name = "roleName") String newRoleName
    ){

        // 1. Memanggil service untuk melakukan update data menu
        String dataUpdate = roleService.updateById(roleId, newRoleName);

        // 2. Membuat Common Response

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roleId + "'s price has been updated successfully")
                .data(dataUpdate)
                .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ROLE_ID, produces = "application/json")
    public ResponseEntity<CommonResponse<String>> deleteMenuById (
            @PathVariable String roleId
    ){
        // 1. Memanggil service untuk menghapus objek menu
        String dataDelete = roleService.deleteById(roleId);

        // 2. Membuat Common Response
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(roleId + "'s data has been deleted from roles list")
                .data(dataDelete)
                .build();

        return ResponseEntity.ok(response);
    }
}
