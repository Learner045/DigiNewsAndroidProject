package com.example.androidProject.DigiNews.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.androidProject.DigiNews.Animation.AnimationUtil;
import com.example.androidProject.DigiNews.Entities.NewsEntity;
import com.example.androidProject.DigiNews.Keys_and_Endpoints.Constants;
import com.example.androidProject.DigiNews.Network.VolleySingleton;
import com.example.androidProject.DigiNews.NewsDetailsActivity;
import com.example.androidProject.DigiNews.R;

import java.util.ArrayList;
import java.util.List;


public class ChangedNewsAdapter extends RecyclerView.Adapter<ChangedNewsAdapter.ViewHolderForFragments> {

    private LayoutInflater inflater;
    private List<NewsEntity> dataList = new ArrayList<>();
    private ImageLoader imageLoader;
    private Context context;
    private int previousPosition=0; //req for animation


    public ChangedNewsAdapter(Context context) {
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public ViewHolderForFragments onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.card_news_row,parent,false);
        ViewHolderForFragments holder=new ViewHolderForFragments(view);
        imageLoader= VolleySingleton.getsInstance().getImageLoader();
        return holder;

    }
    public void setNewsList( List<NewsEntity> data){

        this.dataList=data;
        notifyItemRangeChanged(0, dataList.size());
    }

    @Override
    public void onBindViewHolder(final ViewHolderForFragments holder, int position) {

        //Animation
        if(position>previousPosition){
            //scrolling down
            AnimationUtil.animate(holder,true);
        }else{
            //scrolling up
            AnimationUtil.animate(holder,false);
        }
        previousPosition=position;

        NewsEntity currentObj=dataList.get(position);
        if(currentObj!=null){

            holder.newsTitle.setText(currentObj.getTitle());
            holder.newsSource.setText(currentObj.getSource());
            holder.newsAuthor.setText(currentObj.getAuthor());
            holder.newsDesc.setText(currentObj.getDesc());
            //load an image here
            String imageurl=currentObj.getUrlImage();
           // Log.i("imageLoader",imageLoader.toString());

            if(imageurl!=null)
                holder.newsImage.setImageUrl(imageurl,imageLoader);

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolderForFragments extends RecyclerView.ViewHolder implements View.OnClickListener{


        NetworkImageView newsImage;

        TextView newsTitle;
        TextView newsSource;
        TextView newsAuthor;
        TextView newsDesc;

        public ViewHolderForFragments(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            newsImage= (NetworkImageView) itemView.findViewById(R.id.card_news_thumbnail);
            newsTitle= (TextView) itemView.findViewById(R.id.card_news_title);
            newsSource=(TextView)itemView.findViewById(R.id.card_news_source);
            newsAuthor= (TextView) itemView.findViewById(R.id.card_news_author);
            newsDesc=(TextView)itemView.findViewById(R.id.card_news_desc);
        }

        @Override
        public void onClick(View v) {

            Intent intent=new Intent(context, NewsDetailsActivity.class);
           // Log.i("position cliked is "+" ",getAdapterPosition()+"");
           // Log.i("title",dataList.get(getAdapterPosition()).getTitle());
            NewsEntity currentObject=dataList.get(getAdapterPosition());

            Bundle b=new Bundle();
            b.putParcelable(Constants.CONSTANT_FOR_OBJECT_PASSING,currentObject);
            intent.putExtras(b);

            context.startActivity(intent);
        }
    }
}
