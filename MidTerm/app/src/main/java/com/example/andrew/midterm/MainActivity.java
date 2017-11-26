package com.example.andrew.midterm;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static int count = 0;

    ArrayList<personInfo> myData;
    ArrayList<personInfo> Data;
    SQLiteDatabase db;
    String[] imgs = {"caocao", "guanyu", "liubei", "sunquan", "zhangfei", "zhouyu"};
    int curId = -1;

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
    private boolean hasPermission = false;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;

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

        verifyStoragePermission();
        musicService = new MusicService();
        bindServiceConnection();
        musicService.playOrPause();
        initData();//初始化数据
        Data = myData;

        searchButton = (ImageButton) findViewById(R.id.SearchButton); //搜索按钮
        addButton = (ImageButton) findViewById(R.id.AddButton); //添加按钮
        searchText = (EditText) findViewById(R.id.editText); //用于搜索的文字

        mRecyclerView = (RLoopRecyclerView) findViewById(R.id.myRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//删掉这个变成垂直滑动
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(Data);
        mAdapter.setDatas(Data);

        mRecyclerView.setAdapter(mAdapter);
        FAB = (FloatingActionButton) findViewById(R.id.soundButton);//静音按钮
        FAB.setOnClickListener(FABClickeListener);
        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), myData.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Info.class);
                Bundle bundle = new Bundle();
                personInfo p = Data.get(position);
                curId = p.getId();
                bundle.putInt("id", p.getId());
                bundle.putString("name", p.getName());
                bundle.putString("gender", p.getGender());
                bundle.putString("birthdate", p.getBirthDate());
                bundle.putString("shili", p.getShili());
                bundle.putString("hometown", p.getHomeTown());
                bundle.putString("img", p.getPersonPic());
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });

        mAdapter.setOnItemLongClickListener(new GalleryAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                //action
                Toast.makeText(getApplicationContext(), "bbb", Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPerson.class);
                startActivityForResult(intent, 1);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = searchText.getText().toString();
                Data = new ArrayList<personInfo>();
                Cursor cursor = db.query("person", null, "name=?", new String[]{ temp }, null, null, "id");
                if(cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String gender = cursor.getString(cursor.getColumnIndex("gender"));
                        String birthdate = cursor.getString(cursor.getColumnIndex("birthdate"));
                        String shili = cursor.getString(cursor.getColumnIndex("shili"));
                        String hometown = cursor.getString(cursor.getColumnIndex("hometown"));
                        String img = cursor.getString(cursor.getColumnIndex("img"));
                        personInfo p = new personInfo(id, name, gender, birthdate, hometown, shili, img);
                        Data.add(p);
                    } while(cursor.moveToNext());
                }
                cursor.close();
                mAdapter.setDatas(Data);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if(requestCode == 1) {
                int index = (int)(Math.random() * 6);
                String name = bundle.getString("name"), gender = bundle.getString("gender"), birth = bundle.getString("birthdate"),
                        shili = bundle.getString("shili"), hometown = bundle.getString("hometown");
                personInfo newPerson = new personInfo(count, name, gender, birth,
                        shili, hometown, imgs[index]);
                myData.add(0, newPerson);
                ContentValues values = new ContentValues();
                values.put("id", count);
                values.put("name", name);
                values.put("gender", gender);
                values.put("birthdate", birth);
                values.put("shili", shili);
                values.put("hometown", hometown);
                values.put("img", imgs[index]);
                db.insert("person", null, values);
                count++;
            }
            else {
                if(bundle.getBoolean("deleted")) {
                    int i;
                    for(i = 0; i < myData.size(); i++) {
                        if(myData.get(i).getId() == curId) break;
                    }
                    myData.remove(i);
                }
                else if(bundle.getBoolean("changed")) {
                    String name = bundle.getString("name"), gender = bundle.getString("gender"), birth = bundle.getString("birthdate"),
                            shili = bundle.getString("shili"), hometown = bundle.getString("hometown");
                    int i;
                    for(i = 0; i < myData.size(); i++) {
                        if(myData.get(i).getId() == curId) break;
                    }
                    personInfo p = myData.get(i);
                    p.setName(name);
                    p.setBirthDate(birth);
                    p.setGender(gender);
                    p.setShili(shili);
                }
            }
            Data = myData;
            mAdapter.setDatas(Data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initData() {
        myData = new ArrayList<personInfo>();
        db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("create table if not exists person ("
                  + "id integer primary key, "
                  + "name text, "
                  + "gender text, "
                  + "birthdate text, "
                  + "shili text, "
                  + "hometown text, "
                  + "img text)");
        Cursor cursor = db.query("person", null, null, null, null, null, "id");
        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String birthdate = cursor.getString(cursor.getColumnIndex("birthdate"));
                String shili = cursor.getString(cursor.getColumnIndex("shili"));
                String hometown = cursor.getString(cursor.getColumnIndex("hometown"));
                String img = cursor.getString(cursor.getColumnIndex("img"));
                personInfo p = new personInfo(id, name, gender, birthdate, hometown, shili, img);
                myData.add(p);
                count++;
            } while(cursor.moveToNext());
        }
        cursor.close();
    }

    private void verifyStoragePermission() {
        try {
            int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                hasPermission = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            hasPermission = true;
        } else {
            System.exit(0);
        }
        return;
    }

    View.OnClickListener FABClickeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "You clicked on the sound button", Toast.LENGTH_SHORT).show();
            musicService.playOrPause();
            if(isMute) {
               FAB.setImageResource(R.drawable.sound);
               isMute = false;
            } else {
               FAB.setImageResource(R.drawable.mute);
               isMute = true;
            }
        }
    };
}
