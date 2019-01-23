package com.example.domenico.myapp;

public class Utente {

    private String nome, cognome, password, email, cf, indirizzo, telefono, tipo;


    public Utente(String nome, String cognome, String cf, String email, String indirizzo, String password, String tel, String tipo) {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.email = email;
        this.indirizzo = indirizzo;
        this.password = password;
        this.telefono = tel;
        this.tipo = tipo;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
