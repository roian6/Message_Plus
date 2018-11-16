package com.example.david0926.messageplus.People;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

public class PeopleActivity extends AppCompatActivity {

    //유저 프로필을 표시하는 Activity

    //유저 정보를 담을 변수 선언
    TextView name, email, intro;
    Button btn_ok, btn_cancel;
    ImageView profile;


    //Firebase Authentication, Database 가져오기
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //StatusBar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //전체화면
        setContentView(R.layout.dialog_people);


        Intent getIntent = getIntent(); //getIntent 선언

        //findViewById
        name = findViewById(R.id.dialog_people_name);
        email = findViewById(R.id.dialog_people_email);
        intro = findViewById(R.id.dialog_people_intro);
        profile = findViewById(R.id.profile_dialog_circle);

        //getIntent에서 Extra 값 가져오기
        name.setText(getIntent.getStringExtra("dialog_people_name"));
        email.setText(getIntent.getStringExtra("dialog_people_email"));
        intro.setText("_\n\n" + getIntent.getStringExtra("dialog_people_intro"));
        profile.setImageDrawable(new ColorDrawable(getIntent.getIntExtra("dialog_people_profilenum", 0)));

        //Button findViewById
        btn_ok = findViewById(R.id.btn_people_ok);
        btn_cancel = findViewById(R.id.btn_people_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //대화하기 버튼이 클릭되었을 때

                firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기
                FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기
                UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기
                String nickname = userDB.getUserNickname(getApplicationContext()); //DB에서 현재 유저의 닉네임 가져오기
                Intent getIntent = getIntent(); //getIntent 선언
                RecycleModel_ChatPage model = new RecycleModel_ChatPage(user.getEmail(), user.getUid(),
                        getIntent.getStringExtra("dialog_people_email"), "이제 대화를 시작할 수 있습니다.",
                        "", "", nickname, getIntent.getStringExtra("dialog_people_name"),
                        getIntent.getIntExtra("dialog_people_profilenum", 0)); //RecycleModel_ChatPage 양식으로 대화 시작을 알리는 메세지 작성
                databaseReference.child("message").push().setValue(model); //작성한 대화 시작 메세지를 Firebase DB의 message 항목에 추가
                Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class); //채팅 화면으로 Intent 선언

                //Intent에 getStringExtra에서 가져온 메세지를 보낸 사람/보낸 사람의 닉네임 첨부
                intent.putExtra("name", getIntent.getStringExtra("dialog_people_email"));
                intent.putExtra("nickname", getIntent.getStringExtra("dialog_people_name"));
                startActivity(intent); //채팅 화면 Activity 실행
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //취소 버튼을 누르면 Activity 종료
            }
        });


    }
}
