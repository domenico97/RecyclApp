package com.example.domenico.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class VisualizzaAvvisoDipendenteComunale extends AppCompatActivity {


    TextView nomeAvviso, dataAvviso, destinatario, descrizione;
    private BottomNavigationView bottomNavigationView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizza_avviso_dipendente_comunale);

        nomeAvviso = findViewById(R.id.nomeAvviso);
        dataAvviso = findViewById(R.id.dataAvviso);
        destinatario = findViewById(R.id.destinatario);
        descrizione = findViewById(R.id.descrizione);

        Intent i = getIntent();
        nomeAvviso.setText(i.getStringExtra("OGGETTO"));
        dataAvviso.setText(i.getStringExtra("DATA"));
        descrizione.setText(i.getStringExtra("DESCRIZIONE"));
        destinatario.setText(i.getStringExtra("DESTINATARIO"));

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
                        i.setClass(getApplicationContext(),AvvisiInviatiDipendenteComunale.class);
                        startActivity(i);
                        break;
                    case R.id.area_personale_dipendente_comunale:
                        i.setClass(getApplicationContext(),AreaPersonaleDipendenteComunale.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });


    }

    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonaleDipendenteComunale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }
}
