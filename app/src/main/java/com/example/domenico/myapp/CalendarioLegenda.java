package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

public class CalendarioLegenda extends DialogFragment {

    Dialog dialog;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.calendario_legenda);


//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View layout = inflater.inflate(R.layout.calendario_legenda,null);

//        builder.setView(inflater.inflate(R.layout.calendario_legenda, null));


        CardView x = (CardView) dialog.findViewById(R.id.chiudiLegenda);

//        builder.setView(layout);

//        final AlertDialog alertDialog = builder.create();
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        // Use the Builder class for convenient dialog construction
/*
        .setMessage("baaa")
                .setPositiveButton("SII", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("NOO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/
        // Create the AlertDialog object and return it
        return dialog;
    }







}
