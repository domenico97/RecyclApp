package com.example.domenico.myapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
    int NUMBER_OF_PAGES = 2;
    TextView testo;
    boolean prima_pagina = true;
    String giorno,nomeUtente;
    ImageButton image;
    private View BottomNavigationView;
    int occorenzaGiorno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_cittadino);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nomeUtente = prefs.getString("NOME","");
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        occorenzaGiorno = getOccurenceOfDayInMonth(date);
        giorno = format.format((date));


        //BottomNavigationView = findViewById(R.id.navigationView);
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
                testo.setText(nomeUtente+" oggi si conferisce");
                if (giorno.equals("lunedì")) {
                    if (occorenzaGiorno == 2 || occorenzaGiorno == 4) {
                        image.setImageResource(R.drawable.alluminio);
                    } else
                        image.setImageResource(R.drawable.indifferenziato);
                }
                if (giorno.equals("martedì"))
                    image.setImageResource(R.drawable.umido);
                if (giorno.equals("mercoledì"))
                    image.setImageResource(R.drawable.plastica);
                if (giorno.equals("giovedì"))
                    image.setImageResource(R.drawable.carta_vetro);
                if (giorno.equals("venerdì"))
                    image.setImageResource(R.drawable.umido);
                if (giorno.equals("sabato"))
                    image.setImageResource(R.drawable.non_conferire);
                if (giorno.equals("domenica"))
                    image.setImageResource(R.drawable.umido);

            } else {
                customView = getLayoutInflater().inflate(R.layout.view_custom_second, null);
                testo = customView.findViewById(R.id.testo);
                testo.setText("Continua così, "+ nomeUtente);

            }


            return customView;
        }
    };


    public static int getOccurenceOfDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

}
