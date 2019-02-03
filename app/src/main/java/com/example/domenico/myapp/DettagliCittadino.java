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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    private String codiceFiscale;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagli_cittadino);
        Intent intent = getIntent();
        String codFiscale = intent.getStringExtra("CODFISCALE");
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
        Log.d("AAA", codFiscale);
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
        if (descr == null || descr.equals(""))
            errore("Il campo descrizione non può essere nullo");
        else {
            int id = prefs.getInt("ID", 0);
            Cursor c = db.rawQuery("SELECT cf FROM utenti where id = ?", new String[]{"" + id});
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    codiceFiscale = c.getString(0);


                }
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String data = sdf.format(cal.getTime());


            ContentValues values = new ContentValues();
            values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, "Sanzione");
            values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
            values.put(SchemaDB.Tavola.COLUMN_OGGETTO, "Sanzione");
            values.put(SchemaDB.Tavola.COLUMN_MITTENTE, codiceFiscale);
            values.put(SchemaDB.Tavola.COLUMN_TIPO, "cittadino");
            values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
            values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, cf.getText().toString());
            db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);
            confermaInvio();
        }

    }

    public void back(View v) {
        finish();
    }

    private void errore(String error) {
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
        builder.setMessage(error).setPositiveButton("Ho capito", dialogClickListener).show();

        return;

    }

    private void confermaInvio() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                        startActivity(intent);
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sanzione Inviata\n" +
                "La sanzione è stata inviata.")
                .setPositiveButton("OK", dialogClickListener).show();
        return;

    }

}