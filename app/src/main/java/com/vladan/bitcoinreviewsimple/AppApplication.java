package com.vladan.bitcoinreviewsimple;

import android.app.Application;

import com.vladan.networkchecker.InternetManager;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AppApplication extends Application {
    InternetManager internetManager;
    @Override
    public void onCreate() {
        super.onCreate();
        internetManager = InternetManager.Companion.getInternetManager(this);
        internetManager.registerInternetMonitor();

    }
}
