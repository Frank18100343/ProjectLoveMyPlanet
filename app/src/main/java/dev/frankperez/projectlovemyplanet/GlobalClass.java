package dev.frankperez.projectlovemyplanet;

import android.app.Application;

public class GlobalClass extends Application {

    private String nombres ;
    private String ruc ;
    private String razonSocial;
    private String apellidos;
    private String email ;
    private String telefono;
    private String saldoPuntos;


    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSaldoPuntos() {
        return saldoPuntos;
    }

    public void setSaldoPuntos(String saldoPuntos) {
        this.saldoPuntos = saldoPuntos;
    }
}
