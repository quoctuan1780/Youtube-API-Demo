package com.example.youtube_api_demo.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youtube_api_demo.R;
import com.example.youtube_api_demo.adapter.AdapterHome;
import com.example.youtube_api_demo.models.ModelHome;
import com.example.youtube_api_demo.models.VideoYT;
import com.example.youtube_api_demo.network.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFragment extends Fragment {

    private EditText input_query;
    private Button btn_search;
    private AdapterHome adapter;
    private LinearLayoutManager manager;
    private List<VideoYT> videoList=new ArrayList<>();

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_search,container,false);
        input_query=view.findViewById(R.id.input_query);
        btn_search=view.findViewById(R.id.btn_search);
        RecyclerView rv=view.findViewById(R.id.recycler_search);

        adapter=new AdapterHome(getContext(),videoList);
        manager=new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(input_query.getText().toString())){
                    getJson(input_query.getText().toString());
                }else{
                    Toast.makeText(getContext(), "you need to enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getJson(String query) {
        String url= YoutubeAPI.BASE_URL + YoutubeAPI.sch+ YoutubeAPI.KEY + YoutubeAPI.mx + YoutubeAPI.ord + YoutubeAPI.part +YoutubeAPI.query + query + YoutubeAPI.type;
        Call<ModelHome> data=YoutubeAPI.getHomeVideo().getYT(url);
        data.enqueue(new Callback<ModelHome>() {
            @Override
            public void onResponse(Call<ModelHome> call, Response<ModelHome> response) {
                if(response.errorBody()!=null){
                    Log.v(TAG, "onResponseSearch:"+response.errorBody().toString());
                }else{
                    ModelHome mh=response.body();
                    if(mh.getItems().size()!=0){
                        videoList.addAll(mh.getItems());
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getContext(), "No video",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelHome> call, Throwable t) {
                Log.e(TAG,"onFailure search:",t);
            }
        });
    }
}
