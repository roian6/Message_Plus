package com.example.david0926.messageplus.Dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Chat.ChatPageActivity;
import com.example.david0926.messageplus.Chat.RecycleModel_ChatPage;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dialog_People extends AppCompatActivity {

    TextView name, email, intro;
    Button btn_ok, btn_cancel;
    ImageView profile;




    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_people);

//        LinearLayout view = findViewById(android.R.id.content);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
//        layoutParams.width = view.getWidth()/2;
//        layoutParams.height = view.getHeight()/2;
//
//        view.setLayoutParams(layoutParams);


        Intent getIntent = getIntent();
        name = findViewById(R.id.dialog_people_name);
        email = findViewById(R.id.dialog_people_email);
        intro = findViewById(R.id.dialog_people_intro);
        profile = findViewById(R.id.profile_dialog);
        name.setText(getIntent.getStringExtra("dialog_people_name"));
        email.setText(getIntent.getStringExtra("dialog_people_email"));
        intro.setText("_\n\n" + getIntent.getStringExtra("dialog_people_intro"));
        profile.setBackgroundColor(getIntent.getIntExtra("dialog_people_profilenum", 0));

        btn_ok = findViewById(R.id.btn_people_ok);
        btn_cancel = findViewById(R.id.btn_people_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                UserDB userDB = new UserDB();
                String nickname = userDB.getUserNickname(getApplicationContext());
                Intent getIntent = getIntent();
                RecycleModel_ChatPage model = new RecycleModel_ChatPage(user.getEmail(), user.getUid(), getIntent.getStringExtra("dialog_people_email"), "이제 대화를 시작할 수 있습니다.", "", "", nickname, getIntent.getStringExtra("dialog_people_name"), getIntent.getIntExtra("dialog_people_profilenum", 0));
                databaseReference.child("message").push().setValue(model);
                Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
                intent.putExtra("name", getIntent.getStringExtra("dialog_people_email"));
                intent.putExtra("nickname", getIntent.getStringExtra("dialog_people_name"));
                startActivity(intent);
                finish();
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
