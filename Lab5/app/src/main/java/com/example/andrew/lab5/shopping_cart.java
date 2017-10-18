package com.example.andrew.lab5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 2017/10/18.
 */



public class shopping_cart extends AppCompatActivity {

    ArrayList<ListItems> myData;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        ArrayList<ListItems> tempData = (ArrayList<ListItems>) getIntent().getSerializableExtra("myData");
        int[] countItem = (int[]) getIntent().getSerializableExtra("countItem");

        myData = new ArrayList<ListItems>();
        for(int i = 0; i < tempData.size(); i++) {
            if(countItem[i] != 0) {
                myData.add(tempData.get(i));
            }
        }
        Integer a = myData.size();
        String b = a.toString();
        Toast.makeText(shopping_cart.this, "the size is " + b, Toast.LENGTH_LONG).show();
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycleview_cart);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new CartAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

    }
}
