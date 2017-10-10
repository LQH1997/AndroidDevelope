package com.example.andrew.lab4;

import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logInImage = (ImageView) findViewById(R.id.sysuPic);
        logInImage.setOnClickListener(imagineClickedListener);
        RadioButton stuSelected = (RadioButton) findViewById(R.id.stuButton);
        stuSelected.setOnClickListener(stuSelectedListener);
        RadioButton falSelected = (RadioButton) findViewById(R.id.falButton);
         falSelected.setOnClickListener(falSelectedListener);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button logInButton = (Button) findViewById(R.id.logInButton);
        signUpButton.setOnClickListener(signUpButtonListener);
        logInButton.setOnClickListener(logInButtonListener);
        //   RadioGroup stuOrFalSelect = (RadioGroup) findViewById(R.id.radioGroup);
        //  stuOrFalSelect.setOnCheckedChangeListener(changeChoiceListener);
      //  EditText stuNumber = (EditText) findViewById(R.id.editText);
       // EditText password = (EditText) findViewById(R.id.editText2);
       // Button checkButtonClicked = (Button) findViewById(R.id.checkButton);
       // checkButtonClicked.setOnClickListener(checkClicked);
    }

    //public View.setOnCheckedChangeListener changChoiceListener = new View.setOnCheckedChangeListener() {
//
  //  }


    public View.OnClickListener imagineClickedListener = new View.OnClickListener() {
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("上传头像");
            final String[] choices = {"拍摄", "从相册选择"};
           builder.setItems(choices, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showContent("您选择了" + choices[which]);
                };
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    showContent("您选择了取消");
                }
            });
            builder.show();

        }
    };

    public View.OnClickListener stuSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton stuBtn = (RadioButton) findViewById(R.id.stuButton);
            if(stuBtn.isChecked());
            else showContent("您选择了学生");
        }
    };

    public View.OnClickListener falSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton falBtn = (RadioButton) findViewById(R.id.falButton);
            if(falBtn.isChecked());
            showContent("您选择了教职工");
        }
    };

    public View.OnClickListener signUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //注册按钮点击
            RadioButton stuBut = (RadioButton) findViewById(R.id.stuButton);
            if(stuBut.isChecked()) {
                showContent("学生注册功能尚未启用");
            } else {
                showContent("教职工注册功能尚未启用");
            }
           // showContent("您点击了注册");
        }
    };

    public View.OnClickListener logInButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //登陆按钮点击
            //EditText  a = (EditText) findViewById(R.id.EditText)
           // setContentView(R.layout.activity_main);
              EditText stuNumber = (EditText) findViewById(R.id.editText);
             EditText password = (EditText) findViewById(R.id.editText2);
            String str1 = stuNumber.getText().toString();
            String str2 = password.getText().toString();
            String str3 = "123456";
            if(str1.equals("123456") && str2.equals("6666")) {
                showContent("登陆成功");
            }
            else {
                if(str1.equals("")) showContent("学号不能为空");
                else if(str2.equals("")) showContent("密码不能为空");
                else showContent("学号或密码错误");
            }
        }
    };

    public View.OnClickListener checkButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public void showContent(CharSequence myText) {
        View view = findViewById(R.id.myLayout);
        Snackbar.make(view, myText, Snackbar.LENGTH_SHORT)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setDuration(5000)
                .show();
    };

    public void showContentByToast(CharSequence myText) {
            LayoutInflater inflater = getLayoutInflater();
              final View layout = inflater.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.my_custom_toast));
              TextView text = (TextView) layout.findViewById(R.id.toastText);
                text.setText(myText);
                final Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
    };
}
