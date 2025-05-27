package com.sistema_inventario.controller.api;

import com.sistema_inventario.entity.UserEntity;
import com.sistema_inventario.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API endpoints para gestionar usuarios")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios",
            description = "Devuelve una lista de todos los usuarios del sistema",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Lista de usuarios obtenida exitosamente",
                        content = @Content(schema = @Schema(implementation = UserEntity.class)))
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por ID",
            description = "Devuelve un usuario específico basado en su ID",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Usuario encontrado",
                        content = @Content(schema = @Schema(implementation = UserEntity.class))),
                @ApiResponse(responseCode = "404",
                        description = "Usuario no encontrado")
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserEntity> getUserById(
            @Parameter(description = "ID del usuario a buscar")
            @PathVariable Long id) {
        UserEntity user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario",
            description = "Crea un nuevo usuario en el sistema",
            responses = {
                @ApiResponse(responseCode = "201",
                        description = "Usuario creado exitosamente")
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserEntity> createUser(
            @Parameter(description = "Información del usuario a crear")
            @RequestBody UserEntity user) {
        UserEntity savedUser = userService.saveUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario",
            description = "Actualiza un usuario existente",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Usuario actualizado exitosamente")
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserEntity> updateUser(
            @Parameter(description = "ID del usuario a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Información actualizada del usuario")
            @RequestBody UserEntity user) {
        user.setId(id);
        UserEntity updatedUser = userService.saveUser(user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario",
            description = "Elimina un usuario del sistema",
            responses = {
                @ApiResponse(responseCode = "204",
                        description = "Usuario eliminado exitosamente")
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID del usuario a eliminar")
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener información del usuario actual",
            description = "Devuelve la información del usuario que está actualmente autenticado",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Información del usuario obtenida exitosamente")
            })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserEntity> getCurrentUser() {
        UserEntity currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(currentUser);
    }
}
