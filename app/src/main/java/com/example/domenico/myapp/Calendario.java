package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class Calendario extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario_utente);
        bottomNavigationView = findViewById(R.id.navigationView);
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

                        break;
                }
                return false;
            }
        });
    }

    public void back(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), HomepageCittadino.class);
        startActivity(i);
    }

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.putExtra("ActivityPrecedente","calendario");
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }
}