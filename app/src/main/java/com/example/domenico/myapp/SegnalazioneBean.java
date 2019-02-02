package com.example.domenico.myapp;

public class SegnalazioneBean {

    private String messaggio, dataCreazione, destinatario,mittente,tipo;
    private int numeroSegnalazione;


    public SegnalazioneBean(int numeroSegnalazione, String messaggio, String dataCreazione, String destinatario,String mittente,String tipo) {
        this.messaggio = messaggio;
        this.numeroSegnalazione = numeroSegnalazione;
        this.dataCreazione = dataCreazione;
        this.destinatario = destinatario;
        this.mittente = mittente;
        this.tipo = tipo;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }


    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }


    public String getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(String dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public int getNumeroSegnalazione() {
        return numeroSegnalazione;
    }

    public void setNumeroSegnalazione(int numeroSegnalazione) {
        this.numeroSegnalazione = numeroSegnalazione;
    }
}