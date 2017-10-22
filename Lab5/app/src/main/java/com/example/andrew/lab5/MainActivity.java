package com.example.andrew.lab5;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //RecyclerView myRecyclerView;
    ArrayList<ListItems> myData;
    ArrayList<ListItems> tempData;
    // HomeAdapter myAdapter;
    int countItem[];
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempData = new ArrayList<ListItems>();
        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycleview);
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
            public void onItemClick(View view, int position) {
                myData.get(position).setNum(1);
                //countItem[position] = 1;
                //Integer a = countItem[position];
                //String b = a.toString();
                //Toast.makeText(MainActivity.this, myData.get(position).getName() + " " + b, Toast.LENGTH_SHORT).show();

            }
        });

        mAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("移除商品");
                builder.setMessage("从商品列表中移除" + myData.get(position).getName());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myData.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        countItem[position] = 0;
                        //finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        FloatingActionButton carButton = (FloatingActionButton) findViewById(R.id.fab);
        carButton.setOnClickListener(carClickListener);

    }

    protected void initData() {
        String strings1[] = {"Enchated Forest", "¥ 5.00", "作者", "Johanna Basford", "enchatedforest",
                "Arla Milk", "¥ 59.00", "产地", "德国", "arla",
                "Devondale Milk", "¥ 79.00", "产地", "澳大利亚", "devondale",
                "Kindle Oasis", "¥ 2399.00", "版本", "8GB", "kindle",
                "waitrose 早餐麦片", "¥ 179.00", "重量", "2Kg", "waitrose",
                "Mcvitie's 饼干", "¥ 14.90", "产地", "英国", "mcvitie",
                "Ferrero Rocher", "¥ 132.59", "重量", "300g", "ferrero",
                "Maltesers", "¥ 141.43", "重量", "118g", "maltesers",
                "Lindt", "¥ 139.43", "重量", "249g", "lindt",
                "Borggreve", "¥ 28.90", "重量", "640g", "borggreve"
        };
        countItem = new int[strings1.length / 5];
        myData = new ArrayList<ListItems>();
        int number = 0;
        for (int i = 0; i < strings1.length / 5; i++) {
            ListItems newItem = new ListItems();
            for(int j = 0; j < tempData.size(); j++) {
                if(strings1[i * 5 + 0] == tempData.get(j).getName()) number = 1;
                else number = 0;
            }
            newItem.setAttr(strings1[i * 5 + 0], strings1[i * 5 + 1], strings1[i * 5 + 2], strings1[i * 5 + 3], strings1[i * 5 + 4], number);
            myData.add(newItem);
            countItem[i] = 0;
        }
    }


    FloatingActionButton.OnClickListener carClickListener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, shopping_cart.class);
            // intent.putExtra("countItem", countItem);
            intent.putExtra("myData", myData);
            //startActivity(intent);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tempData = (ArrayList<ListItems>) data.getSerializableExtra("myData");
        for (int i = 0; i < myData.size(); i++) {
            for (int j = 0; j < tempData.size(); j++) {
                if (myData.get(i).getName() == tempData.get(j).getName()) myData.get(i).setNum(1);
                else myData.get(i).setNum(0);
            }
        }
    };
}



