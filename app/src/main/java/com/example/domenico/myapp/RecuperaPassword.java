package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_SENDTO;

public class RecuperaPassword extends Activity {
    private EditText email, cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recupera_password);
        email = findViewById(R.id.email);
        cf = findViewById(R.id.cf);


    }

    public void recuperaPassword(View v) {

        if (!(email.getText().toString().equals("")) && !(cf.getText().toString().equals(""))) {
            confermaInvio();
        } else
            errore("Inserire e-mail e codice fiscale");
    }

    private void confermaInvio() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(intent);
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Password inviata");
        builder.setMessage("La tua password è stata inviata al tuo indirizzo e-mail.").setPositiveButton("OK", dialogClickListener).show();
        return;

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


}