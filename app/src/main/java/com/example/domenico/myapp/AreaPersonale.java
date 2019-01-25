package com.example.domenico.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class AreaPersonale extends AppCompatActivity {
    private View bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_personale);
        bottomNavigationView = findViewById(R.id.navigationView);

    }

    public void back(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), HomepageCittadino.class);
        startActivity(i);
    }

}

