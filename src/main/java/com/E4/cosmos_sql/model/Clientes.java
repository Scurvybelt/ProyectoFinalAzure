package com.E4.cosmos_sql.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;
import java.time.LocalDate;

@Container(containerName = "Clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clientes {
    @Id
    @GeneratedValue
    private String id_Cliente;
    private String Nombre_RazonSocial;
    private String RFC;
    private String Codigo_Postal;
    private String UsoCFDI;
    private String RegimenFiscal;
    private LocalDate Fecha_Registro;
    private String id_Usuario;

    public String getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(String id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getCodigo_Postal() {
        return Codigo_Postal;
    }

    public void setCodigo_Postal(String codigo_Postal) {
        Codigo_Postal = codigo_Postal;
    }

    public LocalDate getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(LocalDate fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }

    public String getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getNombre_RazonSocial() {
        return Nombre_RazonSocial;
    }

    public void setNombre_RazonSocial(String nombre_RazonSocial) {
        Nombre_RazonSocial = nombre_RazonSocial;
    }

    public String getRegimenFiscal() {
        return RegimenFiscal;
    }

    public void setRegimenFiscal(String regimenFiscal) {
        RegimenFiscal = regimenFiscal;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getUsoCFDI() {
        return UsoCFDI;
    }

    public void setUsoCFDI(String usoCFDI) {
        UsoCFDI = usoCFDI;
    }

    public Clientes(String Nombre_RazonSocial, String RFC, String Codigo_Postal, String UsoCFDI, String RegimenFiscal) {
        this.Nombre_RazonSocial = Nombre_RazonSocial;
        this.RFC = RFC;
        this.Codigo_Postal = Codigo_Postal;
        this.UsoCFDI = UsoCFDI;
        this.RegimenFiscal = RegimenFiscal;
        this.Fecha_Registro = LocalDate.now(); // Fecha actual
    }
}