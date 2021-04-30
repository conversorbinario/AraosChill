package com.example.proxecto;

public class Individuo {

    private int plIndiv;
    private String sexo;
    private int especie;


    public Individuo(String sexo, int especie) {
        this.sexo = sexo;
        this.especie = especie;
    }

    public Individuo(int plIndiv, String sexo, int especie) {
        this.plIndiv = plIndiv;
        this.sexo = sexo;
        this.especie = especie;
    }

    public Individuo() {

    }

    public Individuo(String sexo) {
        this.sexo = sexo;
    }

    public int getPlIndiv() {
        return plIndiv;
    }

    public void setPlIndiv(int plIndiv) {
        this.plIndiv = plIndiv;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEspecie() {
        return especie;
    }

    public void setEspecie(int especie) {
        this.especie = especie;
    }
}
