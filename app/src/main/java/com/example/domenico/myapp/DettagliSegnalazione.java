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
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    TextView numeroSegnalazione, dataCreazione, infrazioniTV, cittadino ,nome, cognome,indirizzo;
    String infrazioni;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizza_segnalazione);


        numeroSegnalazione = findViewById(R.id.numeroSegnalazione);
        dataCreazione = findViewById(R.id.dataCreazione);
        infrazioniTV = findViewById(R.id.infrazioni);
        nome = findViewById(R.id.Nome);
        cognome = findViewById(R.id.Cognome);
        indirizzo = findViewById(R.id.Indirizzo);

        Intent i = getIntent();

        numeroSegnalazione.setText(""+i.getIntExtra("NUMERO_SEGNALAZIONE",0));
        dataCreazione.setText(i.getStringExtra("DATA"));
        infrazioni = i.getStringExtra("DESCRIZIONE");
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


        // parsing delle informazioni contenute in "infrazioni"
        String testo ="";
        String[] tmp = infrazioni.split("//");
        String tmpInfrazioni = tmp[0];
        String altreInformazioni = null;
        if ( tmp.length > 1){
            altreInformazioni = tmp[1];
        }

        if(!tmpInfrazioni.isEmpty()) {
            String[] listaInfrazioni = tmpInfrazioni.split("/");
            for (int x = 0; x < listaInfrazioni.length; x++) {
                testo += (x + 1) + ") " + listaInfrazioni[x] + "\n";
                if(x==listaInfrazioni.length-1)
                    testo += "\n";
            }
        }
        if(altreInformazioni!=null){
            testo+="Informazioni Aggiuntive:\n"+altreInformazioni;
        }


        infrazioniTV.setText(testo);




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