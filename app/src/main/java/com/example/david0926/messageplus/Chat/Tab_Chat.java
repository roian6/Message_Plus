package com.example.david0926.messageplus.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Auth.UserModel;
import com.example.david0926.messageplus.R;
import com.example.david0926.messageplus.RecycleListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Tab_Chat extends Fragment{

    RecyclerView rcv;
    RecycleAdapter_Chat rcvAdap;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth firebaseAuth;

    UserDB userDB = new UserDB();

    List<RecycleModel_Chat> items = new ArrayList<>();
    List<String> chatList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_chat, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        rcv = v.findViewById(R.id.chat_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rcv.setLayoutManager(linearLayoutManager);
        rcvAdap = new RecycleAdapter_Chat();
        rcv.setAdapter(rcvAdap);



        //recyclerview
//        View itemImageView = inflater.inflate(R.layout.recycle_item_chat, container, false);
//        ImageView profile = itemImageView.findViewById(R.id.chat_profile);
//        profile.setBackground(new ShapeDrawable(new OvalShape()));
//        profile.setClipToOutline(true);


        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RecycleModel_Chat model = new RecycleModel_Chat();
                RecycleModel_ChatPage member = dataSnapshot.getValue(RecycleModel_ChatPage.class);
                model.setName(member.getName());
                model.setMsg(member.getMsg());
                model.setTime(member.getTime());
                model.setNickname(member.getNickname());
                model.setProfilenum(member.getProfilenum());
                //model.setProfileNum(member.get);

                if(member.getTo().equals(user.getEmail())){
                    for(int i=0;i<rcvAdap.getItems().size();i++){
                        if(member.getName().equals(rcvAdap.getItems().get(i).getName())){
                            rcvAdap.delete(i);
                        }
                    }
                    items.add(model);
                    rcvAdap.add(model);
                    rcvAdap.notifyDataSetChanged();
                }
                else if (member.getName().equals(user.getEmail())){

                    for(int i=0;i<rcvAdap.getItems().size();i++){
                        if(member.getTo().equals(rcvAdap.getItems().get(i).getName())){
                            rcvAdap.delete(i);
                        }
                    }
                    model.setName(member.getTo());
                    model.setNickname(member.getNicknameto());
                    model.setMsg("회원님: " + member.getMsg());
                    items.add(model);
                    rcvAdap.add(model);
                    rcvAdap.notifyDataSetChanged();
                }


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

//        for(int i=0;i<items.size();i++){
//
//        }

        RecycleClick_Chat.addTo(rcv).setOnItemClickListener(new RecycleClick_Chat.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getContext(), ChatPageActivity.class);
                intent.putExtra("name", rcvAdap.getItems().get(position).getName());
                intent.putExtra("nickname", rcvAdap.getItems().get(position).getNickname());
                startActivity(intent);
            }
        });
        //end of recyclerview


        return v;


    }





}
