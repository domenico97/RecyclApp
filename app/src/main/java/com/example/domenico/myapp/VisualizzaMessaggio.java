package com.example.domenico.myapp;

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

public class VisualizzaMessaggio extends AppCompatActivity {


    TextView nomeAvviso, dataAvviso, destinatario, descrizione;
    private BottomNavigationView bottomNavigationView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizza_messaggio);

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
        bottomNavigationView.setSelectedItemId(R.id.navigation_news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        i.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(i);
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

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }
}
