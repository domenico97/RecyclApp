package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VisualizzaMessaggioOperatoreEcologico extends Activity {


    TextView nomeAvviso, dataAvviso, destinatario, descrizione;
    private BottomNavigationView bottomNavigationView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizza_messaggio_operatore_ecologico);

        nomeAvviso = findViewById(R.id.nomeAvviso);
        dataAvviso = findViewById(R.id.dataAvviso);
        destinatario = findViewById(R.id.destinatario);
        descrizione = findViewById(R.id.descrizione);

        Intent i = getIntent();
        nomeAvviso.setText(i.getStringExtra("OGGETTO"));
        dataAvviso.setText(i.getStringExtra("DATA"));
        descrizione.setText(i.getStringExtra("DESCRIZIONE"));
        destinatario.setText(i.getStringExtra("DESTINATARIO"));

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_operatore_ecologico:
                        i.setClass(getApplicationContext(),HomepageOperatoreEcologico.class);
                        startActivity(i);
                        break;
                    case R.id.segnalazioni_operatore_ecologico:
                        i.setClass(getApplicationContext(),SegnalazioniOperatoreEcologico.class);
                        startActivity(i);
                        break;
                    case R.id.avvisi_operatore_ecologico:
                        i.setClass(getApplicationContext(),AvvisiOperatoreEcologico.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });


    }

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }
}
