package com.example.domenico.myapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {


    private SQLiteDatabase db = null;
    public static DatabaseOpenHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private SimpleCursorAdapter adapterSelected;
    SharedPreferences prefs;
    ArrayList<Utente> utenti = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        dbHelper = new DatabaseOpenHelper(this);
        boolean primoAccesso = prefs.getBoolean("Accesso", true);
        if (primoAccesso) {
            db = dbHelper.getWritableDatabase();
            utenti.add(new Utente("Giulia", "Valli", "VLLGLI79A41H703B", "giulia.valli@gmail.com", "via Roma Fisciano", "giulia79", "3894552124", "dip comunale"));
            utenti.add(new Utente("Domenico", "Trotta", "VLLGLI79A41H703B", "domenico.trotta@live.it", "via Roma Fisciano", "dom1997", "3894552124", "cittadino"));
            utenti.add(new Utente("Marco", "Giuliani", "GLNMRC74M06H703X", "marco.giuliani@gmail.com", "via Toscanello Baronissi", "marco74", "3297856896", "op ecologico"));
            for (int i = 0; i < utenti.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(SchemaDB.Tavola.COLUMN_NAME, utenti.get(i).getNome());
                values.put(SchemaDB.Tavola.COLUMN_COGNOME, utenti.get(i).getCognome());
                values.put(SchemaDB.Tavola.COLUMN_CF, utenti.get(i).getCf());
                values.put(SchemaDB.Tavola.COLUMN_EMAIL, utenti.get(i).getEmail());
                values.put(SchemaDB.Tavola.COLUMN_INDIRIZZO, utenti.get(i).getIndirizzo());
                values.put(SchemaDB.Tavola.COLUMN_TELEFONO, utenti.get(i).getTelefono());
                values.put(SchemaDB.Tavola.COLUMN_TIPO, utenti.get(i).getTipo());
                values.put(SchemaDB.Tavola.COLUMN_PASSWORD, utenti.get(i).getPassword());
                db.insert(SchemaDB.Tavola.TABLE_NAME, null, values);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent j = new Intent();
                    j.setClass(getApplicationContext(), LoginForm.class);
                    startActivity(j);
                }
            }).start();


        } else {
            Intent z = new Intent();
            if (prefs.getBoolean("RIMANI_CONNESSO", false)) {
                if (prefs.getString("TIPO", "").equals("cittadino")) {
                    z.setClass(getApplicationContext(), HomepageCittadino.class);
                } else if (prefs.getString("TIPO", "").equals("operatoreEcologico")) {
                    z.setClass(getApplicationContext(), HomepageOperatoreEcologico.class);
                } else if (prefs.getString("TIPO", "").equals("dipendenteComunale")) {
                    z.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                }

            } else {
                z.setClass(getApplicationContext(), LoginForm.class);

            }
            startActivity(z);
        }


    }


}
