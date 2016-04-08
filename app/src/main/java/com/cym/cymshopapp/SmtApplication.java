package com.cym.cymshopapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * ========================
 * CYM
 */
public class SmtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
