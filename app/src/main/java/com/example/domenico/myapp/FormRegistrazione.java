package com.example.domenico.myapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FormRegistrazione extends Activity {
    private EditText email, password, nome, cognome, telefono, indirizzo, cf;
    private DatabaseOpenHelper dbHelper;
    private CheckBox easyMode;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    private boolean selezionato= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nome = findViewById(R.id.nome);
        cognome = findViewById(R.id.cognome);
        cf = findViewById(R.id.codiceFiscale);
        indirizzo = findViewById(R.id.indirizzo);
        telefono = findViewById(R.id.mobphone);
        password = findViewById(R.id.pswrdd);
        email = findViewById(R.id.mail);
        easyMode = findViewById(R.id.easyMode);



        nome.setHintTextColor(Color.WHITE);
        cognome.setHintTextColor(Color.WHITE);
        email.setHintTextColor(Color.WHITE);
        cf.setHintTextColor(Color.WHITE);
        indirizzo.setHintTextColor(Color.WHITE);
        telefono.setHintTextColor(Color.WHITE);
        password.setHintTextColor(Color.WHITE);
    }

    public void loginForm(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), LoginForm.class);
        startActivity(i);
    }
    public void onEasyModeClick(View v){
        SharedPreferences.Editor editor = prefs.edit();
        if(selezionato){
            editor.putBoolean("EASY_MODE", false);
        }else
        editor.putBoolean("EASY_MODE", true);

        editor.commit();
    }


    public void registrati(View v) {
        boolean errore = false;
        String name = nome.getText().toString();
        if (name.equals("")) {
            nome.setHint("Inserire Nome");
            nome.setHintTextColor(Color.RED);
            errore = true;
        }
        String cognom = cognome.getText().toString();
        if (cognom.equals("")) {
            cognome.setHint("Inserire Cognome");
            cognome.setHintTextColor(Color.RED);
            errore = true;
        }
        String mail = email.getText().toString();
        if (mail.equals("")) {
            email.setHint("Inserire E-mail");
            email.setHintTextColor(Color.RED);
            errore = true;
        }
        String codiceFis = cf.getText().toString();
        if (codiceFis.equals("")) {
            cf.setHint("Inserire codice fiscale");
            cf.setHintTextColor(Color.RED);
            errore = true;
        }
        String address = cf.getText().toString();
        if (address.equals("")) {
            indirizzo.setHint("Inserire indirizzo");
            indirizzo.setHintTextColor(Color.RED);
            errore = true;
        }
        String tel = telefono.getText().toString();
        if (tel.equals("")) {
            telefono.setHint("Inserire telefono");
            telefono.setHintTextColor(Color.RED);
            errore = true;
        }
        String pass = password.getText().toString();
        if (pass.equals("")) {
            password.setHint("Inserire password");
            password.setHintTextColor(Color.RED);
            errore = true;
        }


        if (!errore) {
            db = MainActivity.dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(SchemaDB.Tavola.COLUMN_NAME, name);
            values.put(SchemaDB.Tavola.COLUMN_COGNOME, cognom);
            values.put(SchemaDB.Tavola.COLUMN_CF, codiceFis);
            values.put(SchemaDB.Tavola.COLUMN_EMAIL, mail);
            values.put(SchemaDB.Tavola.COLUMN_INDIRIZZO, address);
            values.put(SchemaDB.Tavola.COLUMN_TELEFONO, tel);
            values.put(SchemaDB.Tavola.COLUMN_TIPO, "cittadino");
            values.put(SchemaDB.Tavola.COLUMN_PASSWORD, pass);
            db.insert(SchemaDB.Tavola.TABLE_NAME, null, values);
        } else {
            Toast.makeText(getApplicationContext(), "Campi non corretti", Toast.LENGTH_LONG).show();
        }
        if (!errore) {
            Intent i = new Intent();
            i.setClass(getApplicationContext(), LoginForm.class);
            startActivity(i);
        }
    }
}