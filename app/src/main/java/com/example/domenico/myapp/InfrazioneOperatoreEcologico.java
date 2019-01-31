package com.example.domenico.myapp;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InfrazioneOperatoreEcologico extends FragmentActivity implements DialogConfermaInvioInfrazione.NoticeDialogListener{

    TextView nomeCognomeText, viaText, dataText, oraText, puntiAggiuntiText;
    String nomeCognome, via, data, ora, punti,cf, infrazioniTotali="",cfOpEc;
    EditText altroEditText;
    ArrayList<String> tutteInfrazioni = new ArrayList<String>();
    ArrayList<CheckBox> mCheckBoxes = new ArrayList<CheckBox>();
    SharedPreferences prefs;
    private BottomNavigationView bottomNavigationView;

    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infrazione_operatore_ecologico);

        nomeCognomeText = findViewById(R.id.riepilogoNomeCognome);
        viaText = findViewById(R.id.riepilogoIndirizzo);
        altroEditText = findViewById(R.id.altroEditText);

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


    }


    public void checkBoxClick(View view) {


        CheckBox cb = (CheckBox) view ;
        if(!mCheckBoxes.contains(cb)) {
            Log.d("DBUG","aggiunto");
            mCheckBoxes.add(cb);
        }

    }

    public void inviaInfrazione(View view) {

        infrazioniTotali = "";
        for (String i : tutteInfrazioni){
            tutteInfrazioni.remove(i);
        }

        for (CheckBox cB : mCheckBoxes){
            if(cB.isChecked()){
                tutteInfrazioni.add(cB.getTag().toString());
            }
        }




        for(int i=0;i<tutteInfrazioni.size();i++){
            String x = tutteInfrazioni.get(i);
            infrazioniTotali += x;
            if(i!=(tutteInfrazioni.size()-1)){
                infrazioniTotali += "/";
            }
        }


        String altro = altroEditText.getText().toString();
        if(!altro.isEmpty()){
            infrazioniTotali += "//"+altro;
        }

        Toast.makeText(getApplicationContext(),infrazioniTotali,Toast.LENGTH_SHORT).show();
        if(!infrazioniTotali.isEmpty()){
            DialogConfermaInvioInfrazione x = new DialogConfermaInvioInfrazione();
            x.show(getFragmentManager(),"alert");
            x.setNoticeDialogListener(this);
        }
        else {
            Toast.makeText(getApplicationContext(),"Nessun tipo di infrazione selezionata",Toast.LENGTH_SHORT).show();
        }
        //infrazioniTotali = "";

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //INIVARE INFRAZIONE AL DIP.COMUNALE


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = prefs.getInt("ID", 0);
        db = MainActivity.dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT cf FROM utenti where id = ?", new String[]{"" + id});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                cfOpEc = c.getString(0);
                Log.d("DBUG", cfOpEc);

            }
        }




        String tipo = "Infrazione";
        String descr = infrazioniTotali;
        String obj = "Infrazione";


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String data = sdf.format(cal.getTime());

        ContentValues values = new ContentValues();
        values.put(SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE,tipo);
        values.put(SchemaDB.Tavola.COLUMN_MESSAGGIO, descr);
        values.put(SchemaDB.Tavola.COLUMN_OGGETTO, obj);
        values.put(SchemaDB.Tavola.COLUMN_MITTENTE, cfOpEc);
        values.put(SchemaDB.Tavola.COLUMN_TIPO, "dip comunale"); //Tipologia di attori a cui è rivolta la segnalazione
        values.put(SchemaDB.Tavola.COLUMN_DATA_SEGNALAZIONE, data);
        values.put(SchemaDB.Tavola.COLUMN_DESTINATARIO, cf);
        long x = db.insert(SchemaDB.Tavola.TABLE_NAME1, null, values);
        if(x!=-1){
            Toast.makeText(getApplicationContext(),"Operazione Effettuata con successo",Toast.LENGTH_SHORT).show();
            finish();
        }
        Log.d("DBUG",x+"");

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(),"Operazione Annullata",Toast.LENGTH_SHORT).show();


    }

    public void back(View v) {
        finish();
    }
}
