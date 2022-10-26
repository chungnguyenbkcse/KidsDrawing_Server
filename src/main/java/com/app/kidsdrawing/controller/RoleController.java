package com.app.kidsdrawing.controller;

import java.net.URI;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.kidsdrawing.dto.CreateRoleRequest;
import com.app.kidsdrawing.service.RoleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/role")
public class RoleController {

    private final RoleService  roleService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody CreateRoleRequest createRoleRequest) {
        Long roleId = roleService.createRole(createRoleRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{roleId}")
                .buildAndExpand(roleId).toUri();
        return ResponseEntity.created(location).build();
    }
}
