package com.anuppur.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Initialize database with default test users on application startup
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Check if test user already exists
            Users existingUser = userRepository.findByUsernameAndStatusNot("admin@gmail.com", DMSConstants.STATUS_DELETED);
            
            if (existingUser == null) {
                // Try to get SYSTEM_ADMIN role by ID (assuming it exists)
                Optional<Role> adminRoleOpt = roleRepository.findById("ROLE_SYSTEM_ADMIN");
                System.out.println("Role Found : " + adminRoleOpt.isPresent());
                Role adminRole;
                
                if (adminRoleOpt.isPresent()) {
                    adminRole = adminRoleOpt.get();
                } else {
                    // Create SYSTEM_ADMIN role if it doesn't exist
                    adminRole = new Role();
                    adminRole.setRoleCode("ROLE_SYSTEM_ADMIN");
                    adminRole.setRoleName("System Administrator");
                    adminRole = roleRepository.save(adminRole);
                }

                // Create test admin user
                Users adminUser = new Users();
                adminUser.setUsername("admin@gmail.com");
                adminUser.setPassword(passwordEncoder.encode("admin123")); // Password: admin123
                adminUser.setStatus(DMSConstants.STATUS_ACTIVE);
                adminUser.setFirstname("Admin");
                adminUser.setLastname("User");
                
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                adminUser.setRoles(roles);
                
                userRepository.save(adminUser);
                System.out.println("✅ Test user created: admin@gmail.com / admin123");
            } else {
                System.out.println("✅ Test user already exists");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not initialize test user: " + e.getMessage());
            // Don't fail startup if initialization fails
        }
    }
}
