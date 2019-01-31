package com.example.domenico.myapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.domenico.myapp.HomepageCittadino.getOccurenceOfDayInMonth;

public class EasyMode extends AppCompatActivity {

    CarouselView customCarouselView;
    SharedPreferences prefs;
    private int NUMBER_OF_PAGES = 2;
    TextView testo, data;
    Switch easyMode;
    boolean prima_pagina = true;
    String giorno, nomeUtente;
    ImageButton image;
    private BottomNavigationView bottomNavigationView;
    private int punti;
    int occorenzaGiorno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_mode);
        easyMode = findViewById(R.id.switch1);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        easyMode.setChecked(true);
        data = findViewById(R.id.data);

        nomeUtente = prefs.getString("NOME", "");
        punti = prefs.getInt("PUNTI", 0);
        Date date = new Date();
        DateFormat formatoData = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
        String s = formatoData.format(date);
        data.setText(s);


        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        occorenzaGiorno = getOccurenceOfDayInMonth(date);
        giorno = format.format((date));


    }

    public void logout(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), LoginForm.class);
                        startActivity(i);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Stai per uscire da RecyclApp. Sei sicuro?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }

    public void easyMode(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), HomepageCittadino.class);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("EASY_MODE", false);
        editor.apply();
        startActivity(i);
    }

    public void calendario(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), Calendario.class);
        startActivity(i);
    }

    public void avvisi(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AvvisiCittadino.class);
        startActivity(i);
    }

    public void contatti(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), Contatti.class);
        startActivity(i);
    }

    public void identifica(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), IdentificaTipologiaRifiuto.class);
        startActivity(i);
    }

    public void raccoltaPunti(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RaccoltaPunti.class);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("EASY_MODE", false);
                        editor.commit();
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Conferma \nStai per uscire dalla modalità Easy Mode.Sei sicuro?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }
}