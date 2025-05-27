package com.sistema_inventario.service;

import com.sistema_inventario.entity.CategoryEntity;
import com.sistema_inventario.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryEntity> findAll();
    CategoryEntity findById(Long id);
    CategoryEntity save(CategoryEntity category);
    void deleteById(Long id);
    List<ProductEntity> getProductsByCategory(Long idCategory);
}