package com.anuppur.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.UserRepository;

/**
 * Optionally initializes a development-only user. It is disabled unless
 * explicitly enabled so deployments never receive known default credentials.
 */
@Component
@ConditionalOnProperty(name = "app.data.initialize-test-user", havingValue = "true")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${app.data.test-user.username}")
    private String testUsername;

    @Value("${app.data.test-user.password}")
    private String testPassword;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            Users existingUser = userRepository.findByUsernameAndStatusNot(
                    testUsername, DMSConstants.STATUS_DELETED);

            if (existingUser != null) {
                System.out.println("Test user already exists");
                return;
            }

            Optional<Role> adminRoleOptional = roleRepository.findById("ROLE_SYSTEM_ADMIN");
            Role adminRole = adminRoleOptional.orElseGet(() -> {
                Role role = new Role();
                role.setRoleCode("ROLE_SYSTEM_ADMIN");
                role.setRoleName("System Administrator");
                return roleRepository.save(role);
            });

            Users adminUser = new Users();
            adminUser.setUsername(testUsername);
            adminUser.setEmailId(testUsername);
            adminUser.setPassword(passwordEncoder.encode(testPassword));
            adminUser.setStatus(DMSConstants.STATUS_ACTIVE);
            adminUser.setFirstname("Admin");
            adminUser.setLastname("User");

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
            System.out.println("Test user created: " + testUsername);
        } catch (Exception exception) {
            System.out.println("Could not initialize test user: " + exception.getMessage());
            // Do not fail application startup when optional development data fails.
        }
    }
}
