package com.rocasoftware.rocamanager;

import java.util.Date;

public class usuario
{


    private String UID;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String fechaRegistro;
    private String fechaUltimoLogin;
    private String tipo;

    public usuario()
    {

    }

    public usuario(String UID,String nombre,String apellido,String telefono,String email,String fechaRegistro,String fechaUltimoLogin,String tipo)
    {
        this.UID = UID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.fechaUltimoLogin= fechaUltimoLogin;
        this.tipo = tipo;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaUltimoLogin() {
        return fechaUltimoLogin;
    }

    public void setFechaUltimoLogin(String fechaUltimoLogin) {
        this.fechaUltimoLogin = fechaUltimoLogin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
