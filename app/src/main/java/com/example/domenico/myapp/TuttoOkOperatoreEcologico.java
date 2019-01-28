package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TuttoOkOperatoreEcologico extends Activity {


    TextView nomeCognomeText, viaText, dataText, oraText, puntiAggiuntiText;
    String nomeCognome, via, data, ora, puntiAggiunti;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuttook_operatore_ecologico);

        nomeCognomeText = findViewById(R.id.riepilogoNomeCognome);
        viaText = findViewById(R.id.riepilogoIndirizzo);
        dataText = findViewById(R.id.dataScan);
        oraText = findViewById(R.id.oraScan);
        puntiAggiuntiText = findViewById(R.id.puntiScan);


        Intent i = getIntent();
        nomeCognome = i.getStringExtra("nome") + " " +i.getStringExtra("cognome");
        via = i.getStringExtra("via");


        nomeCognomeText.setText(nomeCognome);
        viaText.setText(via);



    }


}
