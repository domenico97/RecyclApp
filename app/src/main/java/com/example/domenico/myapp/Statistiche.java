package com.example.domenico.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Statistiche extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistiche_layout);
        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_news:

                        break;
                    case R.id.navigation_info:

                        break;
                }
                return false;
            }
        });

        //Grafico a torta
        pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(15, Color.BLUE).setLabel("Q1: $10"));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel("Q2: $4"));
        pieData.add(new SliceValue(10, Color.RED).setLabel("Q3: $18"));
        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Q4: $28"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Sales in million").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);

    }

    public void back(View v) {
      finish();
    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.putExtra("ActivityPrecedente", "calendario");
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }
}