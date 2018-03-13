package com.example.androidProject.DigiNews.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidProject.DigiNews.Entities.ExploreEntity;
import com.example.androidProject.DigiNews.ExploreActivity;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.Constants;
import com.example.androidProject.DigiNews.R;


import java.util.List;


public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyExploreViewHolder> {


    private LayoutInflater inflater;
    private List<ExploreEntity> data;
    private Context c;


    public ExploreAdapter(Context c){
        inflater=LayoutInflater.from(c);
        this.c=c;

    }

    public void setDataExploreList( List<ExploreEntity> data){

        this.data=data; //list of imagesIDS
        notifyItemRangeChanged(0,data.size());
    }
    @Override
    public MyExploreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView=inflater.inflate(R.layout.card_explore_row,parent,false);
        MyExploreViewHolder holder=new MyExploreViewHolder(rootView);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyExploreViewHolder holder, int position) {

        ExploreEntity i=data.get(position);
        if(i!=null){
            holder.image.setImageResource(i.getImageID());
            holder.categoryName.setText(i.getCategoryText());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }





    class MyExploreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        private TextView categoryName;

        public MyExploreViewHolder(View rootView){
            super(rootView);
            rootView.setOnClickListener(this);
            image=(ImageView)rootView.findViewById(R.id.exploreImage);
            categoryName=(TextView)rootView.findViewById(R.id.categoryName);
        }

        @Override
        public void onClick(View v) {
            int count=0;
            Intent i=new Intent(c, ExploreActivity.class);

            switch (getAdapterPosition()){
                case 0://General
                    count=0;break;
                case 1://Entertainment
                    count=1;break;
                case 2://Technology
                    count=2;break;
                case 3://Business
                    count=3;break;
                case 4://image0
                    count=4;break;
                case 5://Science and nature
                    count=5;break;
                case 6://Politics
                    count=6;break;
                case 7://Music
                    count=7;break;
                default://General

            }

            i.putExtra(Constants.IMAGE_EXPLORED,count);
            c.startActivity(i);

        }
    }
}
