package com.example.youtube_api_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.youtube_api_demo.adapter.AdapterHome;
import com.example.youtube_api_demo.fragment.SearchFragment;
import com.example.youtube_api_demo.models.ModelHome;
import com.example.youtube_api_demo.models.VideoYT;
import com.example.youtube_api_demo.network.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchActivity extends AppCompatActivity {
    private Button btn;
    private EditText txt;
    private SearchFragment searchFragment=new SearchFragment();
    private EditText input_query;
    private Button btn_search;
    private AdapterHome adapter;
    private LinearLayoutManager manager;
    private List<VideoYT> videoList = new ArrayList<>();
    RecyclerView rv;
    private ModelHome mh;

    // Cái class này tính là để có cái menu để gọi các fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btn=findViewById(R.id.btn_search);
        txt=findViewById(R.id.input_query);
        input_query = (EditText) findViewById(R.id.input_query);
        rv =  (RecyclerView) findViewById(R.id.recycler_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setFragment(searchFragment);
                //Không dùng Fragment, lấy trực tiếp hiển thị lên activity
                if(!TextUtils.isEmpty(input_query.getText().toString())){
                    getJson(input_query.getText().toString());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mh.getItems().size() > 0){
                                Toast.makeText(SearchActivity.this, "Có data", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SearchActivity.this, "không data", Toast.LENGTH_SHORT).show();
                            }
                            videoList = mh.getItems();
                            adapter = new AdapterHome(SearchActivity.this, videoList);
                            manager = new LinearLayoutManager(SearchActivity.this);
                            rv.setLayoutManager(manager);
                            rv.setAdapter(adapter);
                        }
                    }, 1000);
                }else{
                    Toast.makeText(SearchActivity.this, "you need to enter some text", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void getJson(String query) {
        String url= YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.mx + YoutubeAPI.ord + YoutubeAPI.part +YoutubeAPI.query + query + YoutubeAPI.type;
        Call<ModelHome> data=YoutubeAPI.getHomeVideo().getYT(url);
        data.enqueue(new Callback<ModelHome>() {
            @Override
            public void onResponse(Call<ModelHome> call, Response<ModelHome> response) {
                    if(response.isSuccessful()){
                        mh = response.body();
                        if(response.body().getItems().size() > 0){
                            Toast.makeText(SearchActivity.this, "Co data", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SearchActivity.this, "ko data", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            @Override
            public void onFailure(Call<ModelHome> call, Throwable t) {
                Log.e(TAG,"onFailure search:",t);
            }
        });
    }

    private void setFragment(Fragment Fragment) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        // cái này là đều mở fragment bằng menu

    }
}