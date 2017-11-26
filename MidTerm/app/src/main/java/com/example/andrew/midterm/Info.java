package com.example.andrew.midterm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Info extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private TextView name;
    private TextView gender;
    private TextView birthdate;
    private TextView shili;
    private TextView hometown;
    private Button change;
    private Button delete;

    private Integer id;
    private String nameStr;
    private String genderStr;
    private String birthdateStr;
    private String shiliStr;
    private String hometownStr;

    private boolean changed = false;
    private boolean deleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findView();
        setOnClickListener();

        getPerson();
    }

    @Override
    public void onBackPressed() {
        saveAndFinish();
        super.onBackPressed();
    }

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                saveAndFinish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndFinish() {
        Bundle bundle = new Bundle();
        if (changed) {
            Intent intent =  new Intent();

            bundle.putBoolean("changed", true);

            bundle.putInt("id", id);
            bundle.putString("name", name.getText().toString());
            bundle.putString("gender", gender.getText().toString());
            bundle.putString("birthdate", birthdate.getText().toString());
            bundle.putString("shili", shili.getText().toString());
            bundle.putString("hometown", hometown.getText().toString());

            // 在数据库进行修改
            SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("name", name.getText().toString());
            values.put("gender", gender.getText().toString());
            values.put("birthdate", birthdate.getText().toString());
            values.put("shili", shili.getText().toString());
            values.put("hometown", hometown.getText().toString());

            db.update("person", values, "id = ?", new String[]{ id.toString() });

            intent.putExtras(bundle);

            setResult(RESULT_OK, intent);

        } else {
            Intent intent = new Intent();
            intent.putExtra("changed", false);
            intent.putExtra("deleted", false);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private void findView() {
        img = (ImageView)findViewById(R.id.img);
        name =  (TextView)findViewById(R.id.name);
        gender = (TextView)findViewById(R.id.gender);
        birthdate = (TextView)findViewById(R.id.birthdate);
        shili = (TextView)findViewById(R.id.shili) ;
        hometown = (TextView)findViewById(R.id.hometown);
        change = (Button)findViewById(R.id.change);
        delete = (Button)findViewById(R.id.delete);
    }


    private void getPerson() {
        Bundle bundle = getIntent().getExtras();

        Class myDrawable = R.drawable.class;
        Field field = null;
        int r_id ;
        try {
            field = myDrawable.getField(bundle.getString("img"));
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id=R.drawable.mute;
        }
        img.setImageResource(r_id);

        id = bundle.getInt("id");
        nameStr = bundle.getString("name");
        genderStr = bundle.getString("gender");
        birthdateStr = bundle.getString("birthdate");
        shiliStr = bundle.getString("shili");
        hometownStr = bundle.getString("hometown");

        name.setText(nameStr);
        gender.setText(genderStr);
        birthdate.setText(birthdateStr);
        shili.setText(shiliStr);
        hometown.setText(hometownStr);
    }

    private void setOnClickListener() {
        name.setOnClickListener(this);
        gender.setOnClickListener(this);
        birthdate.setOnClickListener(this);
        shili.setOnClickListener(this);
        hometown.setOnClickListener(this);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameStr != name.getText() || genderStr != gender.getText() || birthdateStr != birthdate.getText()
                        || shiliStr != shili.getText() || hometownStr != hometown.getText())
                    changed = true;
                Toast.makeText(Info.this, "已成功修改", Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent();
                intent.putExtra("deleted", true);
                intent.putExtra("id", id);

                // 在数据库进行删除
                SQLiteDatabase db = openOrCreateDatabase("app.db", MODE_PRIVATE, null);
                db.delete("person", "id = ?", new String[]{ id.toString() });

                setResult(RESULT_OK, intent);
                Toast.makeText(Info.this, "已成功删除", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        TextView textview = ((TextView)view);
        textview.setCursorVisible(true);
        textview.setFocusableInTouchMode(true);
        textview.setInputType(InputType.TYPE_CLASS_TEXT);
        textview.requestFocus(); //to trigger the soft input
    }

}
