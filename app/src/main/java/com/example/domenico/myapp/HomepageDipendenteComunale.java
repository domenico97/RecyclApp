package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomepageDipendenteComunale extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_dipendente_comunale);
    }

    public void ModificaCalendario(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(),ModificaCalendarioDipendenteComunale.class);
        startActivity(i);
    }
}