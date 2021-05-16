package com.example.proxecto;

public class Tipo_AveFB {

    private String xeneroFK;
    private String especie;

    public Tipo_AveFB(String xeneroFK, String especie) {
        this.xeneroFK = xeneroFK;
        this.especie = especie;
    }

    public String getXeneroFK() {
        return xeneroFK;
    }

    public void setXeneroFK(String xeneroFK) {
        this.xeneroFK = xeneroFK;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
