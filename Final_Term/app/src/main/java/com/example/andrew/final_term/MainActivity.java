package com.example.andrew.final_term;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.andrew.final_term.Service.DBService;
import com.example.andrew.final_term.Model.Info;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MainActivityRecyclerViewAdapter mAdapter;
    public ArrayList<Info> myData;
    public FloatingActionButton fab;
    //Image tempImage = new Image();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //Log.e("TAG", ((Integer) myData.size()).toString());
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        fab = (FloatingActionButton)findViewById(R.id.fab);

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
                intent.putExtra("lastModifiedTime", myData.get(position).lastModifiedTime);
                intent.putExtra("context", myData.get(position).context);
                startActivity(intent);

            }
        });

        mAdapter.setOnItemLongClickListener(new MainActivityRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setTitle("删除项目");
                ab.setMessage("确定要删除该项目吗？");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBService.getService(MainActivity.this).deleteInfo(myData.get(position).lastModifiedTime);
                        myData.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                ab.setNegativeButton("取消", null);
                ab.show();
                //Toast.makeText(getApplicationContext(), "Long click", Toast.LENGTH_SHORT).show();
//                DBService.getService(MainActivity.this).deleteInfo(myData.get(position).lastModifiedTime);
//                myData.remove(position);
//                mAdapter.notifyDataSetChanged();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteContextActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        init();
        mAdapter.setData(myData);
        mAdapter.notifyDataSetChanged();

        if (mAdapter.getItemCount() > 0) {
            int randomIndex = new Random().nextInt(mAdapter.getItemCount());
            Info info = mAdapter.getInfo(randomIndex);

            Intent intent = new Intent(NewAppWidget.STATIC_SATISFACTION);
            intent.putExtra("productIndex", randomIndex);
            intent.putExtra("info", (Parcelable) info);
            sendBroadcast(intent);
        }
        super.onStart();
    }

    public void init() {
        myData = DBService.getService(MainActivity.this).getAllInfo();
    }

}
