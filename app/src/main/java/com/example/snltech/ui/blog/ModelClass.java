package com.example.snltech.ui.blog;

import android.graphics.Bitmap;
import android.net.Uri;

public class ModelClass {
    private Bitmap bp;
    private Uri filepath;

    public ModelClass(Bitmap bp, Uri filepath) {
        this.bp=bp;
        this.filepath=filepath;
    }

    public void setBp(Bitmap bp) {
        this.bp = bp;
    }

    public Bitmap getBp() {
        return bp;
    }

    public void setFilepath(Uri filepath) {
        this.filepath = filepath;
    }

    public Uri getFilepath() {
        return filepath;
    }
}