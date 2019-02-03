package com.example.domenico.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AvvisiInviatiDipendenteComunale extends AppCompatActivity {

    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    ListView listView;
    private String cf;
    CustomAdapter customAdapter;
    int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avvisi_inviati_dipendente_comunale);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.avvisi_dipendente_comunale);
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

                        break;
                    case R.id.area_personale_dipendente_comunale:
                        i.setClass(getApplicationContext(), AreaPersonaleDipendenteComunale.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });


        listView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapter(this, R.layout.list_element, new ArrayList<Messaggio>());
        listView.setAdapter(customAdapter);

        //Prelevo il codice fiscale del cittadino dal database
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = prefs.getInt("ID", 0);
        db = MainActivity.dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT cf FROM utenti where id = ?", new String[]{"" + id});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                cf = c.getString(0);
                Log.d("PROVA", cf);

            }
        }

        TextView text = findViewById(R.id.text1);
        //Preleva tutti gli avvisi con mittente = codice fiscale utente.
        c = db.rawQuery("SELECT id,messaggio,mittente,tipo,data_segnalazione,oggetto,destinatario,tipo_segnalazione FROM messaggi WHERE mittente = ?", new String[]{cf});
        if (c != null && c.getCount() > 0) {
            text.setText("Avvisi inviati");
            for (int j = 0; j < c.getCount(); j++) {
                if (c.moveToPosition(j)) {
                    Messaggio mess = new Messaggio(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
                    customAdapter.add(mess);
                }
            }
        } else if (c.getCount() == 0) {
            text.setText("Nessun avviso inviato");
        }

    }


    public void mostraSegnalazione(View v) {
        int position = Integer.parseInt(v.getTag().toString()); //Prelevo la posizione dal tag
        Messaggio c = (Messaggio) customAdapter.getItem(position);//Prelevo il contatto con quella posizione

        Intent i = new Intent();
        i.putExtra("OGGETTO", c.getOggetto());
        i.putExtra("DESCRIZIONE", c.getMessaggio());
        i.putExtra("DATA", c.getData());
        i.putExtra("DESTINATARIO", c.getTipo());
        i.setClass(getApplicationContext(), VisualizzaAvvisoDipendenteComunale.class);
        startActivity(i);
    }

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonaleDipendenteComunale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }
}
