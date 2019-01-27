package com.example.domenico.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;


public class AreaPersonale extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private DatabaseOpenHelper dbHelper;
    TextView nome, cognome, cf, telefono, email;
    private SQLiteDatabase db = null;
    private BottomNavigationView bottomNavigationView;
    private Switch s;
    SharedPreferences prefs;
    Bitmap bitmap;
    ImageView image;
    int id;
    private byte[] immagine;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_personale);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        bottomNavigationView = findViewById(R.id.navigationView);
        s = findViewById(R.id.switchConnesso);

        if (prefs.getBoolean("RIMANI_CONNESSO", false)) {
            s.setChecked(true);
        } else {
            s.setChecked(false);
        }

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

                        break;
                    case R.id.navigation_info:
                        i.setClass(getApplicationContext(), Contatti.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });
        image = findViewById(R.id.image);
        nome = findViewById(R.id.nome);
        cf = findViewById(R.id.cf);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);

        id = prefs.getInt("ID", 0);
        Log.d("PROVA", "" + id);
        db = MainActivity.dbHelper.getWritableDatabase();


        Cursor c = db.rawQuery("SELECT nome,cognome,cf,email,telefono,immagine FROM utenti where id = ?", new String[]{"" + id});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {

                nome.setText(c.getString(0) + " " + c.getString(1));
                cf.setText(c.getString(2));
                email.setText(c.getString(3));
                telefono.setText(c.getString(4));
                immagine = c.getBlob(5);
                if (immagine != null) {
                    bitmap = BitmapFactory.decodeByteArray(immagine, 0, immagine.length);
                    image.setImageBitmap(bitmap);
                }
            }
        }
    }

    public void back(View v) {
        finish();
    }

    public void logout(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent i = new Intent();
                        i.setClass(getApplicationContext(), LoginForm.class);
                        startActivity(i);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Stai per uscire da RecyclApp. Sei sicuro?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
    }

    public void selectImage(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vuoi selezionare una nuova immagine del profilo?")
                .setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        return;
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
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                db = MainActivity.dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(SchemaDB.Tavola.COLUMN_IMMAGINE, stream.toByteArray());
                db.update(SchemaDB.Tavola.TABLE_NAME, cv, "id=" + id, null);

                /*bitmap = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);
                image.setImageBitmap(bitmap);*/

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rimaniConnesso(View v) {
        SharedPreferences.Editor editor = prefs.edit();

        if (!prefs.getBoolean("RIMANI_CONNESSO", false)) {
            s.setChecked(true);
            editor.putBoolean("RIMANI_CONNESSO", true);
        } else {
            s.setChecked(false);
            editor.putBoolean("RIMANI_CONNESSO", false);
        }

        editor.putString("TIPO", "cittadino");
        editor.commit();
    }
}

