package com.example.david0926.messageplus.Chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.GetTimeDate;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatPageActivity extends AppCompatActivity{

    //채팅 화면을 담당하는 Activity

    //Firebase Authentication, Database 가져오기
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    //RecyclerView 및 어댑터 선언
    RecyclerView rcv;
    RecycleAdapter_ChatPage rcvAdap;

    //채팅 입력 EditText, Button 선언
    EditText input;
    Button sendBtn;
    String intentName, intentNickname; //getIntent() 에서 값을 받아올 변수 선언


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //StatusBar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //전체화면
        setContentView(R.layout.activity_chatpage);

        LinearLayout v = findViewById(R.id.activity_chatpage); //채팅 화면 뷰 가져오기
        UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기
        v.setBackgroundColor(Color.parseColor(userDB.getSettingColor(getApplicationContext()))); //사용자가 지정한 색상으로 채팅방 배경색 설정

        firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기


        //getIntent()에서 대화 상대의 이메일과 닉네임을 가져와 변수에 저장
        intentName = getIntent().getStringExtra("name");
        intentNickname = getIntent().getStringExtra("nickname");


        Toolbar toolbar = findViewById(R.id.toolbar_chatpage); //ToolBar 가져오기
        toolbar.setTitle(intentNickname); //ToolBar의 Title을 대화 상대의 닉네임으로 설정
        toolbar.setTitleTextColor(Color.WHITE); //Title 글자 색상을 흰색으로 설정

        setSupportActionBar(toolbar); //커스텀한 툴바를 ActionBar로 지정

        //ActionBar에 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rcv = findViewById(R.id.recycler_chatpage); //RecyclerView findViewById
        rcv.setLayoutManager(new LinearLayoutManager(this)); //RecyclerView에 LayoutManager 지정

        //Adapter 선언 및 RecyclerView의 Adapter로 지정
        rcvAdap = new RecycleAdapter_ChatPage();
        rcv.setAdapter(rcvAdap);


        final FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기


        databaseReference.child("message").addChildEventListener(new ChildEventListener() { //Firebase Database의 message 항목에 이벤트 리스너 생성
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { //message 항목에 값이 추가되었다면(메세지 수신)

                RecycleModel_ChatPage model = dataSnapshot.getValue(RecycleModel_ChatPage.class); // 추가된 값을 RecycleModel_ChatPage 양식으로 DB에서 가져옴

                boolean newChat = true; //해당 유저와의 첫 대화인지 확인할 boolean 변수 선언

                if(model.getTime().equals("")){ //만약 받아온 메세지에 전송시간이 명시되어있지 않다면(대화 시작을 알리는 메세지라면)

                    for(int i=0;i<rcvAdap.getItemCount();i++){ //현재 RecyclerView의 아이템 개수만큼 반복
                        if(rcvAdap.getItemViewType(i)==2) newChat = false; //Adapter의 현재 항목에 대화 시작을 알리는 메세지가 이미 있다면 첫 대화 여부를 false로 지정
                    }
                    if(newChat){ //만약 처음으로 전송된 대화 시작 안내 메세지라면
                        rcvAdap.add(model); //RecyclerView에 항목 추가
                        rcv.scrollToPosition(rcvAdap.getItemCount() -1 ); //RecyclerView의 스크롤을 맨 아래로 내리기
                    }

                }
                else if((model.getTo().equals(user.getEmail()) && intentName.equals(model.getName()) //메세지의 받는 사람이 자신이고 보낸 사람이 현재 대화 상대라면
                        || (intentName.equals(model.getTo()) && model.getName().equals(user.getEmail())))) { //혹은 자신이 전송한 메세지이고 받는 사람이 현재 대화 상대라면
                    rcvAdap.add(model); //RecyclerView에 항목 추가
                    rcv.scrollToPosition(rcvAdap.getItemCount() -1 ); //RecyclerView의 스크롤을 맨 아래로 내리기
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendBtn = findViewById(R.id.btn_send); //Button findViewById

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //전송 버튼을 클릭했을 때

                input = findViewById(R.id.chat_input); //EditText findViewById

                firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기

                FirebaseUser user = firebaseAuth.getCurrentUser();//현재 유저 정보 가져오기

                if(!input.getText().toString().equals("")){ //만약 메세지 입력창이 비어있지 않다면
                    UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기
                    String nickname = userDB.getUserNickname(getApplicationContext()); //DB에서 가져온 현재 유저의 닉네임을 변수에 저장
                    GetTimeDate getTimeDate = new GetTimeDate(); //시간/날짜 호출 클래스 가져오기

                    //RecycleModel_ChatPage 양식에 메세지 정보 저장
                    RecycleModel_ChatPage model = new RecycleModel_ChatPage(user.getEmail(), user.getUid(),
                            intentName, input.getText().toString(), getTimeDate.getTime(), getTimeDate.getDate(),
                            nickname, intentNickname, userDB.getProfilenum(getApplicationContext()));

                    databaseReference.child("message").push().setValue(model); //Firebase DB의 message 항목에 저장된 양식 추가
                    input.setText(""); //메세지 입력창 초기화
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() { //ActionBar의 뒤로가기 버튼이 눌리면
        finish(); //현재 액티비티 종료
        return true;
    }
}
