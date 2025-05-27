package com.sistema_inventario.service;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.SupplierEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {
    List<SupplierEntity> findAll();
    SupplierEntity findById(Long id);
    SupplierEntity save(SupplierEntity supplier);
    void deleteById(Long id);
    List<ProductEntity> getProductsBySupplier(Long supplierId);
}