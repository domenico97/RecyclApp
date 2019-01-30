package com.example.domenico.myapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.zxing.Result;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class IdentificaTipologiaRifiuto extends FragmentActivity implements SimpleScannerFragment.NoticeDialogListener{

    public ListView listView;
    ResultAdapter customAdapter;
    SearchView searchBar;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences prefs;
    TextView nessunRisultatoText;


    String[] nomi = {"Fiordacqua", "Toka", "Cioccolata Migros", "Cioccolata Novi"};
    final int [] immagini ={R.drawable.plastica_result,R.drawable.plastica_result,R.drawable.carta_result,R.drawable.carta_result};
    String[] barcodes = {"8006789010157", "8006373000526", "7613269195306", "80063800215203"};
    ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identifica_tipologia_rifiuto);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        listView = (ListView) findViewById(R.id.mylistview);

        customAdapter = new ResultAdapter(this, R.layout.result_prodotto, new ArrayList<Prodotto>());

        listView.setAdapter(customAdapter);

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

        nessunRisultatoText = findViewById(R.id.noResult);
        searchBar = findViewById(R.id.searchBar);
        searchBar.setIconifiedByDefault(false);
        searchBar.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                checkQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                checkQuery(newText);
                return false;
            }
        });

        FrameLayout frameLayout = findViewById(R.id.scanner_fragment);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SimpleScannerFragment fr = new SimpleScannerFragment();
        ft.add(R.id.scanner_fragment,fr,"scannerQR");
        ft.detach(fr);
        fr.setNoticeDialogListener(this);
        ft.commit();
        toggleQr();








        for (int i = 0; i < nomi.length; i++) {
            Prodotto c = new Prodotto(nomi[i],barcodes[i],getResources().getDrawable(immagini[i]));
            prodotti.add(c);
            //customAdapter.add(c);
        }


/*
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            checkQuery(query);
            Log.d("DBUG","query: "+query);
        }*/
    }

    private void toggleQr() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment x = fm.findFragmentById(R.id.scanner_fragment);
        if(x!=null){
            if(x.isDetached()){
                Log.d("DBUG","QR is detached");
                ft.attach(x);
            }else {
                Log.d("DBUG","QR is attached");
                ft.detach(x);
            }
            ft.commit();
        }
        else{
            Log.d("DBUG","QR is NULL");
        }
    }

    private void checkQuery(String query) {

        customAdapter.clear();
        String lowQuery = query.toLowerCase();
        for (Prodotto x : prodotti){
            String barcode = x.getBarcode().toLowerCase();
            String name = x.getName().toLowerCase();
            if(barcode.contains(lowQuery) || name.contains(lowQuery)){
                nessunRisultatoText.setVisibility(View.GONE);
                customAdapter.add(x);
            }
        }
        if(customAdapter.isEmpty()){
            nessunRisultatoText.setVisibility(View.VISIBLE);
        }
    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void resultReady(Result rawResult) {

        customAdapter.clear();

        if(rawResult.getBarcodeFormat().toString().equals("EAN_13")) {
            String br = rawResult.getText();
            Log.d("DBUG","barcode: "+br);

            for (Prodotto x : prodotti){
                if(x.getBarcode().equals(br)){
                    nessunRisultatoText.setVisibility(View.GONE);
                    customAdapter.add(x);
                }
            }

        }

        if(customAdapter.isEmpty()){
            nessunRisultatoText.setVisibility(View.VISIBLE);
        }

        toggleQr();
    }


    public void onQrClick(View view) {
        toggleQr();
    }
}
