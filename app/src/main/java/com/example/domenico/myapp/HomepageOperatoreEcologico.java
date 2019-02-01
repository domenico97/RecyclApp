package com.example.domenico.myapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;


public class HomepageOperatoreEcologico extends FragmentActivity implements SimpleScannerFragment.NoticeDialogListener{

    Fragment myFragment;
    TextView nomeText;
    TextView cognomeText;
    TextView viaText;
    TextView cittaText;
    TextView infrazioniText;
    TextView puntiText;

    String nome,cognome,via,citta,infrazioni,punti,cf;
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_operatore_ecologico);
       /* myFragment = getSupportFragmentManager().findFragmentById(R.id.scanner_fragment);*/


        nomeText = findViewById(R.id.nome);
        cognomeText = findViewById(R.id.cognome);
        viaText = findViewById(R.id.via);
        cittaText = findViewById(R.id.citta);
        infrazioniText = findViewById(R.id.numeroInfrazioni);
        puntiText = findViewById(R.id.numeroPunti);

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.home_operatore_ecologico);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_operatore_ecologico:

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

        FrameLayout frameLayout = findViewById(R.id.scanner_fragment);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SimpleScannerFragment fr = new SimpleScannerFragment();
        ft.add(R.id.scanner_fragment,fr,"scannerQR");
        fr.setNoticeDialogListener(this);
        ft.commit();

    }

    /*public void avviaQRScan(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), SimpleScannerFragmentActivity.class);
        startActivity(i);
    }*/

    public void magnifyCamera(View view) {

        //DA FINIRE (Errori: doppio click per aprirla)


        Log.d("tag","magnifyCamera");
        ViewGroup.LayoutParams params = myFragment.getView().getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        myFragment.getView().setLayoutParams(params);





       view.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void resultReady(Result rawResult) {


        String s = rawResult.getText();
        if(rawResult.getBarcodeFormat().toString().equals("QR_CODE")) {
            String[] tokens = s.split("\\:");
            cf = tokens[0];
            nome = tokens[1];
            cognome = tokens[2];
            via = tokens[3];
            citta = tokens[4];
/*
            infrazioni = tokens[4];
            punti = tokens[5];*/

            nomeText.setText(nome);
            cognomeText.setText(cognome);
            viaText.setText(via);
            cittaText.setText(citta);
            /*infrazioniText.setText(infrazioni);
            puntiText.setText(punti);*/




            // infrazioni e punti vanno prelevati dal DB se si trova la corrispondenza sul codice fiscale

            db = MainActivity.dbHelper.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT cf,infrazioni,punti FROM  utenti where cf = ?", new String[]{cf});
            if (c.moveToLast()) {

                infrazioni = ""+c.getInt(1);
                punti = ""+c.getInt(2);
                    Toast.makeText(getApplicationContext(), "Punti: "+punti, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Infrazioni: "+infrazioni, Toast.LENGTH_SHORT).show();
                infrazioniText.setText(infrazioni);
                puntiText.setText(punti);


            }
            else{
                Toast.makeText(getApplicationContext(), "Nessun Risultato", Toast.LENGTH_SHORT).show();
            }



        }






    }


    public void resetScan(){
        cf=null;
        nome=null;
        cognome=null;
        infrazioni=null;
        punti=null;
        via=null;
        citta=null;
        nomeText.setText("empty");
        cognomeText.setText("empty");
        viaText.setText("empty");
        cittaText.setText("empty");
        puntiText.setText("empty");
        infrazioniText.setText("empty");

    }


    public void tuttoOk(View view) {

        if(nome!=null){

            inviaIntent(TuttoOkOperatoreEcologico.class);
            resetScan();

        }
        else{
            Toast.makeText(getApplicationContext(),"Dati non rilevati",Toast.LENGTH_SHORT).show();
        }
    }

    public void inviaIntent (Class cls){
        Intent i = new Intent();
        i.setClass(getApplicationContext(),cls);
        i.putExtra("cf",cf);
        i.putExtra("nome",nome);
        i.putExtra("cognome",cognome);
        i.putExtra("via",via);
        i.putExtra("citta",citta);
        i.putExtra("nInfrazioni",infrazioni);
        i.putExtra("nPunti",punti);
        startActivity(i);
    }


    public void infrazione(View view) {

        if(nome!=null){

            inviaIntent(InfrazioneOperatoreEcologico.class);
            resetScan();

        }
        else{
            Toast.makeText(getApplicationContext(),"Dati non rilevati",Toast.LENGTH_SHORT).show();
        }
    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonaleOperatoreEcologico.class);
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
