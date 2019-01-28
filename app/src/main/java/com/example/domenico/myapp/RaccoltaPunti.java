package com.example.domenico.myapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class RaccoltaPunti extends AppCompatActivity {

    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    ListView listView;
    private String cf;
    CustomAdapter customAdapter;
    private int punti;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raccolta_punti);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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
        db = MainActivity.dbHelper.getWritableDatabase();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = prefs.getInt("ID", 0);
        Cursor c = db.rawQuery("SELECT punti FROM  utenti where id = ?", new String[]{"" + id});
        if (c.moveToLast()) {
            punti = c.getInt(0);
            //puntiTot.setText("" + c.getInt(0));


        }


    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }

    public void avviso(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Dai ti mancano solo " + (100 - punti) + " punti per raggiungere il prossimo livello !")
                .setPositiveButton("OK", dialogClickListener).show();

        return;
    }
}
