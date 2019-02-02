package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DettagliSegnalazioneDCOperatore extends Activity {

    TextView cfSegnalatore,dataSegnalazione,cfSegnalato,descrizioneInfrazione;
    String infrazioni;
    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagli_segnalazione_dc_operatore);

        cfSegnalatore = findViewById(R.id.cfSegnalatore);
        dataSegnalazione = findViewById(R.id.dataSegnalazione);
        cfSegnalato = findViewById(R.id.cfSegnalato);
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
        cfSegnalato.setText(i.getStringExtra("destinatario"));
        infrazioni = i.getStringExtra("messaggio");


        String testo ="";
        String[] tmp = infrazioni.split("//");
        String tmpInfrazioni = tmp[0];
        String altreInformazioni = null;
        if ( tmp.length > 1){
            altreInformazioni = tmp[1];
        }

        if(!tmpInfrazioni.isEmpty()) {
            String[] listaInfrazioni = tmpInfrazioni.split("/");
            for (int x = 0; x < listaInfrazioni.length; x++) {
                testo += (x + 1) + ") " + listaInfrazioni[x] + "\n";
                if(x==listaInfrazioni.length-1)
                    testo += "\n";
            }
        }
        if(altreInformazioni!=null){
            testo+="Informazioni Aggiuntive:\n"+altreInformazioni;
        }

        descrizioneInfrazione.setText(testo);
    }

    public void back(View view) {
        finish();
    }
}
