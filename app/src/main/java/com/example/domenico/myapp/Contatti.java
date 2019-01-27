package com.example.domenico.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class Contatti extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private DatabaseOpenHelper dbHelper;
    TextView nome, cognome, cf, telefono, email;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    Bitmap bitmap;
    ImageView image;
    int id;
    private byte[] immagine;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contatti);

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setSelectedItemId(R.id.navigation_info);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_news:

                        break;
                    case R.id.navigation_info:
                       //Siamo già in questa pagina
                        break;
                }
                return false;
            }
        });




    }
}