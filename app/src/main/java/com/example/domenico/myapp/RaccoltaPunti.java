package com.example.domenico.myapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.util.Random;

public class RaccoltaPunti extends AppCompatActivity {

    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    TextView puntiTotali, puntiDaInizioAnno, risparmio, puntiNextLevel, tariIniziale, bonus, tariDaPagare;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    ListView listView;
    private String cf;
    private int punti;
    int livello = 1;
    String[] descriptionData = {"Liv.1", "Liv.2", "Liv.3", "liv.4", "Liv.5"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raccolta_punti);
        risparmio = findViewById(R.id.risparmio);
        tariDaPagare = findViewById(R.id.tariDaPagare);
        puntiDaInizioAnno = findViewById(R.id.puntiDaInizioAnno);
        bonus = findViewById(R.id.bonus);
        puntiNextLevel = findViewById(R.id.puntiNextLevel);
        puntiTotali = findViewById(R.id.puntiTotali);
        tariIniziale = findViewById(R.id.tariIniziale);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        i.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_news:
                        i.setClass(getApplicationContext(), AvvisiCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_info:
                        i.setClass(getApplicationContext(), Contatti.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
        db = MainActivity.dbHelper.getWritableDatabase();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = prefs.getInt("ID", 0);
        Cursor c = db.rawQuery("SELECT punti FROM  utenti where id = ?", new String[]{"" + id});
        if (c.moveToLast()) {
            punti = c.getInt(0);
        }

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

        stateProgressBar.setStateDescriptionTypeface("fonts/RobotoSlab-Light.ttf");
        stateProgressBar.setStateNumberTypeface("fonts/Questrial-Regular.ttf");

        if (punti >= 100) {
            livello = 2;
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
        }  if (punti >= 200) {
            livello = 3;
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
        } if (punti >= 300) {
            livello = 4;
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
        }  if (punti >= 400) {
            livello = 5;
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
        }
        puntiTotali.setText("" + punti);
        puntiDaInizioAnno.setText("" + punti);
        puntiNextLevel.setText("" + getPuntiNextLevel(punti));
        Random r = new Random();
        int low = 150;
        int high = 700;
        int tari = r.nextInt(high - low) + low;

        tariIniziale.setText("" + tari + " €");
        bonus.setText("" + (punti * 0.45)+" €");
        tariDaPagare.setText("" + (tari - (punti * 0.45)) + " €");
        float result = (float) (tari - (tari - (punti * 0.45))) / tari;

        risparmio.setText("" + String.format("%.2f", result*100));

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {

                if (stateNumber == livello) {
                    avviso("Complimenti hai già raggiunto il " + stateNumber + "° livello!");

                } else if(stateNumber>livello){
                    avviso(stateNumber + "° Livello.Ti mancano "+getPuntiNextLevel(punti)+" punti.");
                }
                else {
                    avviso(stateNumber + "° Livello. Hai " + punti + " punti");
                }


            }
        });


    }

    public int getPuntiNextLevel(int punti) {
        int liv = livello + 1;
        if (liv == 2) punti = 100 - punti;
        else if (liv == 3) punti = 200 - punti;
        else if (liv == 4) punti = 300 - punti;
        else if (liv == 5) punti = 400 - punti;
        return punti;
    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }


    public void avviso(String v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(v)
                .setPositiveButton("OK", dialogClickListener).show();

        return;
    }
}
