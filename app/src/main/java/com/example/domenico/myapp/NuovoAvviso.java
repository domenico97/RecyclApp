package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NuovoAvviso extends Activity {
    private BottomNavigationView bottomNavigationView;
    private EditText nome,descrizione;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovo_avviso);
        nome = findViewById(R.id.titolo1);
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
    }


    public void annulla(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                        startActivity(i);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Annulla");
        builder.setMessage("Sei sicuro di voler annullare l'avviso ? ")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }

    public void salva(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        String nomeAvviso = nome.getText().toString();
                       /* ContentValues values = new ContentValues();
                        values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, "");
                        values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
                        values.put(SchemaDB.Tavola.COLUMN_OGGETTO, nomeAvviso);
                        values.put(SchemaDB.Tavola.COLUMN_MITTENTE, cf);
                        values.put(SchemaDB.Tavola.COLUMN_TIPO, "dip comunale"); //Tipologia di attori a cui è rivolta la segnalazione
                        values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
                        values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, "");
                        db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);*/
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salva avviso");
        builder.setMessage("Sei sicuro di voler salvare il nuovo avviso ? ")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }


}

