package com.sistema_inventario.service;

import com.sistema_inventario.entity.RoleEntity;
import com.sistema_inventario.entity.UserEntity;
import com.sistema_inventario.repository.RoleRepository;
import com.sistema_inventario.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitializationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initializeData() {
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_USER");

        // Verificar si el usuario admin ya existe
        if (!userRepository.existsByUsername("admin")) {
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("1234"));
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Sistema");
            adminUser.setEnabled(true);

            // Asignar rol ADMIN al usuario admin
            RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
            if (adminRole == null) {

                adminRole = new RoleEntity();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }
            adminUser.addRole(adminRole);

            userRepository.save(adminUser);
            System.out.println("Usuario admin creado exitosamente");
        }
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            RoleEntity role = new RoleEntity();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("Rol creado: " + roleName);
            return role;
        });
    }
}