package com.example.domenico.myapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class TuttoOkOperatoreEcologico extends Activity {

   private static final int PUNTIDAAGGIUNGERE = 1;
   private SQLiteDatabase db = null;

    TextView nomeCognomeText, viaText, dataText, oraText, puntiAggiuntiText;
    String nomeCognome, via, data, ora, punti,cf;
    private BottomNavigationView bottomNavigationView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuttook_operatore_ecologico);

        db = MainActivity.dbHelper.getWritableDatabase();

        nomeCognomeText = findViewById(R.id.riepilogoNomeCognome);
        viaText = findViewById(R.id.riepilogoIndirizzo);
        dataText = findViewById(R.id.dataScan);
        oraText = findViewById(R.id.oraScan);
        puntiAggiuntiText = findViewById(R.id.puntiScan);


        Intent i = getIntent();
        nomeCognome = i.getStringExtra("nome") + " " +i.getStringExtra("cognome");
        via = i.getStringExtra("via");
        punti = i.getStringExtra("nPunti");
        cf = i.getStringExtra("cf");

        nomeCognomeText.setText(nomeCognome);
        viaText.setText(via);

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_operatore_ecologico:
                        i.setClass(getApplicationContext(),HomepageOperatoreEcologico.class);
                        startActivity(i);
                        break;
                    case R.id.segnalazioni_operatore_ecologico:
                        i.setClass(getApplicationContext(),SegnalazioniOperatoreEcologico.class);
                        startActivity(i);
                        break;
                    case R.id.avvisi_operatore_ecologico:
                        i.setClass(getApplicationContext(),AvvisiOperatoreEcologico.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });



        Calendar calendar = Calendar.getInstance();

        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH) + 1;
        int cYear = calendar.get(Calendar.YEAR);
        String selectedMonth = "" + cMonth;
        String selectedYear = "" + cYear;
        int cHour = calendar.get(Calendar.HOUR_OF_DAY);
        int cMinute = calendar.get(Calendar.MINUTE);
        int cSecond = calendar.get(Calendar.SECOND);

        String hour=cHour+"",minute=cMinute+"",second=cSecond+"";

        if(cHour<=9)
            hour="0"+cHour;
        if(cMinute<=9)
            minute="0"+cMinute;
        if(cSecond<=9)
            second="0"+cSecond;

        data = cDay+"/"+cMonth+"/"+cYear;
        ora = hour+":"+minute+":"+second;

        dataText.setText(data);
        oraText.setText(ora);
        puntiAggiuntiText.setText(PUNTIDAAGGIUNGERE+"");


        ContentValues cv = new ContentValues();
        String puntiAggiornati = Integer.parseInt(punti) + PUNTIDAAGGIUNGERE+"";
        cv.put(SchemaDB.Tavola.COLUMN_PUNTI,puntiAggiornati);



        int x = db.update(SchemaDB.Tavola.TABLE_NAME, cv, SchemaDB.Tavola.COLUMN_CF + "= ?", new String[] {cf});
        Log.d("DBUG","rows affected: "+x);
        Toast.makeText(getApplicationContext(), "Punti aggiornati: "+puntiAggiornati, Toast.LENGTH_SHORT).show();

    }

    public void back(View v) {
        finish();
    }


}
