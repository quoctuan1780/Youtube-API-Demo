package com.example.youtube_api_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.youtube_api_demo.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {
    private Button btn;
    private EditText txt;
    private SearchFragment searchFragment=new SearchFragment();

    // Cái class này tính là để có cái menu để gọi các fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btn=findViewById(R.id.btn_search);
        txt=findViewById(R.id.input_query);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(searchFragment);
            }
        });
    }

    private void setFragment(Fragment Fragment) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        // cái này là đều mở fragment bằng menu

    }
}