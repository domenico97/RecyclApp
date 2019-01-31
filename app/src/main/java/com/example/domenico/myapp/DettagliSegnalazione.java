package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DettagliSegnalazione extends Activity {
    CustomAdapterSegnalazioni customAdapter;
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    TextView numeroSegnalazione, dataCreazione, infrazioni, cittadino ,nome, cognome,indirizzo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segnalazioni_operatore_ecologico);


        numeroSegnalazione = findViewById(R.id.numeroSegnalazione);
        dataCreazione = findViewById(R.id.dataCreazione);
        infrazioni = findViewById(R.id.infrazioni);
        nome = findViewById(R.id.Nome);
        cognome = findViewById(R.id.Cognome);
        indirizzo = findViewById(R.id.Indirizzo);

        Intent i = getIntent();
        numeroSegnalazione.setText(i.getStringExtra("NUMERO_SEGNALAZIONE"));
        dataCreazione.setText(i.getStringExtra("DATA"));
        infrazioni.setText(i.getStringExtra("DESCRIZIONE"));
        nome.setText(i.getStringExtra("NOME"));
        cognome.setText(i.getStringExtra("COGNOME"));
        indirizzo.setText(i.getStringExtra("INDIRIZZO"));

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.segnalazioni_operatore_ecologico);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_operatore_ecologico:
                        i.setClass(getApplicationContext(), HomepageOperatoreEcologico.class);
                        startActivity(i);
                        break;
                    case R.id.segnalazioni_operatore_ecologico:
                        i.setClass(getApplicationContext(), SegnalazioniOperatoreEcologico.class);
                        startActivity(i);
                        break;
                    case R.id.avvisi_operatore_ecologico:
                        i.setClass(getApplicationContext(), AvvisiOperatoreEcologico.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });


    }

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonaleOperatoreEcologico.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }

}