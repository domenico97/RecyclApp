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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends Activity {


    private SQLiteDatabase db = null;
    public static DatabaseOpenHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private SimpleCursorAdapter adapterSelected;
    SharedPreferences prefs;
    ArrayList<Utente> utenti = new ArrayList<>();
    String[] giorni = {"Sun","Mon_Odd","Mon_Even","Tue","Wed","Thu","Fri","Sat"};
    String[] tipologia = {"umido","indifferenziato","alluminio","umido","plastica","cartonevetro","umido","nonconferire"};

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
            utenti.add(new Utente("Domenico", "Trotta", "VLLGLI79A41H703C", "domenico.trotta@live.it", "via Roma Fisciano", "dom1997", "3894552124", "cittadino"));
            utenti.add(new Utente("Marco", "Giuliani", "GLNMRC74M06H703X", "marco.giuliani@gmail.com", "via Toscanello Baronissi", "marco74", "3297856896", "op ecologico"));
            Messaggio x = new Messaggio(0, "ciao", "VLLGLI79A41H703B", "cittadino", "22/10/2018", "PROVA", "VLLGLI79A41H703C", "Multa");
            Messaggio y = new Messaggio(0, "avviso", "VLLGLI79A41H703B", "op ecologico", "22/10/2018", "Avviso", "", "Multa");

            ContentValues values = new ContentValues();
            values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, x.getData());
            values.put(SchemaDB.Tavola.COLUMN_MITTENTE, x.getMittente());
            values.put(SchemaDB.Tavola.COLUMN_OGGETTO, x.getOggetto());
            values.put(SchemaDB.Tavola.COLUMN_TIPO, x.getTipo());
            values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, x.getDestinatario());
            values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, x.getTipo_segnalazione());
            values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, x.getMessaggio());
            db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);


            ContentValues valori = new ContentValues();
            valori.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, y.getData());
            valori.put(SchemaDB.Tavola.COLUMN_MITTENTE, y.getMittente());
            valori.put(SchemaDB.Tavola.COLUMN_OGGETTO, y.getOggetto());
            valori.put(SchemaDB.Tavola.COLUMN_TIPO, y.getTipo());
            valori.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, y.getDestinatario());
            valori.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, y.getTipo_segnalazione());
            valori.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, y.getMessaggio());
            long controllo = db.insert(SchemaDB.Tavola.TABLE_NAME1, null, valori);
            Log.d("PROVA",controllo+"");


            for (int i = 0; i < utenti.size(); i++) {
                values = new ContentValues();
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

            for(int i=0; i< giorni.length;i++){
                ContentValues cv = new ContentValues();
                cv.put(SchemaDB.Tavola.CALENDARIO_GIORNO,giorni[i]);
                cv.put(SchemaDB.Tavola.CALENDARIO_TIPOLOGIA,tipologia[i]);
                long test = db.insert(SchemaDB.Tavola.CALENDARIO, null, cv);
                Log.d("TEST",""+test);

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
            /*int id = prefs.getInt("ID", 0);
            Log.d("PROVA", "" + id);
            db = MainActivity.dbHelper.getWritableDatabase();
            int connesso;

            Cursor c = db.rawQuery("SELECT connesso FROM utenti where id = ?", new String[]{"" + id});
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    connesso = c.getInt(0);

                }*/
                Intent z = new Intent();
                if (prefs.getBoolean("RIMANI_CONNESSO", false)==true) {
                    if (prefs.getString("TIPO", "").equals("cittadino")) {
                        z.setClass(getApplicationContext(), HomepageCittadino.class);
                    } else if (prefs.getString("TIPO", "").equals("op ecologico")) {
                        z.setClass(getApplicationContext(), HomepageOperatoreEcologico.class);
                    } else if (prefs.getString("TIPO", "").equals("dip comunale")) {
                        z.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                    }

                } else {
                    z.setClass(getApplicationContext(), LoginForm.class);

                }
                startActivity(z);
            }


        }


    }
