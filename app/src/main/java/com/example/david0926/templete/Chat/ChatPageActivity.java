package com.example.david0926.templete.Chat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.david0926.templete.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatPageActivity extends AppCompatActivity{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    RecyclerView rcv;
    RecycleAdapter_ChatPage rcvAdap;
    EditText input;

    String getTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chatpage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chatpage);
        toolbar.setTitle("Hatban");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rcv = findViewById(R.id.recycler_chatpage);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcvAdap = new RecycleAdapter_ChatPage();
        rcv.setAdapter(rcvAdap);
        RecycleModel_ChatPage model = new RecycleModel_ChatPage();
        model.setName("Hatban");
        model.setMsg("Hell World!");
        model.setTime(getTime());
        rcvAdap.add(model);
        rcvAdap.notifyDataSetChanged();

        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RecycleModel_ChatPage model = dataSnapshot.getValue(RecycleModel_ChatPage.class);
                rcvAdap.add(model);
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
    }

    public void onSend(View view) {
        input = findViewById(R.id.chat_input);
        if(input.getText().toString()!=""){
            RecycleModel_ChatPage model = new RecycleModel_ChatPage("Hatban", input.getText().toString(), getTime());
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
