package com.sistema_inventario.service;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<ProductEntity> findAll();
    ProductEntity findById(Long id);
    ProductEntity save(ProductEntity product);
    void deleteById(Long id);
    boolean existsByCategoryId(Long categoryId);
    boolean existsBySupplierId(Long supplierId);
    List<ProductEntity> findByUnitsInStockEquals(int stock);
    List<ProductEntity> findByCategory(CategoryEntity category);
}