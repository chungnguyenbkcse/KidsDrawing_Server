package com.app.kidsdrawing.service;

import java.util.UUID;

import com.app.kidsdrawing.dto.CreateRoleRequest;

public interface RoleService {
    UUID createRole(CreateRoleRequest createRoleRequest);
}
