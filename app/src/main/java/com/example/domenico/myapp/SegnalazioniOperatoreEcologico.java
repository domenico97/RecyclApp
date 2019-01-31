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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SegnalazioniOperatoreEcologico extends Activity {
    CustomAdapterSegnalazioni customAdapter;
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    private String cf, cfDestinatario, nome, cognome, indirizzo;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segnalazioni_operatore_ecologico);

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
                        break;
                    case R.id.avvisi_operatore_ecologico:
                        i.setClass(getApplicationContext(), AvvisiOperatoreEcologico.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapterSegnalazioni(this, R.layout.segnalazione_element, new ArrayList<SegnalazioneBean>());
        listView.setAdapter(customAdapter);

        //Prelevo il codice fiscale dell' operatore dal database
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = prefs.getInt("ID", 0);
        db = MainActivity.dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT cf FROM utenti where id = ?", new String[]{"" + id});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                cf = c.getString(0);

            }
        }

        TextView text = findViewById(R.id.text1);
        c = db.rawQuery("SELECT id,messaggio,data_segnalazione,destinatario FROM messaggi WHERE mittente = ?", new String[]{cf});
        if (c != null && c.getCount() > 0) {
            for (int j = 0; j < c.getCount(); j++) {
                if (c.moveToFirst()) {
                    // cfDestinatario = c.getString(3);
                    SegnalazioneBean segn = new SegnalazioneBean(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
                    customAdapter.add(segn);
                }
            }
        } else if (c.getCount() == 0) {
            text.setText("Nessuna segnalazione inviata");
        }


    }

    public void mostraSegnalazione(View v) {
        int position = Integer.parseInt(v.getTag().toString()); //Prelevo la posizione dal tag
        SegnalazioneBean s = (SegnalazioneBean) customAdapter.getItem(position);//Prelevo la segnalazione con quella posizione
        cfDestinatario = s.getDestinatario();
        Cursor c = db.rawQuery("SELECT nome,cognome,indirizzo FROM utenti WHERE cf = ?", new String[]{cfDestinatario});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                nome = c.getString(0);
                cognome = c.getString(1);
                indirizzo = c.getString(2);
            }
        }

        Intent i = new Intent();
        i.putExtra("NUMERO_SEGNALAZIONE",s.getNumeroSegnalazione());
        i.putExtra("DESCRIZIONE", s.getMesssaggio());
        i.putExtra("DATA", s.getDataCreazione());
        i.putExtra("DESTINATARIO", s.getDestinatario());
        i.putExtra("NOME", nome);
        i.putExtra("COGNOME", cognome);
        i.putExtra("INDIRIZZO", indirizzo);
        i.setClass(getApplicationContext(), DettagliSegnalazione.class);
        startActivity(i);
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


