package com.example.andrew.lab10;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Button addButton;
    public ArrayList<Info> myData;
    public myDB db;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    public String TABLE_NAME = "Contacts";
    public String DB_NAME = "Contacts.db";
    public String NO_MATCHING_CONTACT = "NO_MATCHING_CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = (Button) findViewById(R.id.addButton);


        db = new myDB(this, DB_NAME, null, 1);

        initData1();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
              //  intent.putExtra("myDatabase",db);
                intent.setClass(MainActivity.this, AddItem.class);
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycleview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(myData);
        mRecyclerView.setAdapter(mAdapter);

        //deal with click event here
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view , int position){
                LayoutInflater factor = LayoutInflater.from(MainActivity.this);
                final View dialigview = factor.inflate(R.layout.dialog_layout, null);
                final AlertDialog.Builder alartDialog = new AlertDialog.Builder(MainActivity.this);
                alartDialog.setView(dialigview);
                ((TextView) dialigview.findViewById(R.id.modifyName)).setText(myData.get(position).getName());
                ((EditText) dialigview.findViewById(R.id.modifyBirthday)).setText(myData.get(position).getBirthday());
                ((EditText) dialigview.findViewById(R.id.modifyGift)).setText(myData.get(position).getGift());
                String telNumer = findTelNumber(myData.get(position).getName());
                if(!telNumer.equals(NO_MATCHING_CONTACT)) {
                    ((TextView) dialigview.findViewById(R.id.telenumber)).setText(telNumer);
                } else {
                    ((LinearLayout) dialigview.findViewById(R.id.phoneNumberBar)).setVisibility(View.INVISIBLE);
                }

                alartDialog.setPositiveButton("保存修改",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int position) {

                        String modifiedBirthday = ((EditText) dialigview.findViewById(R.id.modifyBirthday)).getText().toString();
                        String modifiedGift = ((EditText) dialigview.findViewById(R.id.modifyGift)).getText().toString();
                        String __name = ((TextView) dialigview.findViewById(R.id.modifyName)).getText().toString();

                        SQLiteDatabase myDB = db.getWritableDatabase();

                        String updataString = "update Contacts set birth = '" + modifiedBirthday + "', gift = '"
                                + modifiedGift + "' where name = '" + __name +"'";
                        myDB.execSQL(updataString);

                        Toast.makeText(getApplicationContext(), modifiedGift, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"已修改",Toast.LENGTH_SHORT).show();
                        refreshData();
                    }
                });
                alartDialog.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"已放弃修改",Toast.LENGTH_SHORT).show();
                    }
                });

                alartDialog.show();
            }
        });

        //deal with long click event here
        mAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), myData.get(position).getName().toString(), Toast.LENGTH_SHORT).show();
                final String __name = myData.get(position).getName().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除信息");
                builder.setMessage("确认删除该项目：联系人 " + myData.get(position).getName().toString() + "?");
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase myDB = db.getWritableDatabase();
                        String deleteString = "delete from " + TABLE_NAME + " where name = '" + __name + "'";
                        myDB.execSQL(deleteString);
                        refreshData();
                        Toast.makeText(getApplicationContext(), "已经删除："+__name, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "已经确认删除",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    //to update data after typed in
    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    //test module
    public void initData1() {
        readData();
    }

    public void readData() {
        myData = new ArrayList<Info>();
        String result = "";
        SQLiteDatabase newDB = db.getReadableDatabase();
        Cursor cursor = newDB.query(TABLE_NAME, null, null, null, null, null, null);
        int nameIndex = cursor.getColumnIndex("name");
        int birthIndex = cursor.getColumnIndex("birth");
        int giftIndex = cursor.getColumnIndex("gift");
        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            Info newInfo = new Info(cursor.getString(nameIndex), cursor.getString(birthIndex), cursor.getString(giftIndex));
            myData.add(newInfo);
        }
        cursor.close();
    }

    public void refreshData() {
        readData();
        mAdapter.setData(myData);
        mAdapter.notifyDataSetChanged();
    }

    public String findTelNumber(String name) {
        boolean exits = false;
        String telNumber = NO_MATCHING_CONTACT;
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
            String tempStr = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if(tempStr.equals(name)) {
//                telNumber
                int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String contactID = cursor.getString(idColumn);
                Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);
                for(phone.moveToFirst(); !(phone.isAfterLast()); phone.moveToNext()) {
                    telNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
            }
        }
        return telNumber;
    }
}
