package com.example.domenico.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

public class HomepageCittadino extends AppCompatActivity {

    CarouselView customCarouselView;
    int NUMBER_OF_PAGES = 2;
    TextView testo;
    boolean prima_pagina = true;
    ImageButton image;

    int[] sampleImages = {R.drawable.calendar, R.drawable.info, R.drawable.home};
    private View BottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_cittadino);
        //BottomNavigationView = findViewById(R.id.navigationView);
        customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        customCarouselView.setPageCount(NUMBER_OF_PAGES);
        // set ViewListener for custom view
        customCarouselView.setViewListener(viewListener);

    }

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            testo = customView.findViewById(R.id.testo);
            image = customView.findViewById(R.id.image);
            if (testo != null && image != null) {
                if (position==0) {
                    testo.setText("NOME UTENTE oggi si conferisce");
                    image.setImageResource(R.drawable.alluminio);

                } else {
                    testo.setText("Continua così, NOME UTENTE");
                    image.setImageResource(R.drawable.alluminio);
                }
            }
            if (testo != null)
                Toast.makeText(getApplicationContext(), "" + testo.getText().toString(), Toast.LENGTH_LONG).show();
            return customView;
        }
    };


}
