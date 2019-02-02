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

public class CustomAdapterRicerche extends ArrayAdapter {

    private int resource;
    private LayoutInflater inflater;
    private ArrayList<Ricerca> objects;

    public ArrayList<Ricerca> getObjects() {
        return objects;
    }

    public CustomAdapterRicerche(Context context, int resourceId, ArrayList<Ricerca> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
        this.objects=objects;
    }



    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {

            v = inflater.inflate(R.layout.ultima_ricerca, null);
        }
        Ricerca c = (Ricerca) getItem(position);

        TextView cf;
        TextView nome;
        ImageButton dettagli;

        cf = (TextView) v.findViewById(R.id.codiceFiscale);
        nome = (TextView) v.findViewById(R.id.nome_cognome);
        dettagli = v.findViewById(R.id.dettagli);


        Log.d("PROVA", c.getCf());
        cf.setText(c.getCf());
        Log.d("PROVA", "" + c.getNome());
        nome.setText(c.getNome() + " " + c.getCognome());


        dettagli.setTag(position);
        return v;
    }
}
