package com.example.domenico.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {


    final static String[] columns = {
            SchemaDB.Tavola._ID,
            SchemaDB.Tavola.COLUMN_NAME,
            SchemaDB.Tavola.COLUMN_COGNOME,
            SchemaDB.Tavola.COLUMN_CF,
            SchemaDB.Tavola.COLUMN_PASSWORD,
            SchemaDB.Tavola.COLUMN_EMAIL,
            SchemaDB.Tavola.COLUMN_INDIRIZZO,
            SchemaDB.Tavola.COLUMN_TIPO,
            SchemaDB.Tavola.COLUMN_TELEFONO,
            SchemaDB.Tavola.COLUMN_PUNTI,
            SchemaDB.Tavola.COLUMN_CONFERIMENTI,
            SchemaDB.Tavola.COLUMN_IMMAGINE,
            SchemaDB.Tavola.COLUMN_INFRAZIONI,
            SchemaDB.Tavola.COLUMN_SEGNALAZIONI,
            SchemaDB.Tavola.COLUMN_MESSAGGIO,
          SchemaDB.Tavola.COLUMN_MITTENTE,
            SchemaDB.Tavola.COLUMN_OGGETTO,
            SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE

    };

    final private static String CREATE_CMD =
            "CREATE TABLE " + SchemaDB.Tavola.TABLE_NAME + " ("
                    + SchemaDB.Tavola._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SchemaDB.Tavola.COLUMN_NAME + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_COGNOME + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_CF + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_PASSWORD + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_EMAIL + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_INDIRIZZO + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_TIPO + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_PUNTI + " INTEGER DEFAULT 0, "
                    + SchemaDB.Tavola.COLUMN_CONFERIMENTI + " INTEGER DEFAULT 0, "
                    + SchemaDB.Tavola.COLUMN_INFRAZIONI + " INTEGER DEFAULT 0, "
                    + SchemaDB.Tavola.COLUMN_SEGNALAZIONI + " INTEGER DEFAULT 0, "
                    + SchemaDB.Tavola.COLUMN_IMMAGINE + " BLOB, "
                    + SchemaDB.Tavola.COLUMN_TELEFONO + " TEXT NOT NULL); ";


    final private static String CREATE_CMD1 =
            "CREATE TABLE " + SchemaDB.Tavola.TABLE_NAME1 + " ("
                    + SchemaDB.Tavola._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SchemaDB.Tavola.COLUMN_MESSAGGIO + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_MITTENTE + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_OGGETTO + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_TIPO_SEGNALAZIONE + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_TIPO + " TEXT NOT NULL); ";


    final private static Integer VERSION = 1;
    final private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context,SchemaDB.Tavola.TABLE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
        db.execSQL(CREATE_CMD1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // non serve in questo esempio, ma deve esserci
    }

    //Questo metodo serve per cancellare il database
    //Non viene usato in questo esempio
    void deleteDatabase() {
        Log.d("DEBUG", "Deleting database " + SchemaDB.Tavola.TABLE_NAME);
        context.deleteDatabase(SchemaDB.Tavola.TABLE_NAME);
    }
}
