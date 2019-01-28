package com.example.domenico.myapp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HomepageCittadino extends AppCompatActivity {

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
        setContentView(R.layout.home_page_cittadino);
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
        customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        customCarouselView.setPageCount(NUMBER_OF_PAGES);

        // set ViewListener for custom view
        customCarouselView.setViewListener(viewListener);

    }

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = null;
            // if (testo != null && image != null) {
            if (position == 0) {
                customView = getLayoutInflater().inflate(R.layout.view_custom_first, null);
                testo = customView.findViewById(R.id.testo);
                image = customView.findViewById(R.id.image);
                if (giorno.equals("lunedì")) {
                    if (occorenzaGiorno == 2 || occorenzaGiorno == 4) {
                        image.setImageResource(R.drawable.alluminio);
                    } else
                        image.setImageResource(R.drawable.indifferenziato);
                }
                boolean nonConferire = false;
                if (giorno.equals("martedì"))
                    image.setImageResource(R.drawable.umido);
                if (giorno.equals("mercoledì"))
                    image.setImageResource(R.drawable.plastica);
                if (giorno.equals("giovedì"))
                    image.setImageResource(R.drawable.carta_vetro);
                if (giorno.equals("venerdì"))
                    image.setImageResource(R.drawable.umido);
                if (giorno.equals("sabato")) {
                    nonConferire = true;
                    image.setImageResource(R.drawable.non_conferire);
                }
                if (giorno.equals("domenica"))
                    image.setImageResource(R.drawable.umido);
                if (nonConferire)
                    testo.setText(nomeUtente + " oggi ");
                else
                    testo.setText(nomeUtente + " oggi si conferisce");
            } else {
                customView = getLayoutInflater().inflate(R.layout.view_custom_second, null);
                testo = customView.findViewById(R.id.testo);
                testo.setText("Continua così, " + nomeUtente);
                TextView points = customView.findViewById(R.id.punti);
                points.setText("" + punti);
                ProgressBar progressBar = customView.findViewById(R.id.progress);
                progressBar.setProgress(punti / 10);
            }


            return customView;
        }
    };


    public static int getOccurenceOfDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }


    public void calendario(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), Calendario.class);
        startActivity(i);
    }

    public void statistiche(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), Statistiche.class);
        startActivity(i);
    }

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void raccoltaPunti(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RaccoltaPunti.class);
        startActivity(i);
    }

    public void easyMode(View v) {

    }

    public void identifica(View v) {

    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        /*SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("RIMANI_CONNESSO", false);*/
                        finishAffinity();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Conferma \nVuoi davvero uscire da RecyclApp ?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }


}
