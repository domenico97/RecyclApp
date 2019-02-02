package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DettagliCittadino extends Activity {
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    CustomAdapterRicerche customAdapter;
    TextView nome, cognome, cf, email, indirizzo, telefono;
    TextView ultimeRicerche;
    EditText descrizione;
    ListView listView;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagli_cittadino);
        Intent intent = getIntent();
        String codFiscale = intent.getStringExtra("CODFISCALE");
        descrizione = findViewById(R.id.descrizione);
        db = MainActivity.dbHelper.getWritableDatabase();
        nome = findViewById(R.id.Nome);
        cognome = findViewById(R.id.Cognome);
        cf = findViewById(R.id.cf);
        telefono = findViewById(R.id.tel);
        email = findViewById(R.id.email);
        indirizzo = findViewById(R.id.Indirizzo);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_dipendente_comunale:
                        i.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                        startActivity(i);
                        break;
                    case R.id.avvisi_dipendente_comunale:
                       /* i.setClass(getApplicationContext(),. class);
                        startActivity(i);*/
                        break;
                    case R.id.area_personale_dipendente_comunale:
                        i.setClass(getApplicationContext(), AreaPersonaleDipendenteComunale.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
       Log.d("AAA",codFiscale);
        Cursor c = db.rawQuery("SELECT nome,cognome,cf,email,indirizzo,telefono FROM utenti WHERE cf = ?", new String[]{codFiscale});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                nome.setText(c.getString(0));
                cognome.setText(c.getString(1));
                cf.setText(c.getString(2));
                email.setText(c.getString(3));
                indirizzo.setText(c.getString(4));
                telefono.setText(c.getString(5));

            }
        }


    }

    public void invia(View v) {
        String descr = descrizione.getText().toString();

    }

    public void back(View v) {
        /*Intent i = new Intent();
        i.setClass(getApplicationContext(), CercaCittadino.class);
        startActivity(i);*/
        finish();
    }

}