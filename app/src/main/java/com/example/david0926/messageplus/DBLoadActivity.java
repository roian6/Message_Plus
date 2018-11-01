package com.example.david0926.messageplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Auth.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBLoadActivity extends AppCompatActivity{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbload);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference.child("user").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                UserModel model = new UserModel();
                model.setNickname(dataSnapshot.getValue(UserModel.class).getNickname());
                model.setEmail(dataSnapshot.getValue(UserModel.class).getEmail());
                model.setUserkey(dataSnapshot.getValue(UserModel.class).getUserkey());
                model.setIntro('#'+dataSnapshot.getValue(UserModel.class).getIntro());
                model.setProfilenum(dataSnapshot.getValue(UserModel.class).getProfilenum());
                FirebaseUser user = firebaseAuth.getCurrentUser();
                UserDB userDB = new UserDB();
                if(model.getUserkey().equals(user.getUid())){
                    userDB.add(getApplicationContext(), model);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
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


    }
}
