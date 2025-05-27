package com.sistema_inventario.controller;

import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.service.CategoryService;
import com.sistema_inventario.service.ProductService;
import com.sistema_inventario.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";
    }

    @GetMapping("/new")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("product", new ProductEntity());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.findAll()); // Agrega esta línea
        return "products/form";
    }

    @PostMapping("/save")
    public String guardarProducto(@ModelAttribute ProductEntity producto,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.save(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto");
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("suppliers", supplierService.findAll()); 
        return "products/form";
    }

    @GetMapping("/delete/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto");
        }
        return "redirect:/products";
    }

    @PostMapping("/products/update")
    public String updateProduct(@RequestParam Long idProduct,
                              @RequestParam String productName,
                              @RequestParam Double unitPrice,
                              @RequestParam Integer unitsInStock) {
        ProductEntity product = productService.findById(idProduct);
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setUnitsInStock(unitsInStock);
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/add-stock/{id}")
    public String addStock(@PathVariable Long id, @RequestParam int cantidad, RedirectAttributes redirectAttrs) {
        ProductEntity product = productService.findById(id);
        if (product == null) {
            redirectAttrs.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/products";
        }
        product.setUnitsInStock(product.getUnitsInStock() + cantidad);
        productService.save(product);
        redirectAttrs.addFlashAttribute("success", "Stock aumentado exitosamente");
        return "redirect:/products";
    }

    @GetMapping("/remove-stock/{id}")
    public String removeStock(@PathVariable Long id, @RequestParam int cantidad, RedirectAttributes redirectAttrs) {
        ProductEntity product = productService.findById(id);
        if (product == null) {
            redirectAttrs.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/products";
        }
        
        int nuevoStock = product.getUnitsInStock() - cantidad;
        if (nuevoStock < 0) {
            redirectAttrs.addFlashAttribute("warning", "Stock no puede ser negativo. Se ajustará a 0");
            nuevoStock = 0;
        }
        
        product.setUnitsInStock(nuevoStock);
        productService.save(product);
        redirectAttrs.addFlashAttribute("success", "Stock reducido exitosamente");
        return "redirect:/products";
    }

}