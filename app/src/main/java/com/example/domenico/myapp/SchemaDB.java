package com.example.domenico.myapp;

import android.content.Intent;
import android.provider.BaseColumns;

import java.sql.Blob;

public class SchemaDB {


    // To prevent someone from accidentally instantiating the
    // schema class, give it an empty constructor.
    public SchemaDB() {
    }

    /* Inner class that defines the table contents */
    public static abstract class Tavola implements BaseColumns {
        public static final String TABLE_NAME = "utenti";
       // public static final String TABLE_NAME1 = "messaggi";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "nome";
        public static final String COLUMN_COGNOME = "cognome";
        public static final String COLUMN_CF = "cf";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_TELEFONO = "telefono";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_INDIRIZZO = "indirizzo";
        public static final String COLUMN_PUNTI = "punti";
        public static final String COLUMN_CONFERIMENTI = "conferimenti";
        public static final String COLUMN_INFRAZIONI = "infrazioni";
        public static final String COLUMN_SEGNALAZIONI = "segnalazioni";
        public static final String COLUMN_IMMAGINE = "immagine";
       // public static final String COLUMN_MESSAGGIO = "messaggio";
       // public static final String COLUMN_MITTENTE = "mittente";

    }

}
