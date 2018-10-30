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

import com.example.david0926.messageplus.R;
import com.example.david0926.messageplus.RecycleListener;
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

    List<RecycleModel_Chat> items = new ArrayList<>();
    List<String> chatList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_chat, container, false);
        rcv = v.findViewById(R.id.chat_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(lm);
        rcvAdap = new RecycleAdapter_Chat();
        rcv.setAdapter(rcvAdap);



        //recyclerview
//        View itemImageView = inflater.inflate(R.layout.recycle_item_chat, container, false);
//        ImageView profile = itemImageView.findViewById(R.id.chat_profile);
//        profile.setBackground(new ShapeDrawable(new OvalShape()));
//        profile.setClipToOutline(true);
        RecycleModel_Chat model = new RecycleModel_Chat();
        model.setProfileId(R.drawable.omnyomnyom_profile);
        model.setName("Hatban");
        model.setMsg("Have a nice day!");
        model.setTime("3:25 PM");
        chatList.add(model.getName());
        //rcvAdap.notifyDataSetChanged();

        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RecycleModel_Chat model = new RecycleModel_Chat();
                model.setName(dataSnapshot.getValue(RecycleModel_ChatPage.class).getName());
                model.setMsg(dataSnapshot.getValue(RecycleModel_ChatPage.class).getMsg());
                model.setTime(dataSnapshot.getValue(RecycleModel_ChatPage.class).getTime());
                model.setProfileId(R.drawable.ic_person);
                for(int i=0;i<chatList.size();i++){
                    if(!model.getName().equals(chatList.get(i))){
                        chatList.add(model.getName());
                        rcvAdap.add(model);
                        rcvAdap.notifyDataSetChanged();
                        return;
                    }
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

        for(int i=0;i<10;i++) {
//            itemImageView = inflater.inflate(R.layout.recycle_item_chat, container, false);
//            profile = itemImageView.findViewById(R.id.chat_profile);
//            profile.setBackground(new ShapeDrawable(new OvalShape()));
//            profile.setClipToOutline(true);
            model = new RecycleModel_Chat();
            model.setProfileId(R.drawable.ic_person);
            model.setName("Someone");
            model.setMsg("Hello World!");
            model.setTime("12:53 PM");
            rcvAdap.add(model);
            rcvAdap.notifyDataSetChanged();
        }

        rcv.addOnItemTouchListener(new RecycleListener(getContext(), rcv, new RecycleListener.OnClickListener() {
            @Override
            public void ItemClick(View v, int position) {
                Toast.makeText(getContext(), position + "clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), ChatPageActivity.class));

            }
        }));
        //end of recyclerview


        return v;


    }





}
