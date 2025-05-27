package com.sistema_inventario.service;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.SupplierEntity;
import com.sistema_inventario.repository.ProductRepository;
import com.sistema_inventario.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public List<SupplierEntity> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public SupplierEntity findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

    @Override
    public SupplierEntity save(SupplierEntity supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public List<ProductEntity> getProductsBySupplier(Long supplierId) {
        return productRepository.existsBySupplierId(supplierId) 
            ? productRepository.findBySupplierId(supplierId) 
            : Collections.emptyList();
    }
}
