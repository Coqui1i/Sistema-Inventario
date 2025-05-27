package com.sistema_inventario.service;

import com.sistema_inventario.entity.CategoryEntity;
import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.repository.CategoryRepository;
import com.sistema_inventario.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity findById(Long idCategory) {
        return categoryRepository.findById(idCategory)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategory));
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<ProductEntity> getProductsByCategory(Long idCategory) {
        CategoryEntity category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + idCategory));
        return category.getProducts();
    }
}
