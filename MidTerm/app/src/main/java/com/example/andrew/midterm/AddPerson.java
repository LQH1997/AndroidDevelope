package com.example.andrew.midterm;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;

public class AddPerson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText hometown = (EditText) findViewById(R.id.hometown);
        final EditText birthdate = (EditText) findViewById(R.id.birthdate);
        final RadioGroup rg1 = (RadioGroup) findViewById(R.id.RG);
        final RadioGroup rg2 = (RadioGroup) findViewById(R.id.RG2);
        Button bt = (Button) findViewById(R.id.submit);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", name.getText().toString());
                bundle.putString("hometown", hometown.getText().toString());
                bundle.putString("hometown", hometown.getText().toString());
                bundle.putString("birthdate", birthdate.getText().toString());
                String gender = "男";
                String shili = "";
                int s = rg2.getCheckedRadioButtonId();
                if(rg1.getCheckedRadioButtonId() == R.id.female)
                    gender = "女";
                if(s == R.id.wei) shili = "魏";
                else if(s == R.id.shu) shili = "蜀";
                else if(s == R.id.wu) shili = "吴";
                bundle.putString("gender", gender);
                bundle.putString("shili", shili);
                bundle.putBoolean("add", true);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
