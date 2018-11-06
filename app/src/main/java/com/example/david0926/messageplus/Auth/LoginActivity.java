package com.example.david0926.messageplus.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.david0926.messageplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity{

    EditText id, password;
    ProgressBar loginProgress;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Toast.makeText(getApplicationContext(), "안녕하세요, "+user.getEmail()+"!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, DBLoadActivity.class));
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }



    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_login:
                id = findViewById(R.id.login_id);
                password = findViewById(R.id.login_password);
                if(!id.getText().toString().equals("") && !password.getText().toString().equals("")){
                    login(id.getText().toString(), password.getText().toString());
                }
                else{
                    Toast.makeText(this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_newaccount:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }

    private void login(String email, String password) {
        loginProgress = findViewById(R.id.progress_login);
        loginProgress.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "안녕하세요, "+id.getText().toString()+"!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DBLoadActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "로그인 실패",
                                    Toast.LENGTH_SHORT).show();
                            loginProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
