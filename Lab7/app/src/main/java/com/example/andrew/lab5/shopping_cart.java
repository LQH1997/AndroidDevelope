package com.example.andrew.lab5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/10/18.
 */



public class shopping_cart extends AppCompatActivity {

    ArrayList<ListItems> myData = new ArrayList<ListItems>();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CartAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        EventBus.getDefault().register(this);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycleview_cart);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new CartAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton mainPageButton = (FloatingActionButton) findViewById(R.id.fab1);
        mainPageButton.setOnClickListener(mainPageButtonListener);

        mAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view , int position){

                Intent intent = new Intent();
                intent.setClass(shopping_cart.this, item_info.class);
                intent.putExtra("myData", myData.get(position));
                startActivity(intent);


            }
        });

        mAdapter.setOnItemLongClickListener(new CartAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(shopping_cart.this);
                builder.setTitle("移除商品");
                builder.setMessage("从购物车中移除" + myData.get(position).getName());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myData.remove(position);
                        mAdapter.notifyItemRemoved(position);

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

    }

    FloatingActionButton.OnClickListener mainPageButtonListener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    EventBus.getDefault().unregister(this);
        Log.i("ShoppingCart", "OnDestory");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AllShoppingList event) {
        myData = event.getInfo();
    }


}
