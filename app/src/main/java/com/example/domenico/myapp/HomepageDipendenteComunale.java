package com.example.domenico.myapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

public class HomepageDipendenteComunale extends Activity {
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_dipendente_comunale);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_dipendente_comunale:
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


    public void inviaSanzione(View v) {
        /*Intent i = new Intent();
        i.setClass(getApplicationContext(), Statistiche.class);
        startActivity(i);*/
    }


    public void statistiche(View v) {
        /*Intent i = new Intent();
        i.setClass(getApplicationContext(), Statistiche.class);
        startActivity(i);*/
    }

    public void ModificaCalendario(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), ModificaCalendarioDipendenteComunale.class);
        startActivity(i);
    }

    public void segnalazioniRicevute(View v) {
        /*Intent i = new Intent();
        i.setClass(getApplicationContext(), Statistiche.class);
        startActivity(i);*/
    }

    public void nuovoAvviso(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), NuovoAvviso.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finishAffinity();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Conferma");
        builder.setMessage("Vuoi davvero uscire da RecyclApp ?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }

}