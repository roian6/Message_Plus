package com.example.david0926.messageplus.People;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserModel;
import com.example.david0926.messageplus.Chat.ChatPageActivity;
import com.example.david0926.messageplus.Chat.RecycleClick_Chat;
import com.example.david0926.messageplus.Chat.RecycleModel_Chat;
import com.example.david0926.messageplus.Chat.RecycleModel_ChatPage;
import com.example.david0926.messageplus.Dialog.Dialog_People;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tab_People extends Fragment{


    RecyclerView rcv;
    RecycleAdapter_People rcvAdap;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_people, container, false);
        rcv = v.findViewById(R.id.people_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(lm);
        rcvAdap = new RecycleAdapter_People();
        rcv.setAdapter(rcvAdap);

        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = v.findViewById(R.id.fab_people);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvAdap.notifyDataSetChanged();
                Toast.makeText(getContext(), "목록이 갱신되었습니다", Toast.LENGTH_SHORT).show();
            }
        });


//        View itemImageView = inflater.inflate(R.layout.recycle_item_people, container, false);
//        ImageView profile = itemImageView.findViewById(R.id.people_profile);
//        profile.setBackground(new ShapeDrawable(new OvalShape()));
//        profile.setClipToOutline(true);
//        model.setProfileId(R.drawable.omnyomnyom_profile);
//        model.setName("Hatban");
//        model.setMsg("#"+"Coding_Slave");


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
                if(!model.getEmail().equals(user.getEmail())){
                    boolean sameItem = false;
                    for(int i=0;i<rcvAdap.getItemCount();i++){
                        if(rcvAdap.getItems().get(i).getEmail().equals(model.getEmail())) sameItem = true;
                    }
                    if(!sameItem){
                        rcvAdap.add(model);
                        rcvAdap.notifyDataSetChanged();
                    }

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


//        for(int i=0;i<10;i++) {
////            itemImageView = inflater.inflate(R.layout.recycle_item_people, container, false);
////            profile = itemImageView.findViewById(R.id.people_profile);
////            profile.setBackground(new ShapeDrawable(new OvalShape()));
////            profile.setClipToOutline(true);
//            model = new RecycleModel_People();
//            model.setProfileId(R.drawable.ic_person);
//            model.setName("Someone");
//            model.setMsg("#"+"Coding All Day");
//            rcvAdap.add(model);
//            rcvAdap.notifyDataSetChanged();
//        }

        RecycleClick_People.addTo(rcv).setOnItemClickListener(new RecycleClick_People.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getContext(), Dialog_People.class);
                intent.putExtra("dialog_people_name", rcvAdap.getItems().get(position).getNickname());
                intent.putExtra("dialog_people_email", rcvAdap.getItems().get(position).getEmail());
                intent.putExtra("dialog_people_intro", rcvAdap.getItems().get(position).getIntro());
                intent.putExtra("dialog_people_profilenum", rcvAdap.getItems().get(position).getProfilenum());
                startActivity(intent);
            }
        });




        return v;
    }



}
