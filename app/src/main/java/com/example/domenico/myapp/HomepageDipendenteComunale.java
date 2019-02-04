package com.example.domenico.myapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class HomepageDipendenteComunale extends Activity {

    private BottomNavigationView bottomNavigationView;
    CarouselView customCarouselView;
    private int NUMBER_OF_PAGES = 2;
    private int newPercentage;
    private ListView listView;
    CustomAdapterSegn customAdapter;
    ArrayList<SegnalazioneBean> segnalazioni = new ArrayList<SegnalazioneBean>();
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    TextView noResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_dipendente_comunale);

        db = MainActivity.dbHelper.getWritableDatabase();



        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_dipendente_comunale:
                        break;
                    case R.id.avvisi_dipendente_comunale:
                        i.setClass(getApplicationContext(), AvvisiInviatiDipendenteComunale.class);
                        startActivity(i);
                        break;
                    case R.id.area_personale_dipendente_comunale:
                        i.setClass(getApplicationContext(), AreaPersonaleDipendenteComunale.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });


        customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        customCarouselView.setPageCount(NUMBER_OF_PAGES);

        // set ViewListener for custom view
        customCarouselView.setViewListener(viewListener);





    }


    public void inviaSanzione(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), CercaCittadino.class);
        startActivity(i);
    }


    public void statistiche(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), StatisticheDipendenteComunale.class);
        startActivity(i);
    }

    public void ModificaCalendario(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), ModificaCalendarioDipendenteComunale.class);
        startActivity(i);
    }

    public void segnalazioniRicevute(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), SegnalazioniDipendenteComunale.class);
        startActivity(i);
    }

    public void nuovoAvviso(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), NuovoAvviso.class);
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

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = null;
            // if (testo != null && image != null) {
            if (position == 0) {
                customView = getLayoutInflater().inflate(R.layout.first_carousel_page_home_dc, null);

                ProgressBar pb = customView.findViewById(R.id.progressBarDipCom);
                TextView percentage = customView.findViewById(R.id.percentage);
                TextView cityPercentage = customView.findViewById(R.id.percentageOfCity);

                newPercentage = 30;
                pb.setProgress(newPercentage);
                cityPercentage.setText(newPercentage+"");


            } else {
                customView = getLayoutInflater().inflate(R.layout.second_carousel_page_home_dc, null);

                noResult = customView.findViewById(R.id.noResult);
                listView = (ListView) customView.findViewById(R.id.mylistview);
                customAdapter = new CustomAdapterSegn(customView.getContext(), R.layout.segnalazioni_dipcom_element, new ArrayList<SegnalazioneBean>());
                listView.setAdapter(customAdapter);

                Cursor c = db.rawQuery("SELECT id,messaggio,data_segnalazione,destinatario,mittente,tipo FROM messaggi WHERE tipo = ? ", new String[]{"dip comunale"});
                if (c != null && c.getCount() > 0) {
                    noResult.setVisibility(View.GONE);
                    SegnalazioneBean segn;
                    for (int j = 0; j < c.getCount(); j++) {
                        if (c.moveToPosition(j)) {

                            segn = new SegnalazioneBean(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5));
                            segnalazioni.add(segn);

                            // cfDestinatario = c.getString(3);

                        }
                    }
                } else if (c.getCount() == 0) {
                    noResult.setVisibility(View.VISIBLE);
                    /*Toast.makeText(getApplicationContext(),"NO SEGN",Toast.LENGTH_SHORT).show();*/
                }

                for (SegnalazioneBean x : segnalazioni){

                    Log.d("DBUG",x.getDestinatario()+x.getTipo()+x.getMittente());
                    String destinatario = x.getDestinatario();

                    if(destinatario.isEmpty()){
                        customAdapter.setIsCitizen(true);
                        Log.d("DBUG","isCitizen");}
                    else{
                        customAdapter.setIsCitizen(false);
                        Log.d("DBUG","isNOTCitizen");
                    }
                    customAdapter.add(x);

                }


            }


            return customView;
        }
    };

    public void dettagliSegnalazione(View view) {

        ImageView img = view.findViewById(R.id.mostraDettagli);
        int tag = Integer.parseInt(img.getTag().toString());
        Log.d("TAG",img.getTag().toString());
        SegnalazioneBean seg;
        seg = (SegnalazioneBean) customAdapter.getItem(tag);

        /*Toast.makeText(getApplicationContext(),seg.getMittente()+seg.getDestinatario()+seg.getMessaggio()+"",Toast.LENGTH_SHORT).show();*/
        boolean x;
        if(seg.getDestinatario().isEmpty())
            x=true;
        else
            x=false;



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

    class CustomAdapterSegn extends ArrayAdapter {

        private int resource;
        private LayoutInflater inflater;
        private boolean isCitizen=true;

        public CustomAdapterSegn(Context context, int resourceId, ArrayList<SegnalazioneBean> objects) {
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
            if(!c.getDestinatario().isEmpty()){
                Log.d("DBUG","isNOTCitizen");
                operatoreLL.setVisibility(View.VISIBLE);
                operatore.setText(c.getMittente());
                cittadino.setText(c.getDestinatario());
            }
            else{
                Log.d("DBUG","isCitizen");
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