package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DettagliSegnalazioneDCOperatore extends Activity {

    TextView cfSegnalatore,dataSegnalazione,cfSegnalato,descrizioneInfrazione;
    String infrazioni;
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dettagli_segnalazione_dc_operatore);

        cfSegnalatore = findViewById(R.id.cfSegnalatore);
        dataSegnalazione = findViewById(R.id.dataSegnalazione);
        cfSegnalato = findViewById(R.id.cfSegnalato);
        descrizioneInfrazione = findViewById(R.id.descrizioneInfrazione);

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



    public void inviaInfrazione(View view) {


/*
            Cursor c = db.rawQuery("SELECT cf FROM utenti where id = ?", new String[]{"" + id});
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    codiceFiscale = c.getString(0);


                }
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String data = sdf.format(cal.getTime());


            ContentValues values = new ContentValues();
            values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE, "Sanzione");
            values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
            values.put(SchemaDB.Tavola.COLUMN_OGGETTO, "Sanzione");
            values.put(SchemaDB.Tavola.COLUMN_MITTENTE, codiceFiscale);
            values.put(SchemaDB.Tavola.COLUMN_TIPO, "cittadino");
            values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
            values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, cf.getText().toString());
            db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);
            confermaInvio();  */

    }

    public void back(View v) {
        finish();
    }

    private void errore(String error) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attenzione");
        builder.setMessage(error).setPositiveButton("Ho capito", dialogClickListener).show();

        return;

    }

    private void confermaInvio() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                        startActivity(intent);
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sanzione Inviata\n" +
                "La sanzione è stata inviata.")
                .setPositiveButton("OK", dialogClickListener).show();
        return;

    }
}
