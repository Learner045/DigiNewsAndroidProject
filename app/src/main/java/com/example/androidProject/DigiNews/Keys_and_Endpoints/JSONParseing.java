package com.example.androidProject.DigiNews.Keys_and_Endpoints;

import com.example.androidProject.DigiNews.Entities.NewsEntity;
import static com.example.androidProject.DigiNews.Keys_and_Endpoints.Keys.KeyNews.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParseing {

    private String source;

    public ArrayList<NewsEntity> parseJsonResponse(JSONObject response) {

       ArrayList<NewsEntity> myDataList=new ArrayList<>();

        if(response==null || response.length()==0){
           // Log.i("ERROR","response was null")
        }
        try{
           // Log.i("data","added");
            //Log.i("RESPONSE IS",response.toString());

            if(response.has("articles")) {
                JSONArray arrayArticles = response.getJSONArray("articles");

                JSONObject currentArticle;

                if(response.has(SOURCE) && !response.isNull(SOURCE)){
                    source=response.getString(SOURCE);

                }else {source="NA";}

                for(int i=0;i<arrayArticles.length();i++){
                    currentArticle=arrayArticles.getJSONObject(i);

                    if(currentArticle!=null) {
                        NewsEntity entity = new NewsEntity();
                        if (currentArticle.has(TITLE) && !currentArticle.isNull(TITLE) && currentArticle.has(URL) && !currentArticle.isNull(URL)) {
                            entity.setTitle(currentArticle.getString(TITLE));
                            entity.setUrlForWebView(currentArticle.getString(URL));
                            entity.setSource(source);
                        } else {
                            continue;
                        }

                        if (currentArticle.has(DESC) && !currentArticle.isNull(DESC)) {
                            entity.setDesc(currentArticle.getString(DESC));
                        } else {
                            entity.setDesc(" ");
                        }
                        if (currentArticle.has(AUTHOR) && !currentArticle.isNull(AUTHOR)) {
                            String author=currentArticle.getString(AUTHOR);
                            if(author.length()<25){
                                entity.setAuthor(currentArticle.getString(AUTHOR));
                            }else{
                                entity.setAuthor("NA");
                            }
                        } else {
                            entity.setAuthor("NA");
                        }
                        if (currentArticle.has(URLImage) && !currentArticle.isNull(URLImage)) {
                            entity.setUrlImage(currentArticle.getString(URLImage));
                        } else {
                            entity.setUrlImage(imageNA);
                        }
                        if (currentArticle.has(PUBLISHED_AT) && !currentArticle.isNull(PUBLISHED_AT)) {
                            entity.setPublishedAt(currentArticle.getString(PUBLISHED_AT).substring(0, 10));
                        } else {
                            entity.setPublishedAt("NA");
                        }
                        myDataList.add(entity);

                    }

                }

            }
        }
        catch(JSONException e){

           // Log.i("ERROR",e.toString());

        }catch(Exception e){

        }

        return myDataList;

    }


}
