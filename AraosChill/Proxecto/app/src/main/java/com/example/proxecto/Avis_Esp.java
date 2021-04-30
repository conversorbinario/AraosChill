package com.example.proxecto;

public class Avis_Esp {

    private String conce;
    private String nomeSitio;
    private String data;
    private String xen_esp;
    private String dir_foto;
    private String dir_audio;
    private int idIndiv;

    public Avis_Esp(String conce, String nomeSitio, String data, String xen_esp, String dir_foto, String dir_audio) {
        this.conce = conce;
        this.nomeSitio = nomeSitio;
        this.data = data;
        this.xen_esp = xen_esp;
        this.dir_foto = dir_foto;
        this.dir_audio = dir_audio;
    }

    public Avis_Esp(String conce, String nomeSitio, String data, String xen_esp, String dir_foto, String dir_audio, int idIndiv) {
        this.conce = conce;
        this.nomeSitio = nomeSitio;
        this.data = data;
        this.xen_esp = xen_esp;
        this.dir_foto = dir_foto;
        this.dir_audio = dir_audio;
        this.idIndiv =idIndiv;
    }

    public int getIdIndiv() {
        return idIndiv;
    }

    public void setIdIndiv(int idIndiv) {
        this.idIndiv = idIndiv;
    }

    public String getConce() {
        return conce;
    }

    public void setConce(String conce) {
        this.conce = conce;
    }

    public String getNomeSitio() {
        return nomeSitio;
    }

    public void setNomeSitio(String nomeSitio) {
        this.nomeSitio = nomeSitio;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getXen_esp() {
        return xen_esp;
    }

    public void setXen_esp(String xen_esp) {
        this.xen_esp = xen_esp;
    }

    public String getDir_foto() {
        return dir_foto;
    }

    public void setDir_foto(String dir_foto) {
        this.dir_foto = dir_foto;
    }

    public String getDir_audio() {
        return dir_audio;
    }

    public void setDir_audio(String dir_audio) {
        this.dir_audio = dir_audio;
    }


}
