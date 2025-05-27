package com.sistema_inventario.service;

import com.sistema_inventario.entity.CategoryEntity;
import com.sistema_inventario.entity.ProductEntity;
import com.sistema_inventario.entity.SupplierEntity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public byte[] generateReport(List<?> data, String reportName, String reportType) throws JRException, IOException {
        // Cargar el archivo .jrxml
        File file = ResourceUtils.getFile("classpath:reports/" + reportName + ".jrxml");
        
        // Compilar el reporte
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        
        // Crear el dataSource con los datos
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        
        // Crear los parámetros del reporte
        Map<String, Object> parameters = new HashMap<>();
        
        // Generar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        // Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] generateProductReport(List<ProductEntity> data) throws JRException, IOException {
        return generateReport(data, "products_report", "pdf");
    }

    public byte[] generateCategoryReport(List<CategoryEntity> data) throws JRException, IOException {
        return generateReport(data, "categories_report", "pdf");
    }

    public byte[] generateSupplierReport(List<SupplierEntity> data) throws JRException, IOException {
        return generateReport(data, "suppliers_report", "pdf");
    }
}
