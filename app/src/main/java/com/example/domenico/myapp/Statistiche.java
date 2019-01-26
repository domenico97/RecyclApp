package com.example.domenico.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Statistiche extends AppCompatActivity {
    private DatabaseOpenHelper dbHelper;
    TextView puntiTot, segnalazioni, infrazioni, conferimentiTot;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    PieChartView pieChartView;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistiche_layout);
        bottomNavigationView = findViewById(R.id.navigationView);
        puntiTot = findViewById(R.id.numeroPuntiTotali);
        segnalazioni = findViewById(R.id.SegnalInviate);
        conferimentiTot = findViewById(R.id.confTotali);
        infrazioni = findViewById(R.id.InfrazTotali);
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

        db = MainActivity.dbHelper.getWritableDatabase();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = prefs.getInt("ID", 0);
        Cursor c = db.rawQuery("SELECT punti,conferimenti,infrazioni,segnalazioni FROM  utenti where id = ?", new String[]{"" + id});
        if (c.moveToLast()) {

            puntiTot.setText("" + c.getInt(0));
            conferimentiTot.setText("" + c.getInt(1));
            infrazioni.setText("" + c.getInt(2));
            segnalazioni.setText("" + c.getInt(3));

        }

        //Grafico a torta
        pieChartView = findViewById(R.id.chart);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(((Integer.parseInt(conferimentiTot.getText().toString())+1) * 10), Color.rgb(51, 133, 255)).setLabel(conferimentiTot.getText().toString()));
        pieData.add(new SliceValue(((Integer.parseInt(infrazioni.getText().toString())+1) * 10), Color.rgb(255, 0, 0)).setLabel(infrazioni.getText().toString()));
        pieData.add(new SliceValue(((Integer.parseInt(segnalazioni.getText().toString())+1) * 10), Color.rgb(255, 214, 51)).setLabel(segnalazioni.getText().toString()));
        pieData.add(new SliceValue(((Integer.parseInt(puntiTot.getText().toString())+1) * 10), Color.rgb(0, 179, 60)).setLabel(puntiTot.getText().toString()));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Statistiche").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);

    }

    public void back(View v) {
        finish();
    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //popupView.setBackgroundDrawable(null);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}