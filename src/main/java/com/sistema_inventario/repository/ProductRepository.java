package com.sistema_inventario.repository;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByCategoryId(Long categoryId);
    boolean existsBySupplierId(Long supplierId);
    List<ProductEntity> findByUnitsInStockEquals(int stock);
    List<ProductEntity> findByCategory(CategoryEntity category);
    List<ProductEntity> findBySupplierId(Long supplierId);
}