package com.example.andrew.final_term;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.andrew.final_term.Service.DBService;
import com.example.andrew.final_term.Model.Info;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/12/29.
 */

public class NoteContextActivity extends AppCompatActivity {
        private RecyclerView mRecyclerView;
        private LinearLayoutManager mLayoutManager;
        private MainActivityRecyclerViewAdapter mAdapter;
        private long lastModifiedTime;

        private int mode = EDITING;
        private static int EDITING = 1;
        private static int CREATING = 2;

        EditText inputContext;
        Button btnComplete;

        ImageButton addTextButton;
        ImageButton addImageButton;
        ImageButton checkButton;
        ImageButton deleteButton;

        LinearLayout inputLayout;
        ScrollView input;

        ArrayList<CheckBox> checkBoxes;

        static final int IMAGE = 1;




        public ArrayList<Info> myData;

        // AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //Image tempImage = new Image();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.note_content);

            Intent intent = getIntent();
            inputContext = (EditText) findViewById(R.id.input_context);
            btnComplete = (Button)findViewById(R.id.btn_complete);

            addTextButton = (ImageButton) findViewById(R.id.addTextButton);
            addImageButton = (ImageButton) findViewById(R.id.addImageButton);
            checkButton = (ImageButton) findViewById(R.id.checkButton);
            checkButton.setTag("NO");
            CheckBox initCB = (CheckBox) findViewById(R.id.initCB);
            deleteButton = (ImageButton) findViewById(R.id.deleteButton);
            inputLayout = (LinearLayout) findViewById(R.id.input);
            input = (ScrollView) findViewById(R.id.scrollInput);
            checkBoxes = new ArrayList<CheckBox>();
            checkBoxes.add(initCB);

            btnComplete.setVisibility(View.VISIBLE);

            lastModifiedTime = intent.getLongExtra("lastModifiedTime", 0L);
            mode = lastModifiedTime == 0 ? CREATING : EDITING;
            if (mode == EDITING) {
                String context = intent.getStringExtra("context");
                inputContext.setText(context);
            } else  if (mode == CREATING){
                inputContext.setText("");
            }

            inputContext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //btnComplete.setVisibility(View.VISIBLE);
                    //inputContext.Edit
                }
            });

            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mode == EDITING) {
                        DBService.getService(NoteContextActivity.this).updateInfo(lastModifiedTime, inputContext.getText().toString());
//                        Intent intent = new Intent(NewAppWidget.APPWIDGET_UPDATE);
//                        intent.putExtra("lastModifiedTime", lastModifiedTime);
//                        sendBroadcast(intent);
                    } else if (mode == CREATING){
                        DBService.getService(NoteContextActivity.this).addInfo(inputContext.getText().toString());
                    }
                    finish();
                }
            });

            addImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  ImageView imageView = new ImageView(getApplicationContext());
                   // DisplayMetrics dm = new DisplayMetrics();
                    //int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getWindowManager().getDefaultDisplay().getMetrics(dm));
                    //RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    //        height);
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE);
                  //  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(1000,1000);
                  //  lp.setMargins(10,10,10,10);
                  //  imageView.setLayoutParams(lp);
                  //  imageView.setImageResource(R.drawable.download);//在这里选择并且添加图片
                  //  inputLayout.addView(imageView, lp);
                  //  Log.e("input", "pic");
                }
            });



            addTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = new EditText(getApplicationContext());
                    LinearLayout newLL = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp1.setMargins(10,10,10,10);
                    newLL.setLayoutParams(lp1); //the new linear layout
                    CheckBox cb = new CheckBox(getApplicationContext()); //checkbox
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(100,100);
                    cb.setLayoutParams(lp2);
                    if(checkButton.getTag().equals("NO")) cb.setVisibility(View.GONE);
                    checkBoxes.add(cb);
                    newLL.addView(cb);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10,10,10,10);
                    newLL.addView(editText);
                    editText.setHint("在这里输入文字");
                    editText.setBackground(null);
                    inputLayout.addView(newLL);
                    Log.e("input", "text");
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(NoteContextActivity.this);
                    ab.setTitle("删除项目");
                    ab.setMessage("确定要删除该项目吗？");
                    ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete();
                        }
                    });
                    ab.setNegativeButton("取消", null);
                    ab.show();
                }
            });

            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkButton.getTag().equals("NO")) {
                        checkButton.setTag("YES");
                        for(int i = 0; i < checkBoxes.size(); i++) {
                            checkBoxes.get(i).setVisibility(View.VISIBLE);
                        }
                    } else {
                        for(int i = 0; i < checkBoxes.size(); i++) {
                            checkButton.setTag("NO");
                            checkBoxes.get(i).setVisibility(View.GONE);
                        }
                    }
                }
            });

        }

        public void delete() {

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            //获取图片路径
            if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                showImage(imagePath);
                c.close();
            }
        }

        public void showImage(String imagePath) {
            ImageView imageView = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(1000,1000);
            lp.setMargins(10,10,10,10);
          //  imageView.setLayoutParams(lp);
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
              imageView.setImageBitmap(bm);//在这里选择并且添加图片
            inputLayout.addView(imageView, lp);
            Log.e("input", "pic");

//            Bitmap bm = BitmapFactory.decodeFile(imagePath);
          // ((ImageView)findViewById(R.id.image)).setImageBitmap(bm);

        }

}
