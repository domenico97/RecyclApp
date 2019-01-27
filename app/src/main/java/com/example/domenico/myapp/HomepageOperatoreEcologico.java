package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class HomepageOperatoreEcologico extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_operatore_ecologico);



    }

    public void avviaQRScan(View view) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), SimpleScannerFragmentActivity.class);
        startActivity(i);
    }
}
