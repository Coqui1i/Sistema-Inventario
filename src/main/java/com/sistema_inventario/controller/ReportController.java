package com.sistema_inventario.controller;

import com.sistema_inventario.service.ProductService;
import com.sistema_inventario.service.CategoryService;
import com.sistema_inventario.service.SupplierService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.core.io.ResourceLoader;
import net.sf.jasperreports.engine.*;

@Controller
public class ReportController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    @GetMapping(value = "/products/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateProductReport(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_productos.pdf");

        // Obtener datos
        List<?> products = productService.findAll();
        if (products == null || products.isEmpty()) {
            throw new Exception("No hay productos para generar el reporte");
        }

        // Cargar y compilar el reporte
        JasperReport report = JasperCompileManager.compileReport(
            resourceLoader.getResource("classpath:reports/products_report.jrxml").getInputStream()
        );

        // Parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Reporte de Productos");

        // Crear dataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);

        // Generar y exportar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping(value = "/categories/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateCategoryReport(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_categorias.pdf");

        // Obtener datos
        List<?> categories = categoryService.findAll();
        if (categories == null || categories.isEmpty()) {
            throw new Exception("No hay categorías para generar el reporte");
        }

        // Cargar y compilar el reporte
        JasperReport report = JasperCompileManager.compileReport(
            resourceLoader.getResource("classpath:reports/categories_report.jrxml").getInputStream()
        );

        // Parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Reporte de Categorías");

        // Crear dataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(categories);

        // Generar y exportar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping(value = "/suppliers/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateSupplierReport(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_proveedores.pdf");

        // Obtener datos
        List<?> suppliers = supplierService.findAll();
        if (suppliers == null || suppliers.isEmpty()) {
            throw new Exception("No hay proveedores para generar el reporte");
        }

        // Cargar y compilar el reporte
        JasperReport report = JasperCompileManager.compileReport(
            resourceLoader.getResource("classpath:reports/suppliers_report.jrxml").getInputStream()
        );

        // Parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Reporte de Proveedores");

        // Crear dataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(suppliers);

        // Generar y exportar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
