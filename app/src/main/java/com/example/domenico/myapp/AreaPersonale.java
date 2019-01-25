package com.example.domenico.myapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AreaPersonale extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private View bottomNavigationView;
    ImageView image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_personale);
        bottomNavigationView = findViewById(R.id.navigationView);

    }

    public void back(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), HomepageCittadino.class);
        startActivity(i);
    }

    public void selectImage(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != PICK_IMAGE) return;
        if (resultCode != RESULT_OK) return;
        if (data != null) {
            try {
                InputStream inputStream = getApplication().getContentResolver().openInputStream(data.getData());
                image = findViewById(R.id.image);
                image.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

