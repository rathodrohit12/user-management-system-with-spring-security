package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.entity.RoleEntity;
import com.rohit.usermanagementsystem.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
