package com.example.androidProject.DigiNews.Network;

import android.app.Application;
import android.content.Context;


public class MyApplication extends Application {
    private static MyApplication sInstance=null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
    public static MyApplication getsInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}