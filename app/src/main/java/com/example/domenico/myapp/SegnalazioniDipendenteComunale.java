package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;

public class SegnalazioniDipendenteComunale extends FragmentActivity {

    private ListView listView;
    SegnalazioniAdapter customAdapter;
    Button mCittadinoButton,mOperatoreButton;
    FrameLayout mFragmentContainer;
    private BottomNavigationView bottomNavigationView;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    ArrayList<SegnalazioneBean> segnalazioni = new ArrayList<SegnalazioneBean>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segnalazioni_dipendente_comunale);

        db = MainActivity.dbHelper.getWritableDatabase();

        mCittadinoButton = findViewById(R.id.cittadinoButton);
        mCittadinoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraSegnalazioni("cittadino");
            }
        });

        mOperatoreButton = findViewById(R.id.operatoreButton);
        mOperatoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostraSegnalazioni("operatore");
            }
        });

        listView = (ListView) findViewById(R.id.mylistview);

        customAdapter = new SegnalazioniAdapter(this, R.layout.segnalazioni_dipcom_element,new ArrayList<SegnalazioneBean>());

        listView.setAdapter(customAdapter);

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
                        i.setClass(getApplicationContext(), Contatti.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });







        Cursor c = db.rawQuery("SELECT id,messaggio,data_segnalazione,destinatario,mittente,tipo FROM messaggi WHERE tipo = ? ", new String[]{"dip comunale"});
        if (c != null && c.getCount() > 0) {
            SegnalazioneBean segn;
            for (int j = 0; j < c.getCount(); j++) {
                if (c.moveToPosition(j)) {
                    // cfDestinatario = c.getString(3);
                    segn = new SegnalazioneBean(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5));
                    segnalazioni.add(segn);
                }
            }
        } else if (c.getCount() == 0) {
            /*Toast.makeText(getApplicationContext(),"NO SEGN",Toast.LENGTH_SHORT).show();*/
        }


        mostraSegnalazioni("operatore");

    }

    private void mostraSegnalazioni(String tipo) {

        customAdapter.clear();

        if(tipo.equals("cittadino")){
            customAdapter.setIsCitizen(true);
            for(SegnalazioneBean x : segnalazioni){
                if(x.getDestinatario().equals("")){
                    customAdapter.add(x);
                }
            }
        }
        else if(tipo.equals("operatore")){
            customAdapter.setIsCitizen(false);
            for(SegnalazioneBean x : segnalazioni){
                if(!x.getDestinatario().isEmpty()){
                    customAdapter.add(x);
                }
            }
        }

    }

    public void dettagliSegnalazione(View view) {

        ImageView img = view.findViewById(R.id.mostraDettagli);
        int tag = Integer.parseInt(img.getTag().toString());
        Log.d("TAG",img.getTag().toString());
        SegnalazioneBean seg;
        seg = (SegnalazioneBean) customAdapter.getItem(tag);

        /*Toast.makeText(getApplicationContext(),seg.getMittente()+seg.getDestinatario()+seg.getMessaggio()+"",Toast.LENGTH_SHORT).show();*/
        boolean x = customAdapter.getIsCitizen();

        Intent i = new Intent();
        i.putExtra("mittente",seg.getMittente());
        i.putExtra("destinatario",seg.getDestinatario());
        i.putExtra("messaggio",seg.getMessaggio());
        i.putExtra("data",seg.getDataCreazione());
        if(x){
            i.setClass(getApplicationContext(),DettagliSegnalazioneDCCittadino.class);
        }
        else {
            i.setClass(getApplicationContext(),DettagliSegnalazioneDCOperatore.class);
        }

        startActivity(i);
    }


     class SegnalazioniAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater inflater;
        private boolean isCitizen=true;

        public SegnalazioniAdapter(Context context, int resourceId, ArrayList<SegnalazioneBean> objects) {
            super(context, resourceId, objects);
            resource = resourceId;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            if (v == null) {

                v = inflater.inflate(R.layout.segnalazioni_dipcom_element, null);
            }
            SegnalazioneBean c = (SegnalazioneBean) getItem(position);

            LinearLayout operatoreLL;
            TextView operatore;
            TextView cittadino;
            ImageView dettagli;

            operatoreLL = (LinearLayout) v.findViewById(R.id.operatoreLL);
            operatore = (TextView) v.findViewById(R.id.operatoreCF);
            cittadino = (TextView) v.findViewById(R.id.cittadinoCF);
            dettagli= v.findViewById(R.id.mostraDettagli);
            //Mostro i dati del contatto c
            /* Populate the row's xml with info from the item */
            if(!isCitizen){
                operatoreLL.setVisibility(View.VISIBLE);
                operatore.setText(c.getMittente());
                cittadino.setText(c.getDestinatario());
            }
            else{
                operatoreLL.setVisibility(View.GONE);
                cittadino.setText(c.getMittente());
            }

            dettagli.setTag(position);
            operatore.setTag(position);
            cittadino.setTag(position);
            return v;
        }

        public boolean getIsCitizen() {
            return isCitizen;
        }

        public void setIsCitizen(boolean citizen) {
            isCitizen = citizen;
        }
    }

    public void back(View view) {
        finish();
    }


}
