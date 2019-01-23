package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_SENDTO;

public class RecuperaPassword extends Activity {
    private EditText email,cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupera_password);
        email = findViewById(R.id.email);
        cf= findViewById(R.id.cf);


    }

    public void recuperaPassword(View v){
       /* Intent i = new Intent(ACTION_SENDTO);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT,"PROVA EMAIL APP");
        i.putExtra(Intent.EXTRA_TEXT,"la tua password è ....");
        i.setData(Uri.parse("mailto:domenico.trotta@live.it"));
        i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);*/
       if(!(email.getText().toString().equals("")) && !(cf.getText().toString().equals(""))){
            Toast.makeText(getApplicationContext(), "Password inviata al tuo indirizzo e-mail", Toast.LENGTH_LONG).show();
        }else
        Toast.makeText(getApplicationContext(), "Inserire e-mail e codice fiscale", Toast.LENGTH_LONG).show();
    }
}