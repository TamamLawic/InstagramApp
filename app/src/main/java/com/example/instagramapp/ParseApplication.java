package com.example.instagramapp;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GNGUrK541QbB7XHjqmfr28iOocQK43bRBgCRtD7P")
                .clientKey("04ptQ9IPvBeQDCqiiXJleUi3F9z9sLwMStUlbh7s")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
