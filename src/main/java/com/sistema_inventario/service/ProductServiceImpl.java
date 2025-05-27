package com.sistema_inventario.service;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.CategoryEntity;
import com.sistema_inventario.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    @Override
    public boolean existsBySupplierId(Long supplierId) {return productRepository.existsBySupplierId(supplierId); }

    @Override
    public List<ProductEntity> findByUnitsInStockEquals(int stock) {
        return productRepository.findByUnitsInStockEquals(stock);
    }

    @Override
    public List<ProductEntity> findByCategory(CategoryEntity category) {
        return productRepository.findByCategory(category);
    }
}