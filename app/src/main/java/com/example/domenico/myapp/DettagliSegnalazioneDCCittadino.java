package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DettagliSegnalazioneDCCittadino extends Activity {

    TextView cfSegnalatore,dataSegnalazione,cfSegnalato,descrizioneInfrazione;
    String infrazioni;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagli_segnalazione_dc_cittadino);

        cfSegnalatore = findViewById(R.id.cfSegnalatore);
        dataSegnalazione = findViewById(R.id.dataSegnalazione);
        descrizioneInfrazione = findViewById(R.id.descrizioneInfrazione);

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
                        /*i.setClass(getApplicationContext(), Avvisi.class);
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


        Intent i = getIntent();
        cfSegnalatore.setText(i.getStringExtra("mittente"));
        dataSegnalazione.setText(i.getStringExtra("data"));
        infrazioni = i.getStringExtra("messaggio");
        descrizioneInfrazione.setText(infrazioni);
    }

    public void back(View view) {
        finish();
    }
}
