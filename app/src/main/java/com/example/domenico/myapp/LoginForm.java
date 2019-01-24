package com.example.domenico.myapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginForm extends Activity {
    //private LoginActivity.UserLoginTask mAuthTask = null;
    private EditText email, password;
    SharedPreferences prefs;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pswrdd);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    effettuaLogin();
                    return true;
                }
                return false;
            }
        });


        Button signInButton = (Button) findViewById(R.id.login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effettuaLogin();
            }
        });


    }


    public void effettuaLogin() {
       /* email = findViewById(R.id.email);
        password = findViewById(R.id.pswrdd);
         if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Inserire e-mail e password", Toast.LENGTH_LONG).show();
        } */

        Intent i = new Intent();
        i.setClass(getApplicationContext(), HomepageCittadino.class);
        startActivity(i);

       /* if (mAuthTask != null) {
            return;
        }*/
        // Reset errors.
        email.setError(null);
        password.setError(null);

        String mail = email.getText().toString();
        String passw = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passw)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        }
        if (!isPasswordValid(passw)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;

        } else if (!isEmailValid(mail)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            db = MainActivity.dbHelper.getWritableDatabase();
            String[] projection = {
                    SchemaDB.Tavola._ID,
                    SchemaDB.Tavola.COLUMN_NAME,
                    SchemaDB.Tavola.COLUMN_COGNOME,
                    SchemaDB.Tavola.COLUMN_TIPO
            };
            //String[] projection = null;

            // Specifichiamo come le vogliamo ordinare le righe
            String sortOrder = SchemaDB.Tavola.COLUMN_NAME + " ASC";

            // Definiamo la parte 'where' della query.
            String selection;
            selection = SchemaDB.Tavola.COLUMN_EMAIL + " = ? "
                    + " and "
                    + SchemaDB.Tavola.COLUMN_PASSWORD + " = ? ";

            // Specifchiamo gli argomento per i segnaposto (i ? nella stringa selection).
            String[] selectionArgs = {email.getText().toString(), password.getText().toString()};

            // Eseguiamo la query
            Cursor cursor = db.query(
                    SchemaDB.Tavola.TABLE_NAME,  // The table to query
                    projection,                  // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

               // Intent i = new Intent();
                if (cursor.getString(3).equals("cittadino")) {
                    i.setClass(getApplicationContext(), HomepageCittadino.class);
                } else if (cursor.getString(3).equals("op ecologico")) {
                     i.setClass(getApplicationContext(), HomepageOperatoreEcologico.class);
                } else if (cursor.getString(3).equals("dip comunale")) {
                    i.setClass(getApplicationContext(), HomepageDipendenteComunale.class);
                }
                // startActivity(i);

                //Salvo l'id dell'utente in una SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("ID", cursor.getInt(0));
                editor.commit();

                Toast.makeText(getApplicationContext(), "Nome:" + cursor.getString(1) + "Cognome: " + cursor.getString(2), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Credenziali non corrette", Toast.LENGTH_LONG).show();
            }
        }

    }


       /* else {

            db = MainActivity.dbHelper.getWritableDatabase();
            String[] projection = {
                    SchemaDB.Tavola._ID,
                    SchemaDB.Tavola.COLUMN_NAME,
                    SchemaDB.Tavola.COLUMN_COGNOME,
                    SchemaDB.Tavola.COLUMN_TIPO
            };
            //String[] projection = null;

            // Specifichiamo come le vogliamo ordinare le righe
            String sortOrder = SchemaDB.Tavola.COLUMN_NAME + " ASC";

            // Definiamo la parte 'where' della query.
            String selection;
            selection = SchemaDB.Tavola.COLUMN_EMAIL + " = ? "
                    + " and "
                    + SchemaDB.Tavola.COLUMN_PASSWORD + " = ? ";

            // Specifchiamo gli argomento per i segnaposto (i ? nella stringa selection).
            String[] selectionArgs = {email.getText().toString(), password.getText().toString()};

            // Eseguiamo la query
            Cursor cursor = db.query(
                    SchemaDB.Tavola.TABLE_NAME,  // The table to query
                    projection,                  // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
            cursor.moveToFirst();
            if (cursor.getColumnCount() > 0) {
                Intent i = new Intent();
                if (cursor.getString(3).equals("cittadino")) {
                    //i.setClass(getApplicationContext(), HomepageCittadino.class);
                } else if (cursor.getString(3).equals("op ecologico")) {
                    // i.setClass(getApplicationContext(), HomepageOpEcologico.class);
                } else if (cursor.getString(3).equals("dip comunale")) {
                    //i.setClass(getApplicationContext(), HomepageDipComunale.class);
                }
                // startActivity(i);

                //Salvo l'id dell'utente in una SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("ID",cursor.getInt(0));
                editor.commit();

                Toast.makeText(getApplicationContext(), "Nome:" + cursor.getString(1) + "Cognome: " + cursor.getString(2), Toast.LENGTH_LONG).show();
            }
        }
}*/

    /*private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void passwordDimenticata(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), RecuperaPassword.class);
        startActivity(i);
    }

    public void registrazione(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), FormRegistrazione.class);
        startActivity(i);
    }
}

