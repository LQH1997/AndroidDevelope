package com.example.andrew.final_term;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MainActivityRecyclerViewAdapter mAdapter;
    public ArrayList<Info> myData;
    //Image tempImage = new Image();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //Log.e("TAG", ((Integer) myData.size()).toString());
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MainActivityRecyclerViewAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MainActivityRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NoteContextActivity.class);
                startActivity(intent);

            }
        });

        mAdapter.setOnItemLongClickListener(new MainActivityRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Long click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {
        myData = new ArrayList<Info>();
        Info tempInfo = new Info("aaa", "bbb", "ccc", "ddd");
        for(int i = 0; i < 5; i++) {
            myData.add(tempInfo);
        }
    }

}
