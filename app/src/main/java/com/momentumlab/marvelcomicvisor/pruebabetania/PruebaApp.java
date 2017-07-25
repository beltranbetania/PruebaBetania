package com.momentumlab.marvelcomicvisor.pruebabetania;

/**
 * Created by siragon on 24/07/2017.
 */

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

public class PruebaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppEventsLogger.activateApp(this);
    }
}