package com.rohit.usermanagementsystem.configuration;

import com.rohit.usermanagementsystem.entity.RoleEntity;
import com.rohit.usermanagementsystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            RoleEntity userRole = new RoleEntity();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);


        }
    }
}
