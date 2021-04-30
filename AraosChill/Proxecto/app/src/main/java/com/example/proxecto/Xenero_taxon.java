package com.example.proxecto;

public class Xenero_taxon {

    private int id_xenero;
    private String xenero;


    public Xenero_taxon(String xenero) {
        this.xenero = xenero;
    }

    public Xenero_taxon(int id_xenero, String xenero) {
        this.id_xenero = id_xenero;
        this.xenero = xenero;
    }

    public int getId_xenero() {
        return id_xenero;
    }

    public void setId_xenero(int id_xenero) {
        this.id_xenero = id_xenero;
    }

    public String getXenero() {
        return xenero;
    }

    public void setXenero(String xenero) {
        this.xenero = xenero;
    }
}
