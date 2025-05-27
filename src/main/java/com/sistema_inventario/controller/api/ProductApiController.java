package com.sistema_inventario.controller.api;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.service.ProductService;
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
@RequestMapping("/api/products")
@Tag(name = "Products", description = "API endpoints para gestionar productos")
public class ProductApiController {

    private final ProductService productService;
    private final ReportService reportService;

    public ProductApiController(ProductService productService, ReportService reportService) {
        this.productService = productService;
        this.reportService = reportService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos",
            description = "Devuelve una lista de todos los productos disponibles",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Lista de productos obtenida exitosamente",
                        content = @Content(schema = @Schema(implementation = ProductEntity.class)))
            })
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID",
            description = "Devuelve un producto específico basado en su ID",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Producto encontrado",
                        content = @Content(schema = @Schema(implementation = ProductEntity.class))),
                @ApiResponse(responseCode = "404",
                        description = "Producto no encontrado")
            })
    public ResponseEntity<ProductEntity> getProductById(
            @Parameter(description = "ID del producto a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto",
            description = "Crea un nuevo producto en el sistema",
            responses = {
                @ApiResponse(responseCode = "201",
                        description = "Producto creado exitosamente")
            })
    public ResponseEntity<ProductEntity> createProduct(
            @Parameter(description = "Información del producto a crear")
            @RequestBody ProductEntity product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto",
            description = "Actualiza un producto existente",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Producto actualizado exitosamente")
            })
    public ResponseEntity<ProductEntity> updateProduct(
            @Parameter(description = "ID del producto a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Información actualizada del producto")
            @RequestBody ProductEntity product) {

        ProductEntity existingProduct = productService.findById(id);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        

        existingProduct.setProductName(product.getProductName());
        existingProduct.setUnitPrice(product.getUnitPrice());
        existingProduct.setUnitsInStock(product.getUnitsInStock());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setSupplier(product.getSupplier());
        
        return ResponseEntity.ok(productService.save(existingProduct));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto",
            description = "Elimina un producto del sistema",
            responses = {
                @ApiResponse(responseCode = "204",
                        description = "Producto eliminado exitosamente")
            })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto a eliminar")
            @PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stock/increase")
    @Operation(summary = "Aumentar stock de un producto",
            description = "Aumenta el stock de un producto específico",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Stock actualizado exitosamente")
            })
    public ResponseEntity<ProductEntity> increaseStock(
            @Parameter(description = "ID del producto")
            @RequestParam Long id,
            @Parameter(description = "Cantidad a aumentar")
            @RequestParam int cantidad) {
        ProductEntity product = productService.findById(id);
        product.setUnitsInStock(product.getUnitsInStock() + cantidad);
        return ResponseEntity.ok(productService.save(product));
    }

    @PostMapping("/stock/decrease")
    @Operation(summary = "Reducir stock de un producto",
            description = "Reduce el stock de un producto específico",
            responses = {
                @ApiResponse(responseCode = "200",
                        description = "Stock actualizado exitosamente")
            })
    public ResponseEntity<ProductEntity> decreaseStock(
            @Parameter(description = "ID del producto")
            @RequestParam Long id,
            @Parameter(description = "Cantidad a reducir")
            @RequestParam int cantidad) {
        ProductEntity product = productService.findById(id);
        product.setUnitsInStock(product.getUnitsInStock() - cantidad);
        return ResponseEntity.ok(productService.save(product));
    }

}
