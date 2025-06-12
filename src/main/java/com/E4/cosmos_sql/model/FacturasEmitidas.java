package com.E4.cosmos_sql.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Container(containerName = "FacturasEmitidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturasEmitidas {
    @Id
    @GeneratedValue
    private String id_Factura;
    private String Num_Serie;
    private String name;
    private String Folio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String UUID;
    private String Base64;
    private LocalDateTime Fecha_Emision;
    private LocalDateTime Fecha_Timbrado;
    private String id_Cliente;
    private String id_Usuario;
    private int Total;

    public String getBase64() {
        return Base64;
    }

    public void setBase64(String base64) {
        Base64 = base64;
    }

    public LocalDateTime getFecha_Emision() {
        return Fecha_Emision;
    }

    public void setFecha_Emision(LocalDateTime fecha_Emision) {
        Fecha_Emision = fecha_Emision;
    }

    public LocalDateTime getFecha_Timbrado() {
        return Fecha_Timbrado;
    }

    public void setFecha_Timbrado(LocalDateTime fecha_Timbrado) {
        Fecha_Timbrado = fecha_Timbrado;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getId_Factura() {
        return id_Factura;
    }

    public void setId_Factura(String id_Factura) {
        this.id_Factura = id_Factura;
    }

    public String getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(String id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getNum_Serie() {
        return Num_Serie;
    }

    public void setNum_Serie(String num_Serie) {
        Num_Serie = num_Serie;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getSubTotal() {
        return (int) (Total / 1.16); // Calculate SubTotal based on Total
    }

    public int getIVA() {
        return Total - getSubTotal(); // Calculate IVA as the difference between Total and SubTotal
    }
}
