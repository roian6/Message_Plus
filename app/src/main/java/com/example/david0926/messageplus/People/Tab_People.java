package com.example.david0926.messageplus.People;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserModel;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tab_People extends Fragment{

    //People 탭 Fragment


    //Firebase Authentication, Database 가져오기
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    //RecyclerView 및 어댑터 선언
    RecyclerView rcv;
    RecycleAdapter_People rcvAdap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_people, container, false); //채팅 탭의 레이아웃 뷰 가져오기

        firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기

        rcv = v.findViewById(R.id.people_recycler); //RecyclerView findViewById
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(lm); //RecyclerView에 LayoutManager 지정

        //Adapter 선언 및 RecyclerView의 Adapter로 지정
        rcvAdap = new RecycleAdapter_People();
        rcv.setAdapter(rcvAdap);

        FloatingActionButton fab = v.findViewById(R.id.fab_people); //FloatingActionButton 가져오기
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Fab가 클릭되었을 때
                rcvAdap.notifyDataSetChanged(); //RecyclerView의 아이템 갱신
                Toast.makeText(getContext(), "목록이 갱신되었습니다", Toast.LENGTH_SHORT).show(); //목록 갱신 토스트
            }
        });


        databaseReference.child("user").addChildEventListener(new ChildEventListener() { //Firebase Database의 user 항목에 이벤트 리스너 생성
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { //user 항목에 값이 추가되었다면

                UserModel model = dataSnapshot.getValue(UserModel.class); //추가된 값을 UserModel 양식으로 DB에서 가져옴
                FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기

                if(!model.getEmail().equals(user.getEmail())){ //만약 가져온 유저 정보가 자신의 프로필이 아니라면
                    boolean sameItem = false; //아이템 중복 방지를 위한 boolean 변수 선언
                    for(int i=0;i<rcvAdap.getItemCount();i++){ //현재 RecyclerView의 아이템 개수만큼 반복
                        if(rcvAdap.getItems().get(i).getEmail().equals(model.getEmail())) sameItem = true; //만약 가져온 프로필이 이미 RecyclerView에 있다면 중복 여부를 true로 설정
                    }
                    if(!sameItem){ //만약 현재 가져온 프로필이 기존 항목과 중복되지 않는다면
                        rcvAdap.add(model); //RecyclerView에 항목 추가
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

        RecycleClick_People.addRecycler(rcv).setOnItemClickListener(new RecycleClick_People.OnItemClickListener() { //RecyclerViewd에 onClickListener 추가
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getContext(), PeopleActivity.class); //프로필 화면으로 Intent 선언

                //Intent에 RecyclerView의 클릭된 위치 아이템에서 가져온 메세지 유저 정보 첨부
                intent.putExtra("dialog_people_name", rcvAdap.getItems().get(position).getNickname());
                intent.putExtra("dialog_people_email", rcvAdap.getItems().get(position).getEmail());
                intent.putExtra("dialog_people_intro", rcvAdap.getItems().get(position).getIntro());
                intent.putExtra("dialog_people_profilenum", rcvAdap.getItems().get(position).getProfilenum());
                startActivity(intent); //프로필 화면 Activity 실행
            }
        });


        return v; //뷰 반환
    }



}
