package com.example.domenico.myapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Segnalazione extends AppCompatActivity {

    private DatabaseOpenHelper dbHelper;
    EditText oggetto, descrizione;
    private String cf;
    private int segnalazioni;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    Spinner spinner;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segnalazione);
        oggetto = findViewById(R.id.oggetto);
        descrizione = findViewById(R.id.descrizione);
        spinner = findViewById(R.id.spinner);
        bottomNavigationView = findViewById(R.id.navigationView);
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
        i.putExtra("ActivityPrecedente", "calendario");
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }

    public void invia(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        String tipo = spinner.getSelectedItem().toString();
                        String descr = descrizione.getText().toString();
                        String obj = oggetto.getText().toString();
                        if (descr.equals("") || obj.equals("")) {
                            Toast.makeText(getApplicationContext(), "I campi non possono essere vuoti !", Toast.LENGTH_SHORT).show();
                        } else {
                            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int id = prefs.getInt("ID", 0);
                            db = MainActivity.dbHelper.getWritableDatabase();
                            Cursor c = db.rawQuery("SELECT cf,segnalazioni FROM utenti where id = ?", new String[]{"" + id});
                            if (c != null && c.getCount() > 0) {
                                if (c.moveToFirst()) {
                                    cf = c.getString(0);
                                    segnalazioni = c.getInt(1);
                                    Log.d("SEGNALAZIONI", "" + segnalazioni);

                                }
                            }
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            String data = sdf.format(cal.getTime());

                            ContentValues values = new ContentValues();
                            values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, tipo);
                            values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
                            values.put(SchemaDB.Tavola.COLUMN_OGGETTO, obj);
                            values.put(SchemaDB.Tavola.COLUMN_MITTENTE, cf);
                            values.put(SchemaDB.Tavola.COLUMN_TIPO, "dip comunale"); //Tipologia di attori a cui è rivolta la segnalazione
                            values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
                            values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, "");
                            db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);


                            /*View toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);
                            Toast toast = new Toast(getApplicationContext());
                            // Set custom view in toast.
                            toast.setView(toastView);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();*/

                            c = db.rawQuery("SELECT messaggio FROM messaggi ", null);
                            if (c != null && c.getCount() > 0) {
                                if (c.moveToFirst()) {
                                    Log.d("PROVA", c.getString(0));

                                }
                            }
                            segnalazioni++;
                            Log.d("SEGNALAZIONI", "" + segnalazioni);
                            ContentValues cv = new ContentValues();
                            cv.put(SchemaDB.Tavola.COLUMN_SEGNALAZIONI, segnalazioni);
                            db.update(SchemaDB.Tavola.TABLE_NAME, cv, "id=" + id, null);

                            oggetto.setText("");
                            descrizione.setText("");
                            confermaInvio();

                        }
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sei sicuro di voler inviare la segnalazione?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;

    }

    private void confermaInvio() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(intent);
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Segnalazione Inviata\n" +
                "La tua segnalazione è stata inviata. Grazie.")
                .setPositiveButton("OK", dialogClickListener).show();
        return;

    }
}