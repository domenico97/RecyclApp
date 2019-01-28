package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

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



        Calendar calendar = Calendar.getInstance();

        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH) + 1;
        int cYear = calendar.get(Calendar.YEAR);
        String selectedMonth = "" + cMonth;
        String selectedYear = "" + cYear;
        int cHour = calendar.get(Calendar.HOUR);
        int cMinute = calendar.get(Calendar.MINUTE);
        int cSecond = calendar.get(Calendar.SECOND);

        data = cDay+"/"+cMonth+"/"+cYear;
        ora = cHour+":"+cMinute+":"+cSecond;

        dataText.setText(data);
        oraText.setText(ora);

    }


}