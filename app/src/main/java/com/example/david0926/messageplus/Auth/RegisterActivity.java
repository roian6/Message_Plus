package com.example.david0926.messageplus.Auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.david0926.messageplus.Chat.RecycleModel_ChatPage;
import com.example.david0926.messageplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText id, password, passwordcheck, nickname, intro;

    ProgressBar regiProgress;
    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        context = this;

        id = findViewById(R.id.reg_id);
        nickname = findViewById(R.id.reg_nickname);
        intro = findViewById(R.id.reg_intro);
        password = findViewById(R.id.reg_password);
        passwordcheck = findViewById(R.id.reg_passwordcheck);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    private boolean isValidPasswd(String target) {
        Pattern p = Pattern.compile("(^.*(?=.{8,100})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$)");
        Matcher m = p.matcher(target);
        return m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }

    private boolean isValidEmail(String target) {
        if (target == null || TextUtils.isEmpty(target)) {
            return false;
        }
        else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void createAccount(final String email, final String password, final String passwordcheck, final String nickname, final String intro) {

        regiProgress = findViewById(R.id.progress_regi);
        regiProgress.setVisibility(View.VISIBLE);

        if (!isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), "이메일이 유효하지 않습니다",
                    Toast.LENGTH_SHORT).show();
            regiProgress.setVisibility(View.INVISIBLE);
            return;
        }

        if (isValidPasswd(password)) {
            Toast.makeText(getApplicationContext(), "패스워드가 유효하지 않습니다",
                    Toast.LENGTH_SHORT).show();
            regiProgress.setVisibility(View.INVISIBLE);
            return;
        }

        if (!password.equals(passwordcheck)) {
            Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다",
                    Toast.LENGTH_SHORT).show();
            regiProgress.setVisibility(View.INVISIBLE);
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String[] profileColorList = context.getResources().getStringArray(R.array.colors);
                            int profileColor = Color.parseColor(profileColorList[new Random().nextInt(profileColorList.length)]);
                            UserModel model = new UserModel(nickname, email, user.getUid(), intro, profileColor);
                            databaseReference.child("user").push().setValue(model);
                            Toast.makeText(getApplicationContext(), "환영합니다, " + id.getText().toString() + "!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "회원가입 실패",
                                    Toast.LENGTH_SHORT).show();
                            regiProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if(!nickname.getText().toString().equals("") && !intro.getText().toString().equals("") && !id.getText().toString().equals("") && !password.getText().toString().equals("") && !passwordcheck.getText().toString().equals("")){
                    createAccount(id.getText().toString(), password.getText().toString(), passwordcheck.getText().toString(), nickname.getText().toString(), intro.getText().toString());
                }
                else{
                    Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
