package com.example.domenico.myapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;


public class AreaPersonale extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private DatabaseOpenHelper dbHelper;
    TextView nome, cognome, cf, telefono, email;
    private View bottomNavigationView;
    private SQLiteDatabase db = null;
    SharedPreferences prefs;
    Bitmap bitmap;
    ImageView image;
    int id;
    boolean img_save = false;
    private byte[] imamagine;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_personale);
        bottomNavigationView = findViewById(R.id.navigationView);
        image = findViewById(R.id.image);
        nome = findViewById(R.id.nome);
        cf = findViewById(R.id.cf);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        id = prefs.getInt("ID", 0);

        db = MainActivity.dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT nome,cognome,cf,email,telefono,immagine FROM  utenti where id = ?", new String[]{"" + id});
        if (c.moveToLast()) {

            nome.setText(c.getString(0) + " " + c.getString(1));
            cf.setText(c.getString(2));
            email.setText(c.getString(3));
            telefono.setText(c.getString(4));
            imamagine = c.getBlob(5);
        }
        /*bitmap = BitmapFactory.decodeByteArray(immagine, 0, immagine.length);
        image.setImageBitmap(bitmap);*/


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
                bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                db = MainActivity.dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(SchemaDB.Tavola.COLUMN_IMMAGINE, stream.toByteArray());
                db.update(SchemaDB.Tavola.TABLE_NAME, cv, "id=" + id, null);
                img_save = true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

