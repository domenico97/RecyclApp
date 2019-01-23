package com.example.domenico.myapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

public class HomepageCittadino extends AppCompatActivity {

    CarouselView customCarouselView;
    int NUMBER_OF_PAGES = 2;
    TextView testo;
    ImageButton image;

    int[] sampleImages = {R.drawable.calendar, R.drawable.info, R.drawable.home};
    private View BottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_cittadino);
        //BottomNavigationView = findViewById(R.id.navigationView);
        customCarouselView = (CarouselView) findViewById(R.id.carouselView);
        customCarouselView.setPageCount(NUMBER_OF_PAGES);
        // set ViewListener for custom view
        customCarouselView.setViewListener(viewListener);

    }

    ViewListener viewListener = new ViewListener() {

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            testo = (TextView) findViewById(R.id.testo);
            image = findViewById(R.id.image);
            if (testo != null && image != null) {
                testo.setText("NOME UTENTE oggi si conferisce");
                image.setImageResource(sampleImages[position]);
                //set view attributes here
            }
            if(testo!=null)
            Toast.makeText(getApplicationContext(), "" + testo.getText().toString(), Toast.LENGTH_LONG).show();
            return customView;
        }
    };


}
