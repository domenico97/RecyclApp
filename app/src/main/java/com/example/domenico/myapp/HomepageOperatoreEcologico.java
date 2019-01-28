package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    String nome,cognome,via,citta,infrazioni,punti;

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
        String[] tokens = s.split("\\:");
        nome = tokens[0];
        cognome = tokens[1];
        via = tokens[2];
        citta = tokens[3];

        infrazioni =tokens[4];
        punti = tokens[5];

        nomeText.setText(nome);
        cognomeText.setText(cognome);
        viaText.setText(via);
        cittaText.setText(citta);
        infrazioniText.setText(infrazioni);
        puntiText.setText(punti);



        Toast.makeText(getApplicationContext(),rawResult.getTimestamp()+"",Toast.LENGTH_SHORT).show();
       /* for( String x : tokens){
            Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();
        }*/

    }

    public void tuttoOk(View view) {

        if(nome!=null){

        Intent i = new Intent();
        i.setClass(getApplicationContext(),TuttoOkOperatoreEcologico.class);
        i.putExtra("nome",nome);
        i.putExtra("cognome",cognome);
        i.putExtra("via",via);
        i.putExtra("citta",citta);
        i.putExtra("nInfrazioni",infrazioni);
        i.putExtra("nPunti",punti);
        startActivity(i);


        }
        else{
            Toast.makeText(getApplicationContext(),"Dati non rilevati",Toast.LENGTH_SHORT).show();
        }
    }

    public void infrazione(View view) {
    }
}
