package com.example.domenico.myapp;

public class Messaggio {

    private String tipo, messsaggio, mittente, data, oggetto, destinatario,tipo_segnalazione;
    private int id;


    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTipo_segnalazione() {
        return tipo_segnalazione;
    }

    public void setTipo_segnalazione(String tipo_segnalazione) {
        this.tipo_segnalazione = tipo_segnalazione;
    }

    public Messaggio(int id, String messaggio, String mittente, String tipo, String data, String oggetto, String destinatario, String tipo_segnalazione) {
        this.messsaggio = messaggio;
        this.id = id;
        this.mittente = mittente;
        this.data = data;
        this.tipo_segnalazione= tipo_segnalazione;
        this.tipo = tipo;
        this.destinatario = destinatario;
        this.oggetto = oggetto;

    }


    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
