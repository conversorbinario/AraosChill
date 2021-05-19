package com.example.proxecto;

import java.io.Serializable;

public class AvistamentoFB implements Serializable{

    private String data;
    private String concello;
    private String nome_sitio;
    private String hora;

    public AvistamentoFB(String concello, String nome_sitio, String data, String hora) {
        this.data = data;
        this.concello = concello;
        this.nome_sitio = nome_sitio;
        this.hora = hora;
    }

    @Override
    public String toString() {
        return this.concello + " " + this.nome_sitio + " " + this.data + " " +this.hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getConcello() {
        return concello;
    }

    public void setConcello(String concello) {
        this.concello = concello;
    }

    public String getNome_sitio() {
        return nome_sitio;
    }

    public void setNome_sitio(String nome_sitio) {
        this.nome_sitio = nome_sitio;
    }
}
