package com.example.david0926.messageplus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.david0926.messageplus.Auth.LoginActivity;


public class IntroActivity extends AppCompatActivity {

    //앱 인트로를 보여주는 Activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_intro); //인트로 실행

        Handler handler = new Handler(); //Handler 선언
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { //1000ms(=1초) 후에 실행
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);  //로그인 화면으로 Intent 선언
                startActivity(intent); //로그인 화면 Activity 실행
                finish(); //현재 Activity 종료
            }
        }, 1000);
    }
}
