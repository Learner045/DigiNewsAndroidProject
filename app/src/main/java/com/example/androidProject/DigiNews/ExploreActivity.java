package com.example.androidProject.DigiNews;

import android.content.Context;
import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidProject.DigiNews.Connection.ConnectionChecker;
import com.example.androidProject.DigiNews.Entities.NewsEntity;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.Constants;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.JSONErr;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.JSONParseing;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.Keys;
import com.example.androidProject.DigiNews.Network.VolleySingleton;
import com.example.androidProject.DigiNews.Views.ChangedNewsAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONObject;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    private String spinnerItems[];
    private ChangedNewsAdapter adapter;
    private ArrayList<NewsEntity> dataList=new ArrayList<>();
    private String newsSource=Constants.DEFAULT_SOURCE_GENERAL;
    private TextView volleyErrorText;
    private  int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

       //CATEGORY NO. TO BE EXPLORED
        count=getIntent().getIntExtra(Constants.IMAGE_EXPLORED,0);
        setSpinnerArray(count);

        ConnectionChecker connectionChecker = new ConnectionChecker(this);
        if(!connectionChecker.isConnected())createBuilder();
        sendJsonRequest();

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);

        spinner.setItems(spinnerItems);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
               // Log.i("item cliked", position + ": " + item);
               String src= getSourceIndex(item);
                if(src!=null) {
                    newsSource =src;
                }
                sendJsonRequest();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.explore_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ChangedNewsAdapter(this);
        recyclerView.setAdapter(adapter);
        volleyErrorText= (TextView) findViewById(R.id.volleyError);



        if(savedInstanceState!=null){
            dataList=savedInstanceState.getParcelableArrayList("NEWS");
            adapter.setNewsList(dataList);
        }else {
            sendJsonRequest();
        }


    }
    private String getSourceIndex(String item){
        if(count==0) {//GENERAL

            switch (item) {
                case "USA Today":return "usa-today";
                case "Time":return "time";
                case "The Washington Post":return "the-washington-post";
                case "The Times Of India":return "the-times-of-india";
                case "The Telegraph":return "the-telegraph";
                case "The New York Times":return "the-new-york-times";
                case "The Huffington Post":return "the-huffington-post";
                case "The Hindu":return "the-hindu";
                case "The Guardian":return "the-guardian-uk";
                case "Reuters":return "reuters";
                case "Reddit":return "reddit-r-all";
                case "New York Magazine":return"new-york-magazine";
                case "Newsweek":return "newsweek";
                case "Mirror Metro":return "mirror metro";
                case "Independent":return "independent";
                case "BBC News":return "bbc-news";
                case "Associated Press":return "associated-press";
                case "Al Jazeera English":return "al-jazeera-english";
                default:return "abc-news-au";

            }
        }else if(count==1){//ENTER
            switch(item){
                case "Buzzfeed":return "buzzfeed";
                case "Daily Mail":return "daily-mail";
                case "Entertainment Weekly":return "entertainment-weekly";
                case "Mashable":return "mashable";
                default:return "the-lad-bible";
            }

        }else if(count==2){//TECHNOLOGY
            switch(item){

                case "The Verge":return "the-verge";
                case "TechCrunch":return "techcrunch";
                case "TechRadar":return "techradar";
                case "Recode":return "recode";
                case "Hacker News":return "hacker-news";
                case "Engadget":return "engadget";
                default:return "ars-technica";
            }

        }else if(count==3){//BUSINESS
            switch (item) {
                case "Business Insider":return "business insider";
                case "CNBC":return "cnbc";
                case "Financial Times":return "financial-times";
                case "Fortune": return "fortune";
                case "The Economist":return "the-economist";
                default: return "fortune";
            }

        }else if(count==4){//SPORTS

            switch(item){
                case "BBC Sport":return "bbc-sport";
                case "ESPN": return"espn";
                case "ESPN Cricket Info":return "espn-cric-info";
                case "Football Italia": return "football-italia";
                case "FoxSports":return "fox-sports";
                case "NFL News":return "nfl-news";
                case "TalkSport":return "talksport";
                case "The Sport Bible":return "the-sport-bible";
                default:return "bbc-sport";

            }

        }else if(count==5){//SCI AND NATURE
            switch(item){
                case "New Scientist":return "new-scientist";
                case "National Geographic":return "national-geographic";
                default:return "national-geographic";
            }

        }else if(count==6){//POLITICS

                return "breitbart-news";


        }else if(count==7){//MUSIC


               return "mtv-news-uk";

        }
       return null;

    }

    //changed
    private void setSpinnerArray(int count){
        String general_arr[]=getResources().getStringArray(R.array.general_array);
        String technology_arr[]=getResources().getStringArray(R.array.technology_array);
        String sports_arr[]=getResources().getStringArray(R.array.sports_array);
        String entertainment_arr[]=getResources().getStringArray(R.array.ent_array);
        String politics_arr[]={getString(R.string.Breitbart_News)};
        String science_arr[]=getResources().getStringArray(R.array.science_array);
        String music_arr[]={getString(R.string.Mtv_News)};
        String business_arr[]=getResources().getStringArray(R.array.business_array);

        switch (count){
            case 0://General
                spinnerItems=general_arr; newsSource=Constants.DEFAULT_SOURCE_GENERAL; break;
            case 1://Entertainment
                spinnerItems=entertainment_arr; newsSource=Constants.DEFAULT_SOURCE_ENTERTAINMENT; break;
            case 2://Technology
                spinnerItems=technology_arr; newsSource=Constants.DEFAULT_SOURCE_TECHNOLOGY; break;
            case 3://Business
                spinnerItems=business_arr; newsSource=Constants.DEFAULT_SOURCE_BUSINESS; break;
            case 4://image0
                spinnerItems=sports_arr; newsSource=Constants.DEFAULT_SOURCE_SPORTS; break;
            case 5://Science and nature
                spinnerItems=science_arr; newsSource=Constants.DEFAULT_SOURCE_SCIENCE; break;
            case 6://Politics
                spinnerItems=politics_arr; newsSource=Constants.DEFAULT_SOURCE_POLITICS; break;
            case 7://Music
                spinnerItems=music_arr; newsSource=Constants.DEFAULT_SOURCE_MUSIC; break;
            default://General
                spinnerItems=general_arr; newsSource=Constants.DEFAULT_SOURCE_GENERAL; break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("NEWS", dataList);
    }

    private String getUrl(){

        return "https://newsapi.org/v1/articles?source="+newsSource+"&sortBy=top&apiKey="+ Keys.KeyNews.API_KEY;
    }

    private void sendJsonRequest(){

        final JSONParseing jsonParseing=new JSONParseing();
        RequestQueue requestQueue= VolleySingleton.getsInstance().getmRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, getUrl(),null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

               volleyErrorText.setVisibility(View.GONE);
                dataList= jsonParseing.parseJsonResponse(jsonObject);
                adapter.setNewsList(dataList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                JSONErr errorHandler=new JSONErr();
                errorHandler.ErrorInfo(volleyError, volleyErrorText);

            }
        });
        requestQueue.add(request);

    }

    public void createBuilder()
    {
      //  Log.i("createBuilder","createBuilderclicked");
        Context c=this;
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


}
