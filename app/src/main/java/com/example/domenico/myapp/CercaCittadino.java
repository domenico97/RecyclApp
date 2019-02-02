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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class CercaCittadino extends Activity {
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SearchView searchView;
    CustomAdapterRicerche customAdapter;
    TextView ultimeRicerche;
    ListView listView;
    private int elem = 0;
    private int idRicerca;
    SharedPreferences.Editor editor;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cerca_cittadino);

        db = MainActivity.dbHelper.getWritableDatabase();
        searchView = findViewById(R.id.searchCittadino);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        if (prefs.getInt("ID_RICERCA", 0) == 0) {
            editor.putInt("ID_RICERCA", 0);
            editor.commit();
        }


        ultimeRicerche = findViewById(R.id.ultimeRicerche);
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

        listView = (ListView) findViewById(R.id.listRicerche);
        customAdapter = new CustomAdapterRicerche(this, R.layout.ultima_ricerca, new ArrayList<Ricerca>());
        listView.setAdapter(customAdapter);

        db = MainActivity.dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT cf,nome,cognome FROM ricerche", null);
        if (c != null && c.getCount() > 0) {
            Ricerca r;
            elem = c.getCount();
            for (int j = 0; j < c.getCount(); j++) {
                if (c.moveToPosition(j)) {
                    r = new Ricerca(c.getString(0), c.getString(1), c.getString(2));
                    Log.d("PROVA5", c.getString(0));
                    customAdapter.add(r);
                }

            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                db = MainActivity.dbHelper.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT cf,nome,cognome FROM utenti WHERE cf = ?", new String[]{query});
                if (c != null && c.getCount() > 0) {
                    if (c.moveToFirst()) {
                        Cursor z = db.rawQuery("SELECT cf,nome,cognome FROM ricerche WHERE cf = ?", new String[]{query});
                        if (z == null || z.getCount() == 0) {
                            Ricerca r = new Ricerca(c.getString(0), c.getString(1), c.getString(2));
                            if (elem == 4) {
                                idRicerca = prefs.getInt("ID_RICERCA", 0);
                                idRicerca++;
                                editor.putInt("ID_RICERCA", idRicerca);
                                Cursor f = db.rawQuery("SELECT cf,nome,cognome FROM ricerche WHERE id = ?", new String[]{"" + idRicerca});
                                if (f != null && f.getCount() > 0) {
                                    if (f.moveToFirst()) {
                                        Ricerca d = new Ricerca(f.getString(0), f.getString(1), f.getString(2));
                                        customAdapter.remove(d);
                                    }
                                }
                                db.delete(SchemaDB.Tavola.TABLE_NAME2, SchemaDB.Tavola._ID + "=?", new String[]{"" + idRicerca});
                                ContentValues valori = new ContentValues();
                                valori.put(SchemaDB.Tavola.COLUMN_CF, c.getString(0));
                                valori.put(SchemaDB.Tavola.COLUMN_NAME, c.getString(1));
                                valori.put(SchemaDB.Tavola.COLUMN_COGNOME, c.getString(2));
                                db.insert(SchemaDB.Tavola.TABLE_NAME2, null, valori);
                                customAdapter.add(r);
                                listView = (ListView) findViewById(R.id.listRicerche);
                                customAdapter = new CustomAdapterRicerche(getApplicationContext(), R.layout.ultima_ricerca, customAdapter.getObjects());
                                listView.setAdapter(customAdapter);
                                Intent i = new Intent();
                                i.putExtra("CODFISCALE", query);
                                i.setClass(getApplicationContext(), DettagliCittadino.class);
                                startActivity(i);

                            } else if (elem < 4) {
                                ContentValues valori = new ContentValues();
                                valori.put(SchemaDB.Tavola.COLUMN_CF, c.getString(0));
                                valori.put(SchemaDB.Tavola.COLUMN_NAME, c.getString(1));
                                valori.put(SchemaDB.Tavola.COLUMN_COGNOME, c.getString(2));
                                db.insert(SchemaDB.Tavola.TABLE_NAME2, null, valori);
                                elem++;
                                customAdapter.add(r);
                                Intent i = new Intent();
                                i.putExtra("CODFISCALE", c.getString(0));
                                i.setClass(getApplicationContext(), DettagliCittadino.class);
                                startActivity(i);
                            }

                        }
                    }
                } else
                    errore("Codice Fiscale non trovato");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listView!= null) {
            listView.invalidateViews();
        }
        customAdapter.notifyDataSetChanged();
       /* customAdapter = new CustomAdapterRicerche(this, R.layout.ultima_ricerca, customAdapter.getObjects());
        listView.setAdapter(customAdapter);*/

    }

    @Override
    public void onRestart() {
        super.onRestart();
        if (listView!= null) {
            listView.invalidateViews();
        }
        customAdapter.notifyDataSetChanged();
       /* customAdapter = new CustomAdapterRicerche(this, R.layout.ultima_ricerca, customAdapter.getObjects());
        listView.setAdapter(customAdapter);*/
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listView!= null) {
            listView.invalidateViews();
        }
        customAdapter.notifyDataSetChanged();
       /* customAdapter = new CustomAdapterRicerche(this, R.layout.ultima_ricerca, customAdapter.getObjects());
        listView.setAdapter(customAdapter);*/
    }

    public void dettagliCittadino(View v) {
        int position = Integer.parseInt(v.getTag().toString());
        Ricerca c = (Ricerca) customAdapter.getItem(position);
        Intent i = new Intent();
        Log.d("CF", c.getCf());
        i.putExtra("CODFISCALE", c.getCf());
        i.setClass(getApplicationContext(), DettagliCittadino.class);
        startActivity(i);
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

    public void back(View v) {
        finish();
    }

}

