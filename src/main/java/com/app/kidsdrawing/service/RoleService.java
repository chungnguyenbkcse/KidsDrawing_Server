package com.app.kidsdrawing.service;



import com.app.kidsdrawing.dto.CreateRoleRequest;

public interface RoleService {
    Long createRole(CreateRoleRequest createRoleRequest);
}
