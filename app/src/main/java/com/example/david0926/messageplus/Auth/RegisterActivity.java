package com.example.david0926.messageplus.Auth;

import android.content.Context;
import android.content.Intent;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity{

    EditText id, password, passwordcheck, nickname, intro;
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

        context=this;

        id = findViewById(R.id.reg_id);
        nickname = findViewById(R.id.reg_nickname);
        intro = findViewById(R.id.reg_intro);
        password = findViewById(R.id.reg_password);
        passwordcheck = findViewById(R.id.reg_passwordcheck);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    private boolean isValidPasswd(String target, String targetcheck) {
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$)");

        Matcher m = p.matcher(target);
        if (m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") && target.equals(targetcheck)){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isValidEmail(String target) {
        if (target == null || TextUtils.isEmpty(target)){
            return false;
        }
        else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private void createAccount(final String email, final String password, final String passwordcheck, final String nickname, final String intro) {
        if(!isValidEmail(email)){
            Toast.makeText(getApplicationContext(), "Email is not valid",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (isValidPasswd(password, passwordcheck)){
            Toast.makeText(getApplicationContext(), "PW is not valid",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Authentication Success",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserModel model = new UserModel(nickname, email, user.getUid(), intro, 1);
                            databaseReference.child("user").push().setValue(model);
                            Toast.makeText(getApplicationContext(), "Welcome, "+id.getText().toString()+"!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_register:
                createAccount(id.getText().toString(), password.getText().toString(), passwordcheck.getText().toString(), nickname.getText().toString(), intro.getText().toString());
                break;
        }
    }

}
