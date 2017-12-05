package com.example.andrew.lab9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText pw, pwConfirm;
    Button OKBtn, ClearBtn;
    //String pw1, pw2;
    boolean havePassword = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pw = (EditText) findViewById(R.id.pw1);
        pwConfirm = (EditText) findViewById(R.id.pwConfirm);

        havePassword = checkPasswordExit();
        if(havePassword) {
            pw.setVisibility(View.INVISIBLE);
        }

        OKBtn =(Button) findViewById(R.id.OK);
        OKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // pw.setVisibility(View.INVISIBLE);
                String pw1, pw2;
                pw1 = pw.getText().toString();
                pw2 = pwConfirm.getText().toString();
                if(!havePassword) {
                    if (pw1.equals("")) {
                        pw.setHint("密码不能为空");
                        Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (pw2.equals("")) {
                        pwConfirm.setHint("确认密码不能为空");
                        Toast.makeText(getApplicationContext(), "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!pw1.equals(pw2)) {
                        pw.setText("");
                        pw.setHint("密码不匹配");
                        pwConfirm.setText("");
                        Toast.makeText(getApplicationContext(), "密码不匹配", Toast.LENGTH_SHORT).show();
                    } else {
                        savePassword(pw1);
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, FileEdit.class);
                        startActivity(intent);
                    }
                } else {
                    if(pw2.equals(getPassword())) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, FileEdit.class);
                        startActivity(intent);
                    } else {
                        pwConfirm.setText("");
                        pwConfirm.setHint("密码不正确");
                        Toast.makeText(getApplicationContext(), "密码不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ClearBtn = (Button) findViewById(R.id.clear);
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.setText("");
                pw.setHint("New Password");
                pwConfirm.setText("");
                pwConfirm.setHint("Confirm Password");
            }
        });
    }
    public void savePassword(String pw1) {
        Context context = getApplicationContext();
        SharedPreferences library = context.getSharedPreferences("MY_PASSWORD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = library.edit();
        editor.putString("PASSWARD", pw1);
        editor.commit();
    };

    public boolean checkPasswordExit() {
        Context context = getApplicationContext();
        SharedPreferences library = context.getSharedPreferences("MY_PASSWORD", Context.MODE_PRIVATE);
        String tempStr = library.getString("PASSWARD", "defaultpw");
       //sus Toast.makeText(getApplicationContext(), "111"+tempStr+"222", Toast.LENGTH_SHORT).show();
        if(tempStr.equals("defaultpw")) {
            return false;
        } else {
            return true;
        }
    };

    public String getPassword() {
        Context context = getApplicationContext();
        SharedPreferences library = context.getSharedPreferences("MY_PASSWORD", Context.MODE_PRIVATE);
        String tempStr = library.getString("PASSWARD", "defaultpw");
        return tempStr;
    }
}
