package com.example.andrew.lab11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.andrew.lab11.R.layout.repo_model;

/**
 * Created by Andrew on 2017/12/12.
 */

public class RepoModel extends AppCompatActivity {

    public ArrayList<model> myData;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private repoModelAdapter mAdapter;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_model);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String name = (String) getIntent().getStringExtra("name");
        Toast.makeText(RepoModel.this, name, Toast.LENGTH_SHORT).show();

        init(name);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new repoModelAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void init(final String name) {
        myData = new ArrayList<model>();
       // Info newInfo = new Info("aaa", "bbb", "ccc");
       // myData.add(newInfo);
       // Info newInfo1 = new Info("aaa1", "bbb1", "ccc1");
       // myData.add(newInfo1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                getUserRepo(name);
                //progressBar.setVisibility(View.INVISIBLE);
            }
        }).start();
       // getUserRepo(name);

    }

    public void getUserRepo(final String userName) {
        //Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();
     //   progressBar.setVisibility(View.VISIBLE);
        Log.i(" _testing2", "in testing");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i(" _testing2", "retrofit finished");
        GetInterfaces.GetUserRepo service = retrofit.create(GetInterfaces.GetUserRepo.class);
        Call<ResponseBody> repos = service.listRepos(userName);
        Log.i(" _testing2", "call finished");

        repos.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Gson gson = new Gson();
                try {
                    //String temp[] = new String(response.body().get(0).bytes());

                    //List<Student> students;
 //                   students = gson.fromJson(jsonData, new TypeToken<List<Student>>(){}.getType());

                    String temp = new String(response.body().bytes());
                    Log.i("aaaaaa", temp);
                    List<model> repos;
                    repos = gson.fromJson(temp, new TypeToken<List<model>>(){}.getType());
                    for(int i = 0; i < repos.size(); i++) {
                        model tempModel = repos.get(i);
                        Toast.makeText(getApplicationContext(), "aaa"+tempModel.getDescription(), Toast.LENGTH_SHORT).show();
                        if((repos.get(i).getDescription())==null) repos.get(i).setDescription("这个用户很懒，什么也没写");
                        myData.add(repos.get(i));

                        //if(myData.get(i).getDescription().equals("null")) {
                         //   myData.get(i).description = "这个用户很懒，什么也没写";
                        //}
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RepoModel.this, "好像没有这个用户" + userName, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("tag", t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RepoModel.this, "好像没有这个用户"+userName, Toast.LENGTH_SHORT).show();

            }
        });
        Log.i(" _testing2", "finished");
       // progressBar.setVisibility(View.INVISIBLE);
    }





}
