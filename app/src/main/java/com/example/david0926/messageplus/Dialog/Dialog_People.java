package com.example.david0926.messageplus.Dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Chat.RecycleModel_ChatPage;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dialog_People extends Activity {

    TextView name, email, intro;
    Button btn_ok, btn_cancel;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_people);





        name = findViewById(R.id.dialog_people_name);
        email = findViewById(R.id.dialog_people_email);
        intro = findViewById(R.id.dialog_people_intro);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("dialog_people_name"));
        email.setText(intent.getStringExtra("dialog_people_email"));
        intro.setText(intent.getStringExtra("dialog_people_intro"));

        btn_ok = findViewById(R.id.btn_people_ok);
        btn_cancel = findViewById(R.id.btn_people_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent intent = getIntent();
                    UserDB userDB = new UserDB();
                    String nickname = userDB.getUserNickname(getApplicationContext());
                    RecycleModel_ChatPage model = new RecycleModel_ChatPage(user.getEmail(), user.getUid(), intent.getStringExtra("dialog_people_email"), "이제 대화를 시작할 수 있습니다.", "", nickname, intent.getStringExtra("dialog_people_name"));
                    databaseReference.child("message").push().setValue(model);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
