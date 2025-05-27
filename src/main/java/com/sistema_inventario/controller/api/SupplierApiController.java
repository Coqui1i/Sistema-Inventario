package com.sistema_inventario.controller.api;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.SupplierEntity;
import com.sistema_inventario.service.SupplierService;
import com.sistema_inventario.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Suppliers", description = "API endpoints para gestionar proveedores")
public class SupplierApiController {

    private final SupplierService supplierService;
    private final ReportService reportService;

    public SupplierApiController(SupplierService supplierService, ReportService reportService) {
        this.supplierService = supplierService;
        this.reportService = reportService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores",
            description = "Devuelve una lista de todos los proveedores disponibles",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Lista de proveedores obtenida exitosamente",
                        content = @Content(schema = @Schema(implementation = SupplierEntity.class)))
            })
    public ResponseEntity<List<SupplierEntity>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un proveedor por ID",
            description = "Devuelve un proveedor específico basado en su ID",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Proveedor encontrado",
                        content = @Content(schema = @Schema(implementation = SupplierEntity.class))),
                @ApiResponse(responseCode = "404",
                        description = "Proveedor no encontrado")
            })
    public ResponseEntity<SupplierEntity> getSupplierById(
            @Parameter(description = "ID del proveedor a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo proveedor",
            description = "Crea un nuevo proveedor en el sistema",
            responses = {
                @ApiResponse(responseCode = "201",
                        description = "Proveedor creado exitosamente")
            })
    public ResponseEntity<SupplierEntity> createSupplier(
            @Parameter(description = "Información del proveedor a crear")
            @RequestBody SupplierEntity supplier) {
        return ResponseEntity.ok(supplierService.save(supplier));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un proveedor",
            description = "Actualiza un proveedor existente",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Proveedor actualizado exitosamente")
            })
    public ResponseEntity<SupplierEntity> updateSupplier(
            @Parameter(description = "ID del proveedor a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Información actualizada del proveedor")
            @RequestBody SupplierEntity supplier) {
        supplier.setId(id);
        return ResponseEntity.ok(supplierService.save(supplier));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un proveedor",
            description = "Elimina un proveedor del sistema",
            responses = {
                @ApiResponse(responseCode = "204",
                        description = "Proveedor eliminado exitosamente")
            })
    public ResponseEntity<Void> deleteSupplier(
            @Parameter(description = "ID del proveedor a eliminar")
            @PathVariable Long id) {
        supplierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    @Operation(summary = "Obtener productos de un proveedor",
            description = "Devuelve todos los productos asociados a un proveedor",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Lista de productos obtenida exitosamente")
            })
    public ResponseEntity<List<ProductEntity>> getProductsBySupplier(
            @Parameter(description = "ID del proveedor")
            @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getProductsBySupplier(id));
    }

}
