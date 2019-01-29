package com.example.domenico.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import com.synnapps.carouselview.CarouselView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.domenico.myapp.HomepageCittadino.getOccurenceOfDayInMonth;

public class EasyMode extends AppCompatActivity {

    CarouselView customCarouselView;
    SharedPreferences prefs;
    private int NUMBER_OF_PAGES = 2;
    TextView testo;
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
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nomeUtente = prefs.getString("NOME", "");
        punti = prefs.getInt("PUNTI", 0);
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        occorenzaGiorno = getOccurenceOfDayInMonth(date);
        giorno = format.format((date));

        bottomNavigationView = findViewById(R.id.navigationView);
        //Listener bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        //Sono già alla Home
                        break;
                    case R.id.navigation_news:
                        i.setClass(getApplicationContext(), AvvisiCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_info:
                        i.setClass(getApplicationContext(), Contatti.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
    }
}