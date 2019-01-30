package com.example.domenico.myapp;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Prodotto> {
    private int resource;
    private LayoutInflater inflater;

    public ResultAdapter(Context context, int resourceId, List<Prodotto> objects) {
        super(context, resourceId, objects);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG","Inflating view");
            v = inflater.inflate(R.layout.result_prodotto, null);
        }

        Prodotto c = getItem(position);

        Log.d("DEBUG","contact c="+c);

        TextView name;
        ImageView fotoButton;


        name = (TextView) v.findViewById(R.id.elem_lista_nome);
        fotoButton = (ImageView) v.findViewById(R.id.elem_lista_foto);


        fotoButton.setImageDrawable(c.getPicture());
        name.setText(c.getName());



        fotoButton.setTag(position);
        name.setTag(position);




        return v;
    }
}


