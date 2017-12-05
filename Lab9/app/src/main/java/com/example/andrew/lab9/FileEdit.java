package com.example.andrew.lab9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andrew on 2017/11/28.
 */

public class FileEdit  extends AppCompatActivity {
    EditText name, context;
    Button save, load, clear, delete;
    //String str1, str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);

        name = (EditText) findViewById(R.id.name);
        context = (EditText) findViewById(R.id.context);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        clear = (Button) findViewById(R.id.clear);
        delete = (Button) findViewById(R.id.delete);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1, str2;
                str1 = name.getText().toString();
                str2 = context.getText().toString();
                if(!(str1.equals("") && str2.equals(""))) {
                    saveNewContextByStream(str1, str2);
                    Toast.makeText(getApplicationContext(), "Save successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if(str1.equals("")) Toast.makeText(getApplicationContext(),"第一项不能为空",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getApplicationContext(),"第二项不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1, str2;
                str1 = name.getText().toString();
                //str2 = findContext(str1);
                str2 = findContextByStream(str1);
                //if(!str2.equals("NoMatchingKey")) {
                if(!str2.equals("")) {
                    context.setText(str2);
                    Toast.makeText(getApplicationContext(), "Load successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    context.setText("");
                    Toast.makeText(getApplicationContext(), "Fail to load file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                context.setText("");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1;
                str1 = name.getText().toString();
                context.setText("");
                deleteContextByStream(str1);
                //Toast.makeText(getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveNewContext(String str1, String str2) {
        Context context = getApplicationContext();
        SharedPreferences library = context.getSharedPreferences("MY_Context", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = library.edit();
        editor.putString(str1, str2);
        editor.commit();
    }

    public void saveNewContextByStream(String str1, String str2) {
        try(FileOutputStream fileOutputStream = openFileOutput(str1, MODE_PRIVATE)) {
            //fileOutputStream.write(str1.getBytes());
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
            Log.i("TAG", "save success");
        } catch (IOException exp) {
            Log.i("TAG", "save fail");
        }
    }

    public String findContext(String str1) {
        Context context = getApplicationContext();
        SharedPreferences library = context.getSharedPreferences("MY_Context", Context.MODE_PRIVATE);
        String str = library.getString(str1, "NoMatchingKey");
        return  str;
    }

    public String findContextByStream(String str1) {
        try(FileInputStream fileInputStream = openFileInput(str1)) {
            byte[] context = new byte[fileInputStream.available()];
            fileInputStream.read(context);
            Log.i("TAG", "load success");
            fileInputStream.close();
            return new String(context);

        } catch (IOException exp) {
            Log.i("TAG", "can't find file");
            return "";
        }

    };

    public void deleteContext(String str1) {
        Context context = getApplicationContext();
        SharedPreferences library = context.getSharedPreferences("MY_Context", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = library.edit();
        editor.remove(str1);
        editor.commit();
    }

    public void deleteContextByStream(String str1) {
        File file = new File(str1);
        if(deleteFile(str1)) {
            Toast.makeText(getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
            Log.i("TAG", "deleted");
        } else {
            Toast.makeText(getApplicationContext(), "Delete Failed", Toast.LENGTH_SHORT).show();
            Log.i("TAG", "delete failed");
        }
    }

}
