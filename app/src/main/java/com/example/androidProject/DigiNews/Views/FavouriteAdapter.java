package com.example.androidProject.DigiNews.Views;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.androidProject.DigiNews.Animation.AnimationUtil;
import com.example.androidProject.DigiNews.DatabaseWork.NewsEntityRealm;
import com.example.androidProject.DigiNews.FavouriteActivity;
import com.example.androidProject.DigiNews.FavouriteDetailsActivity;
import com.example.androidProject.DigiNews.Network.VolleySingleton;
import com.example.androidProject.DigiNews.R;

import java.util.ArrayList;
import java.util.List;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private Context c;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private List<NewsEntityRealm> dataList=new ArrayList<>();
    private int previousPosition=0;

    public FavouriteAdapter(Context c){
        this.c=c;
        layoutInflater=LayoutInflater.from(c);

    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView=layoutInflater.inflate(R.layout.card_favourite_row,parent,false);
        FavouriteViewHolder holder=new FavouriteViewHolder(rootView);
        imageLoader= VolleySingleton.getsInstance().getImageLoader();
        return holder;

    }
    public  void setNewsList( List<NewsEntityRealm> data){

        this.dataList=data;
        notifyItemRangeChanged(0, dataList.size());
    }
    private void gotoDetails(int position){

        //send the object URL
        String url=null;
        Intent intent=new Intent(c, FavouriteDetailsActivity.class);
        url=dataList.get(position).getUrlForWebViewRealm();
        intent.putExtra("FAVURL",url);
        c.startActivity(intent);
    }

    private void removeItem(NewsEntityRealm currentObj){
        int position=dataList.indexOf(currentObj);
        if(position!=-1){
        dataList.remove(position);
        notifyItemRemoved(position);

        //REMOVE FROM DATABASE ASYNC
        FavouriteActivity obj=new FavouriteActivity();
        obj.deleteDataFromRealm(position);}
    }

    private void showLocationDialog(final NewsEntityRealm currentObj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.MyDialogTheme);
        builder.setTitle(c. getString(R.string.dialog_title));
        builder.setMessage(c. getString(R.string.dialog_message));

        String positiveText = c. getString(R.string.Remove);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        //REMOVE OBJ
                        removeItem(currentObj);
                    }
                });

        String negativeText = c.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        //DONT DO ANYTHING
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    public void onBindViewHolder(final FavouriteViewHolder holder, int position) {


        //ANIMATION
        if(position>previousPosition){

            //we are scrolling down
            AnimationUtil.animate(holder,true);
        }else{

            //scrolling up
            AnimationUtil.animate(holder,false);
        }
        final int posi=position;
        previousPosition=position;

        //

        final NewsEntityRealm currentObj=dataList.get(position);

        if(currentObj!=null){
            holder.title.setText(currentObj.getTitleRealm());
            holder.publishedAt.setText(currentObj.getPublishedAtRealm().toString());

            //load image here

            String imageurl=currentObj.getUrlImageRealm();
            if(imageurl!=null)
                holder.newsImage.setImageUrl(imageurl,imageLoader);


        }
        holder.itemViewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("item clicked at pos",posi+"");
                //goto FavDetailsActivity
                gotoDetails(posi);

            }
        });
        holder.itemViewParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showLocationDialog(currentObj);//ASK For confirmation and then delete
                return true;
            }
        });



    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

   public static class FavouriteViewHolder extends RecyclerView.ViewHolder {


        NetworkImageView newsImage;

        TextView title;
        TextView publishedAt;
        View itemViewParent;
        public FavouriteViewHolder(View itemView) {
            super(itemView);
            itemViewParent=itemView;

            newsImage=(NetworkImageView)itemView.findViewById(R.id.card_news_thumbnail);
            title=(TextView)itemView.findViewById(R.id.card_news_title);
            publishedAt=(TextView)itemView.findViewById(R.id.publishedAt);
        }


   }

}
