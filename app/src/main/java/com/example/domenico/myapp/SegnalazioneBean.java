package com.example.domenico.myapp;

public class SegnalazioneBean {

    private String messsaggio, dataCreazione, destinatario;
    private int numeroSegnalazione;


    public SegnalazioneBean(int numeroSegnalazione, String messaggio, String dataCreazione, String destinatario) {
        this.messsaggio = messaggio;
        this.numeroSegnalazione = numeroSegnalazione;
        this.dataCreazione = dataCreazione;
        this.destinatario = destinatario;

    }


    public String getMesssaggio() {
        return messsaggio;
    }

    public void setMesssaggio(String messsaggio) {
        this.messsaggio = messsaggio;
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