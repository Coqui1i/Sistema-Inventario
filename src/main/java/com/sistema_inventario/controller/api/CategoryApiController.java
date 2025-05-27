package com.sistema_inventario.controller.api;

import com.sistema_inventario.entity.CategoryEntity;
import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.service.CategoryService;
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
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "API endpoints para gestionar categorías")
public class CategoryApiController {

    private final CategoryService categoryService;
    private final ReportService reportService;

    public CategoryApiController(CategoryService categoryService, ReportService reportService) {
        this.categoryService = categoryService;
        this.reportService = reportService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las categorías",
            description = "Devuelve una lista de todas las categorías disponibles",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Lista de categorías obtenida exitosamente",
                            content = @Content(schema = @Schema(implementation = CategoryEntity.class)))
            })
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }



    @GetMapping("/{id}")
    @Operation(summary = "Obtener una categoría por ID",
            description = "Devuelve una categoría específica basada en su ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Categoría encontrada",
                            content = @Content(schema = @Schema(implementation = CategoryEntity.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Categoría no encontrada")
            })
    public ResponseEntity<CategoryEntity> getCategoryById(
            @Parameter(description = "ID de la categoría a buscar")
            @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva categoría",
            description = "Crea una nueva categoría en el sistema",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Categoría creada exitosamente")
            })
    public ResponseEntity<CategoryEntity> createCategory(
            @Parameter(description = "Información de la categoría a crear")
            @RequestBody CategoryEntity category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría",
            description = "Actualiza una categoría existente",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Categoría actualizada exitosamente")
            })
    public ResponseEntity<CategoryEntity> updateCategory(
            @Parameter(description = "ID de la categoría a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Información actualizada de la categoría")
            @RequestBody CategoryEntity category) {
        category.setId(id);
        return ResponseEntity.ok(categoryService.save(category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría",
            description = "Elimina una categoría del sistema",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Categoría eliminada exitosamente")
            })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID de la categoría a eliminar")
            @PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    @Operation(summary = "Obtener productos de una categoría",
            description = "Devuelve todos los productos asociados a una categoría",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Lista de productos obtenida exitosamente")
            })
    public ResponseEntity<List<ProductEntity>> getProductsByCategory(
            @Parameter(description = "ID de la categoría")
            @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getProductsByCategory(id));
    }

}
