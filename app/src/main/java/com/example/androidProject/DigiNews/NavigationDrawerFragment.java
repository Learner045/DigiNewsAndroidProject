package com.example.androidProject.DigiNews;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidProject.DigiNews.Share.Share;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private int nav_items_icons[]=new int[]{R.mipmap.favourite,R.mipmap.info,R.mipmap.rateus,R.mipmap.share};
    private String nav_items_text[];
    private ListView navListView;
    private Context c;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public void setContext(Context c){
        this.c=c;
    }

    private String[] getNavData(){
        nav_items_text=getResources().getStringArray(R.array.nav_items);
        return nav_items_text;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        navListView=(ListView)rootview.findViewById(R.id.nav_list);
        NavAdapter navAdapter=new NavAdapter(getActivity(),getNavData());
        navListView.setAdapter(navAdapter);
        navListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.i("item cliked is ",nav_items_text[position]+" ");
                if(nav_items_text[position].equals(getString(R.string.Favourites))){
                    startActivity(new Intent(c,FavouriteActivity.class));
                }else if(nav_items_text[position].equals(getString(R.string.About_Us))){
                    startActivity(new Intent(c,AboutUs.class));
                }else if(nav_items_text[position].equals(getString(R.string.Share_))){
                    Share share=new Share(getActivity());
                    share.shareApp();
                }else if(nav_items_text[position].equals(getString(R.string.Rate_Us))){

                }
            }
        });
        return rootview;

    }

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar){

        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset<0.6){
                toolbar.setAlpha(1-slideOffset);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    class NavAdapter extends ArrayAdapter<String>{

        Context c;
        NavAdapter(Context c,String[] titles){
            super(c,R.layout.nav_list_row,titles);
            this.c=c;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View rootView=convertView;
            NavViewHolder holder=null;
            if(rootView==null) {
                LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rootView = inflater.inflate(R.layout.nav_list_row, parent, false);
                holder=new NavViewHolder(rootView);
                rootView.setTag(holder);
            }else{
                holder= (NavViewHolder) rootView.getTag();
            }

            holder.navIcon.setImageResource(nav_items_icons[position]);
            holder.navText.setText(nav_items_text[position]);
            return rootView;
        }
    }
    class NavViewHolder {

        ImageView navIcon;
        TextView navText;
        NavViewHolder(View view){

            navIcon=(ImageView)view.findViewById(R.id.nav_item_image);
            navText=(TextView)view.findViewById(R.id.nav_item_text);
        }


    }

}
