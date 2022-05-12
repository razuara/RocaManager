package com.rocasoftware.rocamanager;

public class ConductorModel {
    String nombreCompleto,email,cede,perfiImgSrc;

    public ConductorModel()
    {

    }
    public ConductorModel(String nombre, String email, String cede,String perfiImgSrc) {
        this.nombreCompleto = nombre;
        this.email = email;
        this.cede = cede;
        this.perfiImgSrc = perfiImgSrc;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCede() {
        return cede;
    }

    public void setCiudadCede(String perfiImgSrc) {
        this.cede = cede;
    }
    public String getPerfiImgSrc() {
        return perfiImgSrc;
    }

    public void setPerfiImgSrc(String perfiImgSrc) {
        this.perfiImgSrc = perfiImgSrc;
    }
}
