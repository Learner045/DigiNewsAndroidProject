package com.example.androidProject.DigiNews.Share;

import android.content.Context;
import android.content.Intent;
import com.example.androidProject.DigiNews.DatabaseWork.NewsEntityRealm;
import com.example.androidProject.DigiNews.Entities.NewsEntity;



public class Share {
   private Context c;
   private String articleTitle;
   private String articleDesc;
   private String articleLink;
   private String shareMessage="Hey Check this out...";
   private String appName="DigiNews";
   private String appLink="  ";

    public Share(Context c){
        this.c=c;
    }


    private void prepareIntentForArticles(){
       Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, appName);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.putExtra(Intent.EXTRA_TEXT, articleTitle);
        sendIntent.putExtra(Intent.EXTRA_TEXT, articleDesc);
        sendIntent.putExtra(Intent.EXTRA_TEXT,articleLink);
        Intent chooser = Intent.createChooser(sendIntent, "Share via");

        c.startActivity(chooser);
    }


    public void shareArticle(NewsEntityRealm currentObj){

        articleTitle=currentObj.getTitleRealm();
        articleDesc=currentObj.getDescRealm();
        articleLink=currentObj.getUrlForWebViewRealm();

        prepareIntentForArticles();


    }
    public void shareArticle(NewsEntity currentObj){

        articleTitle=currentObj.getTitle();
        articleDesc=currentObj.getDesc();
        articleLink=currentObj.getUrlForWebView();

        prepareIntentForArticles();
    }

    public void shareApp(){

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, appName);
            String title = "\nLet me recommend you this application\n\n";
            appLink = appLink + "https://play.google.com/store/apps/details?id=\n\n";
            i.putExtra(Intent.EXTRA_TEXT, title);
            c.startActivity(Intent.createChooser(i, "choose one"));

        } catch(Exception e) {
            //e.toString();

        }

    }


}
