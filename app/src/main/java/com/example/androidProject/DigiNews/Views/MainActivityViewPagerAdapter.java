package com.example.androidProject.DigiNews.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.androidProject.DigiNews.TabFragments.ExploreFragment;
import com.example.androidProject.DigiNews.TabFragments.PopularFragment;
import com.example.androidProject.DigiNews.TabFragments.TrendingFragment;


public class MainActivityViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment returnFragment=null;
        switch (i){
            case 0:returnFragment= ExploreFragment.newInstance();break;
            case 1:returnFragment= TrendingFragment.newInstance();break;
            case 2:returnFragment= PopularFragment.newInstance(); break;
            default:returnFragment=null;
        }

        return returnFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;

        switch (position){
            case 0:title= " Explore ";break;
            case 1:title=" Trending ";break;
            case 2:title=" Popular ";break;
            default:title=null;

        }

        return title;
    }
}
