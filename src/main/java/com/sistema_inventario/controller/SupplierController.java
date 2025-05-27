package com.sistema_inventario.controller;

import com.sistema_inventario.entity.SupplierEntity;
import com.sistema_inventario.service.ProductService;
import com.sistema_inventario.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final ProductService productService;

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        return "suppliers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new SupplierEntity());
        return "suppliers/form";
    }

    @PostMapping("/save")
    public String saveSupplier(@ModelAttribute SupplierEntity supplier) {
        supplierService.save(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.findById(id));
        return "suppliers/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (productService.existsBySupplierId(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "No se puede eliminar al proveedor porque tiene productos asociados.");
            return "redirect:/error-supplier";
        }
        supplierService.deleteById(id);
        return "redirect:/suppliers";
    }

    @PostMapping("/suppliers/update")
    public String updateSupplier(@RequestParam Long id,
                               @RequestParam String supplierName,
                               @RequestParam String supplierPhone,
                               @RequestParam String supplierEmail) {
        SupplierEntity supplier = supplierService.findById(id);
        supplier.setSupplierName(supplierName);
        supplier.setSupplierPhone(supplierPhone);
        supplier.setSupplierEmail(supplierEmail);
        supplierService.save(supplier);
        return "redirect:/suppliers";
    }

}