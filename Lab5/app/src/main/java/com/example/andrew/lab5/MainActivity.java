package com.example.andrew.lab5;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

     RecyclerView myRecyclerView;
     ArrayList<ListItems> myData;
     HomeAdapter myAdapter;
    int countItem[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycleview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(myAdapter = new HomeAdapter());
        FloatingActionButton carButton = (FloatingActionButton) findViewById(R.id.fab);
        carButton.setOnClickListener(carClickListener);
        RecyclerView itemButton = (RecyclerView) findViewById(R.id.my_recycleview);
        itemButton.setOnClickListener(itemClickListener);

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



    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.items, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(myData.get(position).getName());
            holder.tv1.setText(myData.get(position).getName().substring(0,1));
            //holder.tv.setOnClickListener(itemClickListener);
        }

        @Override
        public int getItemCount() {
            return myData.size();
        }

        class MyViewHolder extends ViewHolder
                //implements View.OnClickListener, View.OnLongClickListener
        {

            TextView tv, tv1;

            public int position;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                tv1 = (TextView) view.findViewById(R.id.header);
            }


        }

    }
    
    FloatingActionButton.OnClickListener carClickListener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            //int a = countItem[0];
            //Integer b = a;
           // Toast.makeText(MainActivity.this, b.toString(), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, shopping_cart.class);
            startActivity(intent);
        }
    };

    RecyclerView.OnClickListener itemClickListener = new RecyclerView.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_LONG).show();
        }
    };

}
