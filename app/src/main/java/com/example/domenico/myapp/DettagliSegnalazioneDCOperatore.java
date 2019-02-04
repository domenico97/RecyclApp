package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DettagliSegnalazioneDCOperatore extends Activity {

    TextView cfSegnalatore,dataSegnalazione,cfSegnalato,descrizioneInfrazione;
    String infrazioni,cfDaSegnalare,dataInfrazione;
    FrameLayout inviaButton;
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    String codiceFiscale = "Assessorato all'ambiente";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagli_segnalazione_dc_operatore);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        cfSegnalatore = findViewById(R.id.cfSegnalatore);
        dataSegnalazione = findViewById(R.id.dataSegnalazione);
        cfSegnalato = findViewById(R.id.cfSegnalato);
        descrizioneInfrazione = findViewById(R.id.descrizioneInfrazione);
        inviaButton = findViewById(R.id.inviaButton);

        db = MainActivity.dbHelper.getWritableDatabase();

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
        dataInfrazione = i.getStringExtra("data");
        dataSegnalazione.setText(dataInfrazione);
        cfDaSegnalare = i.getStringExtra("destinatario");
        cfSegnalato.setText(cfDaSegnalare);
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
        checkInfrazioneInviata();
    }

    private void checkInfrazioneInviata() {


        Cursor c = db.rawQuery("SELECT tipo_segnalazione,data_segnalazione,destinatario,oggetto,mittente,messaggio FROM messaggi where tipo = ?", new String[]{"cittadino"});
        if (c != null && c.getCount() > 0) {
            for (int j = 0; j < c.getCount(); j++) {
                if (c.moveToPosition(j)) {
                    Log.d("DBUG", "ciSta");
                    String tipo_segnalazione = c.getString(0);
                    String data = c.getString(1);
                    String destinatario = c.getString(2);
                    String oggetto = c.getString(3);
                    String mittente = c.getString(4);
                    String messaggio = c.getString(5);
                    Log.d("DBUG", tipo_segnalazione + data + destinatario + oggetto + messaggio);
                    if (tipo_segnalazione.equals("Sanzione") && data.equals(dataInfrazione) && destinatario.equals(cfDaSegnalare) && oggetto.equals("Sanzione") && messaggio.equals(infrazioni)) {
                        Log.d("DBUG", "giaInviata");
                        inviaButton.setVisibility(View.INVISIBLE);
                        return;
                    }


                }
            }
        }



    }


    public void inviaInfrazione(View view) {

            int id = prefs.getInt("ID",0);

            Cursor c = db.rawQuery("SELECT cf FROM utenti where id = ?", new String[]{"" + id});
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    codiceFiscale = c.getString(0);


                }
            }





        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invia Sanzione");
        builder.setMessage("Sei sicuro di voler inviare la sanzione ? ")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }

    public void back(View v) {
        finish();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    confermaInvio();
                    break;

            }
        }
    };

    DialogInterface.OnClickListener sanzioneInviata = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;

            }
        }
    };

    private void confermaInvio() {

        ContentValues values = new ContentValues();
        values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, "Sanzione");
        values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, infrazioni);
        values.put(SchemaDB.Tavola.COLUMN_OGGETTO, "Sanzione");
        values.put(SchemaDB.Tavola.COLUMN_MITTENTE, codiceFiscale);
        values.put(SchemaDB.Tavola.COLUMN_TIPO, "cittadino");
        values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, dataInfrazione);
        values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, cfDaSegnalare);
        Log.d("DBUG",""+db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sanzione Inviata\n" +
                "La sanzione è stata inviata.")
                .setPositiveButton("OK", sanzioneInviata).show();
        return;

    }
}
