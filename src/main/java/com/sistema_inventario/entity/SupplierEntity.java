package com.sistema_inventario.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "suppliers")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String supplierName;

    @Column(name = "address")
    private String supplierEmail;

    @Column(name = "phone")
    private String supplierPhone;
}
