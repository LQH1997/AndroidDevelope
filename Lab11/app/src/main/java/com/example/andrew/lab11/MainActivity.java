package com.example.andrew.lab11;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public Button clearButton;
    public Button fetchButton;
    public ArrayList<Info> myData;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fetchButton = (Button) findViewById(R.id.fetchButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        init();
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = ((EditText) findViewById(R.id.searchText)).getText().toString();
                addUser(input);
                //Todo
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo
                ((EditText) findViewById(R.id.searchText)).setText("");
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("name", myData.get(position).getLogin());
                intent.setClass(MainActivity.this, RepoModel.class);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("确定要删除该项吗？");
                adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myData.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                adb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do Nothing
                    }
                });
                adb.show();

            }
        });
    }

    public void init() {
        myData = new ArrayList<Info>();
        //addUser("gfzheng");
    }


    public void addUser(String userName) {
        //Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        Log.i(" _testing", "in testing");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i(" _testing", "retrofit finished");
        GetInterfaces.GetUser service = retrofit.create(GetInterfaces.GetUser.class);
        Call<ResponseBody> repos = service.listRepos(userName);
        Log.i(" _testing", "call finished");

        repos.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String temp = new String(response.body().bytes());
                    Log.i("TAG", "result: " + temp);
                    Gson gson = new Gson();
                    Info thisInfo = gson.fromJson(temp, Info.class);
                    thisInfo.setBlog("blog: " + thisInfo.getBlog());
                    thisInfo.setId("id: " + thisInfo.getId());
                    myData.add(thisInfo);
                    progressBar.setVisibility(View.INVISIBLE);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "好像没有这个用户", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("tag", t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "好像没有这个用户", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(" _testing", "finished");
    }




}
