package com.example.domenico.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class CustomAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resourceId, List<Messaggio> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {

            v = inflater.inflate(R.layout.list_element, null);
        }
        Messaggio c = (Messaggio) getItem(position);

        TextView nomeAvviso;
        TextView dataAvviso;
        ImageButton dettagli;

        nomeAvviso = (TextView) v.findViewById(R.id.nomeAvviso);
        dataAvviso = (TextView) v.findViewById(R.id.dataAvviso);
        dettagli= v.findViewById(R.id.mostraAvviso);
        //Mostro i dati del contatto c
        /* Populate the row's xml with info from the item */

        nomeAvviso.setText(c.getOggetto());
        dataAvviso.setText(c.getData());

        dettagli.setTag(position);
        nomeAvviso.setTag(position);
        dataAvviso.setTag(position);
        return v;
    }
}
