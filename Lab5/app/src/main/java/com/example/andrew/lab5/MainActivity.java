package com.example.andrew.lab5;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     //RecyclerView myRecyclerView;
     ArrayList<ListItems> myData;
    // HomeAdapter myAdapter;
    int countItem[];
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycleview);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view , int position){

                countItem[position] += 1;
                Integer a = countItem[position];
                String b = a.toString();
                Toast.makeText(MainActivity.this, myData.get(position).getName() + " " + b, Toast.LENGTH_SHORT).show();

            }
        });

        mAdapter.setOnItemLonngClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "aaaaa", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton carButton = (FloatingActionButton) findViewById(R.id.fab);
        carButton.setOnClickListener(carClickListener);

    }

    protected void initData() {
        String strings1[] = {"Enchated Forest",    "¥ 5.00",      "作者",    "Johanna Basford",
                "Arla Milk",          "¥ 59.00",     "产地",    "德国",
                "Devondale Milk",     "¥ 79.00",     "产地",    "澳大利亚",
                "Kindle Oasis",       "¥ 2399.00",   "版本",    "8GB",
                "waitrose 早餐麦片",  "¥ 179.00",    "重量",    "2Kg",
                "Mcvitie's 饼干",     "¥ 14.90",     "产地",    "英国",
                "Ferrero Rocher",     "¥ 132.59",    "重量",    "300g",
                "Maltesers",          "¥ 141.43",    "重量",    "118g",
                "Lindt",              "¥ 139.43",    "重量",    "249g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g",
                "Borggreve",          "¥ 28.90",     "重量",    "640g"
        };
        countItem = new int[strings1.length/4];
        myData = new ArrayList<ListItems>();
        for(int i = 0; i < strings1.length/4; i++) {
            ListItems newItem = new ListItems();
            newItem.setAttr(strings1[i*4+0], strings1[i*4+1], strings1[i*4+2], strings1[i*4+3]);
            myData.add(newItem);
            countItem[i] = 0;
        }
    }



    FloatingActionButton.OnClickListener carClickListener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, shopping_cart.class);
            intent.putExtra("countItem", countItem);
            intent.putExtra("myData", myData);
            startActivity(intent);
        }
    };


}
