package com.example.proxecto;

public class IndoIndiv {

    int idIndi;
    String conc;
    String nomeSit;
    String foto;
    String data;


    public IndoIndiv(int idIndi, String conc, String nomeSit, String foto, String data) {
        this.idIndi = idIndi;
        this.conc = conc;
        this.nomeSit = nomeSit;
        this.foto = foto;
        this.data=data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIdIndi() {
        return idIndi;
    }

    public void setIdIndi(int idIndi) {
        this.idIndi = idIndi;
    }

    public String getConc() {
        return conc;
    }

    public void setConc(String conc) {
        this.conc = conc;
    }

    public String getNomeSit() {
        return nomeSit;
    }

    public void setNomeSit(String nomeSit) {
        this.nomeSit = nomeSit;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return this.idIndi + " " + this.conc + " " + this.nomeSit;
    }
}
