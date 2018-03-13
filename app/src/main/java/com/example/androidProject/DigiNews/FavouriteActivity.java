package com.example.androidProject.DigiNews;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.example.androidProject.DigiNews.DatabaseWork.NewsEntityRealm;
import com.example.androidProject.DigiNews.Views.FavouriteAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class FavouriteActivity extends AppCompatActivity  {


    private ArrayList<NewsEntityRealm>dataList=new ArrayList<>();
    private Realm realm=null;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        message=(TextView)findViewById(R.id.message);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.favourite_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavouriteAdapter adapter = new FavouriteAdapter(this);
       //CHANGED
        SlideInRightAnimator animator = new SlideInRightAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setRemoveDuration(2000);
        recyclerView.setItemAnimator(animator);
        //
        recyclerView.setAdapter(adapter);


        getDataFromRealm();

        adapter.setNewsList(dataList);

    }


    private void getDataFromRealm(){



        try {
            dataList = (ArrayList<NewsEntityRealm>)new ArrayList(realm.where(NewsEntityRealm.class).findAllAsync());
        }catch(Exception e){
            //Log.i("exception ",e.toString());
        }


        if(dataList==null || dataList.size()==0){
            message.setVisibility(View.VISIBLE);
           // Log.i("data not found","");
        }else{
            message.setVisibility(View.GONE);
            //Log.i("data found", dataList.get(0).getTitleRealm());

        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm!=null)realm.close();
    }

    public void deleteDataFromRealm(final int position){

        realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {


                @Override
                public void execute(Realm realm) {


                    //QUERY TO GET ALL OBJECTS
                    final RealmResults<NewsEntityRealm> results = realm.where(NewsEntityRealm.class).findAllSortedAsync("publishedAtRealm", Sort.DESCENDING);
                    // remove a single object

                    NewsEntityRealm n = results.get(position);
                    n.deleteFromRealm();

                   // Log.i("data deleted","true");
                }
            });

    }



}
