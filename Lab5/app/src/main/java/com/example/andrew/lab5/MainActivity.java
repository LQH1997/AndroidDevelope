package com.example.andrew.lab5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     RecyclerView myRecyclerView;
     List<String> myData;
    List<String> DataHeader;
     HomeAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycleview);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(myAdapter = new HomeAdapter());

    }

    protected void initData() {
        String[] shoppingItems = new String[] {"Enchated Forest", "Arla Milk",  "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
                "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        myData = new ArrayList<String>();
        for (int i = 0; i < shoppingItems.length; i++) {
            String temp = shoppingItems[i];
            myData.add(temp);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.items, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(myData.get(position));
            holder.tv1.setText(myData.get(position).substring(0,1));
        }

        @Override
        public int getItemCount() {
            return myData.size();
        }

        class MyViewHolder extends ViewHolder {

            TextView tv, tv1;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                tv1 = (TextView) view.findViewById(R.id.header);
            }
        }
    }

}
