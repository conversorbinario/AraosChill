package com.example.proxecto;

public class Tipo_ave {

    private int id_ave;
    private int xenero;
    private String xeneroString;
    private String especie;

    public Tipo_ave(int id_ave, int xenero, String especie) {
        this.id_ave = id_ave;
        this.xenero = xenero;
        this.especie = especie;
    }
    public Tipo_ave(String especie) {

        this.especie = especie;
    }




    public String getXeneroString() {
        return xeneroString;
    }

    public void setXeneroString(String xeneroString) {
        this.xeneroString = xeneroString;
    }


    public Tipo_ave(int xenero) {
        this.xenero = xenero;
    }

    public int getId_ave() {
        return id_ave;
    }

    public void setId_ave(int id_ave) {
        this.id_ave = id_ave;
    }

    public int getXenero() {
        return xenero;
    }

    public void setXenero(int xenero) {
        this.xenero = xenero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
