package com.example.domenico.myapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterSegnalazioni extends ArrayAdapter {

    private int resource;
    private LayoutInflater inflater;

    public CustomAdapterSegnalazioni(Context context, int resourceId, ArrayList<SegnalazioneBean> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {

            v = inflater.inflate(R.layout.segnalazione_element, null);
        }
        SegnalazioneBean c = (SegnalazioneBean) getItem(position);

        TextView numeroSegnalazione;
        TextView dataCreazione;
        ImageButton dettagli;

        numeroSegnalazione = (TextView) v.findViewById(R.id.numeroSegnalazione);
        dataCreazione = (TextView) v.findViewById(R.id.dataCreazione);
        dettagli = v.findViewById(R.id.mostraAvviso);
        //Mostro i dati del contatto c
        /* Populate the row's xml with info from the item */

        Log.d("PROVA",c.getDataCreazione());
        dataCreazione.setText(c.getDataCreazione());
        Log.d("PROVA",""+c.getNumeroSegnalazione());
        numeroSegnalazione.setText(""+c.getNumeroSegnalazione());

        dettagli.setTag(position);
        numeroSegnalazione.setTag(position);
        dataCreazione.setTag(position);
        return v;
    }
}
