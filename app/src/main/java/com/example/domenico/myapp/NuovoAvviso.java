package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NuovoAvviso extends Activity {
    private BottomNavigationView bottomNavigationView;
    private EditText nome, descrizione;
    private DatabaseOpenHelper dbHelper;
    private CheckBox cittadinoCheck, operatoreCheck;
    String cittadino = "";
    String operatore = "";
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    boolean cittadinoChecked = false;
    boolean operatoreChecked = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuovo_avviso);
        nome = findViewById(R.id.titolo1);
        descrizione = findViewById(R.id.descrizione);
        db = MainActivity.dbHelper.getWritableDatabase();
        cittadinoCheck = findViewById(R.id.cittadinoCheck);
        operatoreCheck = findViewById(R.id.operatoreCheck);

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


    public void onCittadinoClick(View v) {
        if (cittadinoChecked) {
            cittadinoChecked = false;
        } else {
            cittadinoChecked = true;
        }
    }

    public void onOperatoreClick(View v) {
        if (operatoreChecked) {
            operatoreChecked = false;
        } else {
            operatoreChecked = true;
        }
    }


    public void salva(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String data = sdf.format(cal.getTime());
                        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        int id = prefs.getInt("ID", 0);
                        String cf = null;
                        Cursor c = db.rawQuery("SELECT cf FROM  utenti where id = ?", new String[]{"" + id});
                        if (c.moveToLast()) {
                            cf = c.getString(0);
                        }
                        String nomeAvviso = nome.getText().toString();
                        String descr = descrizione.getText().toString();
                        if (cittadinoChecked) {
                            ContentValues values = new ContentValues();
                            values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, "");
                            values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
                            values.put(SchemaDB.Tavola.COLUMN_OGGETTO, nomeAvviso);
                            values.put(SchemaDB.Tavola.COLUMN_MITTENTE, cf);
                            values.put(SchemaDB.Tavola.COLUMN_TIPO, "cittadino"); //Tipologia di attori a cui è rivolta la segnalazione
                            values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
                            values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, "");
                            db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);
                        }
                        if (operatoreChecked) {
                            ContentValues values = new ContentValues();
                            values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, "");
                            values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
                            values.put(SchemaDB.Tavola.COLUMN_OGGETTO, nomeAvviso);
                            values.put(SchemaDB.Tavola.COLUMN_MITTENTE, cf);
                            values.put(SchemaDB.Tavola.COLUMN_TIPO, "op ecologico"); //Tipologia di attori a cui è rivolta la segnalazione
                            values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
                            values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, "");
                            db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);
                        }
                        if (cittadinoChecked == false && operatoreChecked == false) {
                            errore();
                        } else
                            invioEffettuato();

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

    private void invioEffettuato() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finish();
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notifica");
        builder.setMessage("L'avviso è stato inviato ")
                .setPositiveButton("Ho capito", dialogClickListener).show();

        return;

    }

    private void errore() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attenzione");
        builder.setMessage("Nessun destinatario selezionato ! ")
                .setPositiveButton("Ho capito", dialogClickListener).show();

        return;

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


}

