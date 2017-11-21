package com.example.andrew.midterm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<personInfo> myData;

    private RLoopRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private GalleryAdapter mAdapter;
    private MusicService musicService;
    private FloatingActionButton FAB;
    private ImageButton searchButton, addButton;
    private EditText searchText;
    public boolean isMute = false;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    private void bindServiceConnection() {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    public android.os.Handler handler = new android.os.Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicService = new MusicService();
        bindServiceConnection();
        musicService.playOrPause();
        initData();//初始化数据

        searchButton = (ImageButton) findViewById(R.id.SearchButton); //搜索按钮
        addButton = (ImageButton) findViewById(R.id.AddButton); //添加按钮
        searchText = (EditText) findViewById(R.id.editText); //用于搜索的文字

        mRecyclerView = (RLoopRecyclerView) findViewById(R.id.myRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//删掉这个变成垂直滑动
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(myData);
        mAdapter.setDatas(myData);

        mRecyclerView.setAdapter(mAdapter);
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.soundButton);//静音按钮
        FAB.setOnClickListener(FABClickeListener);
        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), myData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemLongClickListener(new GalleryAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                //action
                Toast.makeText(getApplicationContext(), "bbb", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initData() {
        String string[] = {"曹操", "男", "100.0.0", "魏", "不知道", "caocao",
                           "刘备", "男", "100.0.0", "蜀", "不知道", "liubei",
                           "关羽", "男", "100.0.0", "蜀", "不知道", "guanyu",
                           "张飞", "男", "100.0.0", "蜀", "不知道", "zhangfei",
                           "孙权", "男", "100.0.0", "吴", "不知道", "sunquan",
                           "周瑜", "男", "100.0.0", "吴", "不知道", "zhouyu",
        };
        myData = new ArrayList<personInfo>();
        for(int i = 0; i < string.length / 6; i++) {
            personInfo newPerson = new personInfo(string[i*6+0], string[i*6+1], string[i*6+2],string[i*6+4],string[i*6+3],string[i*6+5]);
            myData.add(newPerson);
        }
    }

    View.OnClickListener FABClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "You clicked on the sound button", Toast.LENGTH_SHORT).show();
            musicService.playOrPause();
            if(isMute) {
               // FAB.setBackgroundResource(R.drawable.sound);
               // isMute = false;
            } else {
               // FAB.setImageResource(R.drawable.mute);
               // isMute = true;
            }
        }
    };
}
