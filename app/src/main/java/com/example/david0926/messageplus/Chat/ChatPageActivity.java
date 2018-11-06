package com.example.david0926.messageplus.Chat;

import android.content.Intent;
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
import android.widget.EditText;

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

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth firebaseAuth;

    RecyclerView rcv;
    RecycleAdapter_ChatPage rcvAdap;
    EditText input;

    String intentName, intentNickname;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chatpage);

        firebaseAuth = FirebaseAuth.getInstance();


        intentName = getIntent().getStringExtra("name");
        intentNickname = getIntent().getStringExtra("nickname");
        Toolbar toolbar = findViewById(R.id.toolbar_chatpage);
        toolbar.setTitle(intentNickname);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rcv = findViewById(R.id.recycler_chatpage);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcvAdap = new RecycleAdapter_ChatPage();
        rcv.setAdapter(rcvAdap);

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RecycleModel_ChatPage model = dataSnapshot.getValue(RecycleModel_ChatPage.class);
                if((model.getTo().equals(user.getEmail()) && intentName.equals(model.getName()) || (intentName.equals(model.getTo()) && model.getName().equals(user.getEmail())))) {
                    rcvAdap.add(model);
                }
                rcv.scrollToPosition(rcvAdap.getItemCount() -1 );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                rcvAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onSend(View view) {
        input = findViewById(R.id.chat_input);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(!input.getText().toString().equals("")){
            UserDB userDB = new UserDB();
            String nickname = userDB.getUserNickname(getApplicationContext());
            GetTimeDate getTimeDate = new GetTimeDate();
            RecycleModel_ChatPage model = new RecycleModel_ChatPage(user.getEmail(), user.getUid(), intentName, input.getText().toString(), getTimeDate.getTime(), getTimeDate.getDate(), nickname, intentNickname, userDB.getProfilenum(getApplicationContext()));
            databaseReference.child("message").push().setValue(model);
            input.setText("");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
