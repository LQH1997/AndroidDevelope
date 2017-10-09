package com.example.andrew.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logInImage = (ImageView) findViewById(R.id.sysuPic);
        logInImage.setOnClickListener(logInListener);
        RadioButton stuSelected = (RadioButton) findViewById(R.id.stuButton);
        stuSelected.setOnClickListener(stuSelectedListener);
        RadioButton falSelected = (RadioButton) findViewById(R.id.falButton);
        falSelected.setOnClickListener(falSelectedListener);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button logInButton = (Button) findViewById(R.id.logInButton);
        signUpButton.setOnClickListener(signUpButtonListener);
        logInButton.setOnClickListener(logInButtonListener);
    }

    public View.OnClickListener logInListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
        }
    };

    public View.OnClickListener stuSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showContent("您选择了学生");
        }
    };

    public View.OnClickListener falSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showContent("您选择了教职工");
        }
    };

    public View.OnClickListener signUpButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //注册按钮点击
            showContent("您点击了注册");
        }
    };

    public View.OnClickListener logInButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //登陆按钮点击
            showContent("您点击了登陆");
        }
    };

    public void showContent(CharSequence myText) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.my_custom_toast));
        TextView text = (TextView) layout.findViewById(R.id.toastText);
        text.setText(myText);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

//        Toast myToast = Toast.makeText(MainActivity.this, myText, Toast.LENGTH_SHORT);
//        myToast.setGravity(Gravity.BOTTOM, 0, 0);
//        myToast.show();
    };
}
