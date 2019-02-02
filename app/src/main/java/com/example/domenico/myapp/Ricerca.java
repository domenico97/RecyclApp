package com.example.domenico.myapp;

public class Ricerca {
    private String nome, cognome, cf;


    public Ricerca(String cf, String nome, String cognome) {
        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

}
