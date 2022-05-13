package com.rocasoftware.rocamanager;

public class VehiculoModel {
    String marca,tipo,color,vehiculoImgSrc;

    public VehiculoModel() {

    }

    public VehiculoModel(String marca, String tipo, String color, String vehiculoImgSrc) {
        this.marca = marca;
        this.tipo = tipo;
        this.color = color;
        this.vehiculoImgSrc = vehiculoImgSrc;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVehiculoImgSrc() {
        return vehiculoImgSrc;
    }

    public void setVehiculoImgSrc(String vehiculoImgSrc) {
        this.vehiculoImgSrc = vehiculoImgSrc;
    }
}
