package com.example.androidProject.DigiNews.TabFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidProject.DigiNews.Connection.ConnectionChecker;
import com.example.androidProject.DigiNews.Entities.NewsEntity;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.JSONErr;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.JSONParseing;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.Keys;
import com.example.androidProject.DigiNews.Network.VolleySingleton;
import com.example.androidProject.DigiNews.R;
import com.example.androidProject.DigiNews.Views.ChangedNewsAdapter;

import org.json.JSONObject;

import java.util.ArrayList;


public class TrendingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ChangedNewsAdapter adapter;
    private ArrayList<NewsEntity> dataList=new ArrayList<>();
    private TextView volleyErrorText;
    private  ConnectionChecker connectionChecker;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static TrendingFragment newInstance(){
        return new TrendingFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("NEWS", dataList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // sendJsonRequest();
        connectionChecker=new ConnectionChecker(getActivity());

        if(connectionChecker.isConnected()) {

            sendJsonRequest();
        }else{
            //changed
            Toast.makeText(getActivity(),getActivity().getString(R.string.error_network),Toast.LENGTH_LONG).show();
        }

    }

    public void createBuilder()
    {
        Log.i("createBuilder","createBuilderclicked");
        Context c=getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.connection_dialog_title));
        builder.setMessage(getString(R.string.connection_dialog_message));

        String positiveText = getString(R.string.connection_dialog_positive_button);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // sendJsonRequest();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_trending,container,false);
        volleyErrorText=(TextView)rootView.findViewById(R.id.textVolleyError);

        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.trending_fragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter=new ChangedNewsAdapter(getActivity());

        recyclerView.setAdapter(adapter);
        if(!connectionChecker.isConnected()){
            volleyErrorText.setText("No Network Detected");
            volleyErrorText.setVisibility(View.VISIBLE);
            createBuilder();

        }

        if(savedInstanceState!=null){
            dataList=savedInstanceState.getParcelableArrayList("NEWS");
            adapter.setNewsList(dataList);
        }else {
             sendJsonRequest();
        }


        return rootView;
    }

    private String getUrl(){

        return "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey="+ Keys.KeyNews.API_KEY;

    }

    private void sendJsonRequest(){

        final JSONParseing jsonParseing=new JSONParseing();
        RequestQueue requestQueue= VolleySingleton.getsInstance().getmRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, getUrl(),null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                volleyErrorText.setVisibility(View.GONE);
                dataList= jsonParseing.parseJsonResponse(jsonObject);
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                adapter.setNewsList(dataList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                JSONErr errorHandler=new JSONErr();
                errorHandler.ErrorInfo(volleyError, volleyErrorText);

            }
        });
        requestQueue.add(request);

    }


    @Override
    public void onRefresh() {

        volleyErrorText.setVisibility(View.GONE);
        if(!connectionChecker.isConnected()){
            //display dialog box
            createBuilder();

        }else {
            sendJsonRequest();
        }
    }
}
