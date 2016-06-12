package com.github.curioustechizen.ago.sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class AgoSampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
