package com.upgrade.resto.service;

import com.upgrade.resto.dto.request.RoleRequest;
import com.upgrade.resto.dto.response.RoleResponse;
import com.upgrade.resto.entity.Role;

import java.util.List;

public interface RoleService {
    RoleResponse createNewRole(RoleRequest roleRequest);

    Role getById(String roleId);

    List<Role> getAllRoles();

    String updateById(String roleId, String newRoleName);

    String deleteById(String roleId);

    long count();
}
