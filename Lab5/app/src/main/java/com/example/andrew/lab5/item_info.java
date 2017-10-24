package com.example.andrew.lab5;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/10/18.
 */



public class item_info extends AppCompatActivity {
     int clicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_info);
        TextView itemName = (TextView) findViewById(R.id.itemName);
        TextView itemInfo = (TextView) findViewById(R.id.itemInfo);
        TextView itemPrice = (TextView) findViewById(R.id.itemPrice);
        ImageView itemPic = (ImageView) findViewById(R.id.itemPic);
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(goBackListener) ;
        ImageButton starButton = (ImageButton) findViewById(R.id.starButton);
        starButton.setOnClickListener(starListener);


        ListItems thisItem = (ListItems) getIntent().getSerializableExtra("myData");
        itemName.setText(thisItem.getName());
        itemInfo.setText(thisItem.getCatagory() + "  " + thisItem.getInfo());
        itemPrice.setText(thisItem.getPrice());
        int resID = getResources().getIdentifier(thisItem.getSrc(), "drawable", "com.example.andrew.lab5");
        itemPic.setBackgroundResource(resID);


    }

    ImageButton.OnClickListener goBackListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(item_info.this, "aaa", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    ImageButton.OnClickListener starListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            ImageButton starButton = (ImageButton) findViewById(R.id.starButton);
            if (clicked == 0) {
                starButton.setImageResource(getResources().getIdentifier("full_star", "drawable", "com.example.andrew.lab5"));
                clicked = 1;
            }
            else if (clicked == 1) {
                starButton.setImageResource(getResources().getIdentifier("empty_star", "drawable", "com.example.andrew.lab5"));
                clicked = 0;
        }
        }
    };
}
