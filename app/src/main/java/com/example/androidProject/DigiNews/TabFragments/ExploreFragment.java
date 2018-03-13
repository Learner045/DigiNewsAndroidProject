package com.example.androidProject.DigiNews.TabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.androidProject.DigiNews.Entities.ExploreEntity;
import com.example.androidProject.DigiNews.R;
import com.example.androidProject.DigiNews.Views.ExploreAdapter;
import java.util.ArrayList;


public class ExploreFragment extends Fragment {

    private ExploreAdapter adapter;
    private ArrayList<ExploreEntity>data=new ArrayList<>();
    public static ExploreFragment newInstance(){
        return new ExploreFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ExploreAdapter(getActivity());// ADAPTER IS PLACED DIFFERENTLY THEN IN OTHER FRAGMENTS
        getData();
    }

    private void getData(){
       //, , gaming, general,entertainment,technology,/business,sport,sci&nature,politics,music  .
        String categoryArr[]=getResources().getStringArray(R.array.category_array);

        int categoryCount=9;

        for(int i=0;i<categoryCount;i++){

            String categoryName=categoryArr[i];
            int imageId = getResources().getIdentifier(categoryName.toLowerCase().replace(" ",""), "drawable", getActivity().getPackageName());
           // int imageId=R.drawable.("image"+i);

            data.add(new ExploreEntity(imageId,categoryName)); //add images

        }
       // Log.i("data added","true");
        adapter.setDataExploreList(data);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_explore,container,false);
        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.explore_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return rootView;
    }


}
