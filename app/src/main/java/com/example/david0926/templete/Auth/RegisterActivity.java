package com.example.david0926.templete.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.david0926.templete.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity{

    EditText id, password, nickname;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);



    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_register:
                nickname = findViewById(R.id.reg_nickname);
                id = findViewById(R.id.reg_id);
                password = findViewById(R.id.reg_password);
                firebaseAuth.createUserWithEmailAndPassword(id.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "auth failed", Toast.LENGTH_SHORT).show();
                                }


                            }

                        });

                Toast.makeText(this, "Welcome, "+id.getText().toString()+"!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userUpdate(FirebaseUser user){
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
//            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
//
//            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
//            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
//        }
    }
}
