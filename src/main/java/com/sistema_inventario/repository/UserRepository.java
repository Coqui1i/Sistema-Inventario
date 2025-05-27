package com.sistema_inventario.repository;

import com.sistema_inventario.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity>findByUsername(String username);
    boolean existsByUsername(String username);
}