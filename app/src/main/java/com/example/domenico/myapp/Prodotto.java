package com.example.domenico.myapp;

import android.graphics.drawable.Drawable;

public class Prodotto {
    private String name;
    public String barcode;
    private Drawable picture;

    public Prodotto(String n, String b, Drawable f) {
            this.name = n;
            this.barcode = b;
            this.picture = f;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }
}
