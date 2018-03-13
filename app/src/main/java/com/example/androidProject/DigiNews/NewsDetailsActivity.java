package com.example.androidProject.DigiNews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.androidProject.DigiNews.DatabaseWork.NewsEntityRealm;
import com.example.androidProject.DigiNews.Entities.NewsEntity;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.Constants;
import com.example.androidProject.DigiNews.Share.Share;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class NewsDetailsActivity extends AppCompatActivity implements View.OnClickListener{


    private NewsEntity currentObject;
    private Realm realm=null;
    private RealmAsyncTask transaction=null;
    private Boolean Added;
    private Context c;
    private ProgressBar progressBar;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        Added=false;
        c=this;

        Realm.init(this);

        String url;

            //WEBVIEW CREATION
        WebView webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }else{

                    progressBar.setProgress(newProgress);
                    progressBar.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        //RETRIEVED PASSED OBJECT FROM FRAGMENTS To View its details
        Bundle b=this.getIntent().getExtras();
        if(b!=null){
            currentObject=b.getParcelable(Constants.CONSTANT_FOR_OBJECT_PASSING);

            try{

            url=currentObject.getUrlForWebView();
            webView.loadUrl(url);

            }catch(Exception e){
                Toast.makeText(this, R.string.no_url,Toast.LENGTH_LONG).show();
            }
        }

        //FLOATING BUTTONS
        generateButton();



    }

    private void generateButton(){

        //ICON WILL HAVE A TRANSPARENT PLUS SIGN
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.plus); //add plus icon here

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.selector_button) //BACKGROUND OF MAIN ICON
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        //BACKGROUND OF SUB BUTTONS grey and dark grey background
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg));

        // repeat many times:
        ImageView heartIcon = new ImageView(this);
        heartIcon.setImageResource(R.mipmap.favourite);
        SubActionButton heartButton = itemBuilder.setContentView(heartIcon).build();
        heartButton.setOnClickListener(this);
        heartButton.setTag(Constants.HEART_BUTTON);


        ImageView shareIcon = new ImageView(this);
        shareIcon.setImageResource(R.mipmap.share);
        SubActionButton shareButton = itemBuilder.setContentView(shareIcon).build();
        shareButton.setOnClickListener(this);
        shareButton.setTag(Constants.SHARE_BUTTON);


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(heartButton)
                .addSubActionView(shareButton)
                .enableAnimations()
                .attachTo(actionButton)
                .build();

    }

    public void writeToRealm()
    {

        //REALM
       try {
           realm = Realm.getDefaultInstance();
           transaction = realm.executeTransactionAsync(new Realm.Transaction() {
               @Override
               public void execute(Realm bgRealm) {
                   NewsEntityRealm entity = bgRealm.createObject(NewsEntityRealm.class);
                   //write currentObj content into realm
                   entity.setUrlForWebViewRealm(currentObject.getUrlForWebView());
                   entity.setUrlImageRealm(currentObject.getUrlImage());
                   entity.setTitleRealm(currentObject.getTitle());
                   entity.setAuthorRealm(currentObject.getAuthor());
                   entity.setSourceRealm(currentObject.getSource());
                   entity.setDescRealm(currentObject.getDesc());
                   //convert string to date object and store it in realm
                   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                   try {
                       date = formatter.parse(currentObject.getPublishedAt());
                   } catch (ParseException p) {
                       date = new Date();
                   }
                   entity.setPublishedAtRealm(date);

               }
           }, new Realm.Transaction.OnSuccess() {
               @Override
               public void onSuccess() {
                   // Transaction was a success.
                  // Log.i("transaction", "YES");
                   Toast.makeText(c, "Article added to favourites", Toast.LENGTH_LONG).show();
               }
           }, new Realm.Transaction.OnError() {
               @Override
               public void onError(Throwable error) {
                   // Transaction failed and was automatically canceled.4
                  // Log.i("error", error.toString());
                   Toast.makeText(c, "Sorry, couldn't perform this action at this time.", Toast.LENGTH_LONG).show();
               }
           });

       }
        finally{
           if(realm!=null)
            realm.close();
        }


    }

    @Override
    public void onClick(View v) {

        if(v.getTag().equals(Constants.HEART_BUTTON)){

            //add to favourites and let user know
          //  add object to database
            if(!Added){
            Added=true;
            writeToRealm();
            }
            else{
                Toast.makeText(c,"Article has been already added",Toast.LENGTH_LONG).show();
            }

        }else if(v.getTag().equals(Constants.SHARE_BUTTON)){

           // share the article link along with image and title
           // Log.d("objectdata",currentObject.toString());
           Share share=new Share(c);
           share.shareArticle(currentObject);

        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        if(transaction!=null && !transaction.isCancelled()){
            transaction.cancel();
        }
        if(realm!=null)realm.close();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm!=null)realm.close();
    }




    //SHARE METHODS

}
