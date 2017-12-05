package com.example.andrew.lab10;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Andrew on 2017/12/4.
 */

public class AddItem extends AppCompatActivity {
    public Button addButton;
    public EditText name;
    public myDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        addButton = (Button) findViewById(R.id.aaa);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = ((EditText) findViewById(R.id.name)).getText().toString();
                String _birthday = ((EditText) findViewById(R.id.birthday)).getText().toString();
                String _gift = ((EditText) findViewById(R.id.gift)).getText().toString();
                //String _name = name.getText().toString();
                if(_name.equals("")) {
                    ((EditText) findViewById(R.id.name)).setHint("名字不可以为空");
                    Toast.makeText(getApplicationContext(),"名字为空，请完善",Toast.LENGTH_SHORT).show();
                } else {
                    db = new myDB(getApplicationContext(), "Contacts.db", null, 1);
                    SQLiteDatabase newDB = db.getWritableDatabase();
                    boolean canAdd = true;

                    Cursor cursor = newDB.query("Contacts", null, null, null, null, null, null);
                    int nameIndex = cursor.getColumnIndex("name");
                    for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
                        if(_name.equals(cursor.getString(nameIndex))) {
                            Toast.makeText(getApplicationContext(),"已经存在相同的联系人",Toast.LENGTH_SHORT).show();
                            canAdd = false;
                        }
                    }

                    if(canAdd) {
                        ContentValues values = new ContentValues();
                        values.put("name", _name);
                        values.put("birth", _birthday);
                        values.put("gift", _gift);
                        newDB.insert("Contacts", null, values);
                        finish();
                    }
                }
            }
        });
    }

}
