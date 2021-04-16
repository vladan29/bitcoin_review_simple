package com.vladan.bitcoinreviewsimple;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.vladan.networkchecker.InternetManager;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class AppApplication extends Application {
    InternetManager internetManager;
    FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        internetManager = InternetManager.getInternetManager(this);
        internetManager.registerInternetMonitor();

    }
}
