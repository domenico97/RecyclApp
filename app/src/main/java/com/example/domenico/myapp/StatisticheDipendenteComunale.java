package com.example.domenico.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class StatisticheDipendenteComunale extends AppCompatActivity {

    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    PieChartView pieChartView;
    ListView listView;
    private String cf;
    CustomAdapter customAdapter;
    int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistiche_dipendente_comunale);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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
        pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(((float) 6.80), Color.rgb(118, 165, 175)).setLabel("Vetro"));
        pieData.add(new SliceValue(((float) 37.68), Color.rgb(53, 28, 117)).setLabel("Plastica"));
        pieData.add(new SliceValue(((float) 14.58), Color.rgb(255, 153, 0)).setLabel("Umido"));
        pieData.add(new SliceValue(((float) 12.47), Color.rgb(43, 120, 228)).setLabel("Alluminio"));
        pieData.add(new SliceValue(((float) 13.57), Color.rgb(241, 194, 50)).setLabel("Indifferenziato"));
        pieData.add(new SliceValue(((float) 14.89), Color.rgb(142, 124, 195)).setLabel("Carta e cartone"));


        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartView.setPieChartData(pieChartData);


    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonaleDipendenteComunale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }
}
