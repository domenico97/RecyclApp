package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CambiaTipologiaRifiuto  extends FragmentActivity implements DialogConfermaCambioTipologia.NoticeDialogListener {

    TextView giornoText;
    ImageView imgView;
    ArrayList<RadioButton> mRadioButtons = new ArrayList<RadioButton>();
    private BottomNavigationView bottomNavigationView;
    String giorno;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    String tipo = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambia_tipologia_rifiuto);

        giornoText = (TextView) findViewById(R.id.giorno);
        imgView = (ImageView) findViewById(R.id.immagineGiorno);
        db = MainActivity.dbHelper.getWritableDatabase();

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.home_dipendente_comunale:
                        i.setClass(getApplicationContext(),HomepageDipendenteComunale.class);
                        startActivity(i);
                        break;
                    case R.id.avvisi_dipendente_comunale:
                        i.setClass(getApplicationContext(),HomepageDipendenteComunale.class);
                        startActivity(i);
                        break;
                    case R.id.area_personale_dipendente_comunale:
                        i.setClass(getApplicationContext(),HomepageDipendenteComunale.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });

        Intent i = getIntent();
        giornoText.setText(i.getStringExtra("dataCompleta"));
        int id = i.getIntExtra("id",-1);
        giorno = i.getStringExtra("day");
        Bitmap tmp = BitmapFactory.decodeResource(getResources(),id);
        Log.d("CALENDARIO","id = "+id+"tmp = "+tmp);
        imgView.setImageBitmap(tmp);



    }

    public void radioButtonClick(View view) {
        RadioButton cb = (RadioButton) view ;
        if(!mRadioButtons.contains(cb)) {
            Log.d("CALENDARIO","aggiunto");
            mRadioButtons.add(cb);
        }
    }


    public void annulla(View view) {
        finish();
    }

    public void salva(View view) {



        for (RadioButton x : mRadioButtons){
            if(x.isChecked()){
                tipo = x.getTag().toString();
                Log.d("CALENDARIO","button "+ tipo+" selected");
            }
        }
        if(tipo!=null){
            DialogConfermaCambioTipologia x = new DialogConfermaCambioTipologia();
            x.show(getFragmentManager(),"alert");
            x.setNoticeDialogListener(this);
        }
        else{
            Toast.makeText(getApplicationContext(),"Nessuna Scelta Effettuata",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        ContentValues cv = new ContentValues();

        cv.put(SchemaDB.Tavola.CALENDARIO_TIPOLOGIA,tipo);



        int x = db.update(SchemaDB.Tavola.CALENDARIO, cv, SchemaDB.Tavola.CALENDARIO_GIORNO + "= ?", new String[] {giorno});
        if(x!=1){
            Toast.makeText(getApplicationContext(),"Si è verificato un Errore",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Operazione effettuata con successo",Toast.LENGTH_SHORT).show();
        }

        Log.d("CALENDARIO","rows affected: "+x);



        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getApplicationContext(),"Operazione Annullata",Toast.LENGTH_SHORT).show();
    }


    public void backOE(View view) {
        finish();
    }
}
