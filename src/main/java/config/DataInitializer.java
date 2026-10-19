
package com.ems.employee_management_system.config;

import com.ems.employee_management_system.entity.Role;
import com.ems.employee_management_system.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ems.employee_management_system.entity.User;
import com.ems.employee_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initializeRoles(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            createRoleIfMissing(roleRepository, "ROLE_ADMIN");
            createRoleIfMissing(roleRepository, "ROLE_HR");
            createRoleIfMissing(roleRepository, "ROLE_EMPLOYEE");

            String adminUsername = System.getenv("EMS_ADMIN_USERNAME");
            String adminPassword = System.getenv("EMS_ADMIN_PASSWORD");

            if (adminUsername == null || adminUsername.isBlank()
                    || adminPassword == null || adminPassword.isBlank()) {
                throw new IllegalStateException(
                        "EMS Admin environment variables are not configured");
            }

            if (!userRepository.existsByUsername(adminUsername)) {
                Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() ->
                                new IllegalStateException("Admin role not found"));

                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(adminRole);
                admin.setEnabled(true);

                userRepository.save(admin);
                System.out.println("Initial Admin account created.");
            } else {
                System.out.println("Initial Admin account already exists.");
            }
        };
    }


    private void createRoleIfMissing(
            RoleRepository roleRepository, String roleName) {

        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }
}
