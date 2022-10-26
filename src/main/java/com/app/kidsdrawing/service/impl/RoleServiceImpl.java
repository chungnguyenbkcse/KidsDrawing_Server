package com.app.kidsdrawing.service.impl;



import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateRoleRequest;
import com.app.kidsdrawing.entity.Role;
import com.app.kidsdrawing.repository.RoleRepository;
import com.app.kidsdrawing.service.RoleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Override
    public Long createRole(CreateRoleRequest createRoleRequest) {
        Role savedTutorial = Role.builder()
                .name(createRoleRequest.getName())
                .description(createRoleRequest.getDescription())
                .build();
        roleRepository.save(savedTutorial);

        return savedTutorial.getId();
    }
}
