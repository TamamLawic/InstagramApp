package com.example.instagramapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
/**
 * Parse initialization, registers Parse models based on client and server keys.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //register Parse Models
        ParseObject.registerSubclass(Post.class);

        //set applicationId, and server server based on the values
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GNGUrK541QbB7XHjqmfr28iOocQK43bRBgCRtD7P")
                .clientKey("04ptQ9IPvBeQDCqiiXJleUi3F9z9sLwMStUlbh7s")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
