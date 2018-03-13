package com.example.androidProject.DigiNews.Connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectionChecker {
    Context c;
    public ConnectionChecker(Context c){
        this.c =c;
    }
    public boolean isConnected()
    {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return connected;
    }


}
