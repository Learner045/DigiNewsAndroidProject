package com.example.androidProject.DigiNews.Keys_and_Endpoints;

import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.androidProject.DigiNews.R;


public class JSONErr {
    public void ErrorInfo(VolleyError volleyError,TextView volleyErrorText){
        volleyErrorText.setVisibility(View.VISIBLE);
        if(volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError){

            volleyErrorText.setText(R.string.error_timeout);

        }else if(volleyError instanceof AuthFailureError){
            volleyErrorText.setText(R.string.error_auth);

        }else if(volleyError instanceof ServerError){
            volleyErrorText.setText(R.string.error_server);

        }else if(volleyError instanceof NetworkError){
            volleyErrorText.setText(R.string.error_network);

        }else if(volleyError instanceof ParseError){
            volleyErrorText.setText(R.string.error_parser);

        }

    }
}
