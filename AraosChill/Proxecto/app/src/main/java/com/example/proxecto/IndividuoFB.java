package com.example.proxecto;

public class IndividuoFB {


    //vamos a guardar la PK de la bases de datos LOCAL para mejorar el rendimiento
    private int especie;

    private String sexo;
    private String rutaFoto;
    private String rutaAudio;
    private String plumaxe;
    private int peso;


    public IndividuoFB(int especie, String sexo, String rutaFoto, String rutaAudio, String plumaxe, int peso) {
        this.especie = especie;
        this.sexo = sexo;
        this.rutaFoto = rutaFoto;
        this.rutaAudio = rutaAudio;
        this.plumaxe = plumaxe;
        this.peso = peso;
    }

    public IndividuoFB( String rutaFoto, String rutaAudio, String plumaxe, int peso) {
        this.especie = especie;
        this.sexo = sexo;
        this.rutaFoto = rutaFoto;
        this.rutaAudio = rutaAudio;
        this.plumaxe = plumaxe;
        this.peso = peso;
    }

    public IndividuoFB(int especie, String rutaFoto, String rutaAudio, String plumaxe, int peso) {
        this.especie = especie;
        this.sexo = sexo;
        this.rutaFoto = rutaFoto;
        this.rutaAudio = rutaAudio;
        this.plumaxe = plumaxe;
        this.peso = peso;
    }

    public int getEspecie() {
        return especie;
    }

    public void setEspecie(int especie) {
        this.especie = especie;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getRutaAudio() {
        return rutaAudio;
    }

    public void setRutaAudio(String rutaAudio) {
        this.rutaAudio = rutaAudio;
    }

    public String getPlumaxe() {
        return plumaxe;
    }

    public void setPlumaxe(String plumaxe) {
        this.plumaxe = plumaxe;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
