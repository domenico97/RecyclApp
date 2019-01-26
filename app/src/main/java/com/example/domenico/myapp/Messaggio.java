package com.example.domenico.myapp;

public class Messaggio {

    private String tipo, messsaggio, mittente;


    public Messaggio(String messaggio, String mittente, String tipo) {
        this.messsaggio = messaggio;
        this.mittente = mittente;
        this.tipo = tipo;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMesssaggio() {
        return messsaggio;
    }

    public void setMesssaggio(String messsaggio) {
        this.messsaggio = messsaggio;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
}
