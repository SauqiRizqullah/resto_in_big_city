package com.upgrade.resto.service.impl;

import com.upgrade.resto.dto.request.RoleRequest;
import com.upgrade.resto.dto.response.RoleResponse;
import com.upgrade.resto.entity.Role;
import com.upgrade.resto.repository.RoleRepository;
import com.upgrade.resto.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public RoleResponse createNewRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .roleName(roleRequest.getRoleName())
                .build();
        roleRepository.saveAndFlush(role);

        return parseRoleToRoleResponse(role);
    }

    private RoleResponse parseRoleToRoleResponse(Role role) {

        String id;

        if (role.getRoleId() == null) {
            id = null;
        } else {
            id = role.getRoleId();
        }

        return RoleResponse.builder()
                .roleId(id)
                .roleName(role.getRoleName())
                .build();

    }

    @Override
    public Role getById(String roleId) {

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id was not found!!!"));

        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public String updateById(String roleId, String newRoleName) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id was not found!!!"));

        role.setRoleName(newRoleName);

        roleRepository.saveAndFlush(role);
        return "The new name of " + role.getRoleName() + " is " + newRoleName;
    }

    @Override
    public String deleteById(String roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role Id tidak ditemukan"));
        roleRepository.delete(role);
        return "We are so sad that " + roleId + "'s data is gone forever";
    }

    @Override
    public long count() {
        return roleRepository.count();
    }
}
