package com.example.david0926.messageplus.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.david0926.messageplus.Dialog.Dialog_ChatFab;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Tab_Chat extends Fragment{

    //채팅 탭 Fragment

    //Firebase Authentication, Database 가져오기
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    //RecyclerView 및 어댑터 선언
    RecyclerView rcv;
    RecycleAdapter_Chat rcvAdap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_chat, container, false); //채팅 탭의 레이아웃 뷰 가져오기

        firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기
        final FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기


        rcv = v.findViewById(R.id.chat_recycler); //RecyclerView findViewById
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        //최근 메세지가 상단에 표시되도록 item을 쌓는 방향 reverse
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        rcv.setLayoutManager(linearLayoutManager); //RecyclerView에 LayoutManager 지정

        //Adapter 선언 및 RecyclerView의 Adapter로 지정
        rcvAdap = new RecycleAdapter_Chat();
        rcv.setAdapter(rcvAdap);

        FloatingActionButton fab = v.findViewById(R.id.fab_chat); //FloatingActionButton 가져오기
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Fab가 클릭되었을 때
                DialogFragment dialogFragment = new Dialog_ChatFab(); //Dialog_ChatFab으로 DialogFragment 선언
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_chatfab"); //Dialog 실행
            }
        });


        databaseReference.child("message").addChildEventListener(new ChildEventListener() { //Firebase Database의 message 항목에 이벤트 리스너 생성
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { //message 항목에 값이 추가되었다면(메세지 수신)

                RecycleModel_ChatPage member = dataSnapshot.getValue(RecycleModel_ChatPage.class); //추가된 값을 RecycleModel_ChatPage 양식으로 DB에서 가져옴
                RecycleModel_Chat model = new RecycleModel_Chat(member.getName(),
                        member.getMsg(), member.getTime(), member.getNickname() ,member.getProfilenum()); //가져온 값을 RecycleModel_Chat 양식으로 캐스팅


                if(member.getTo().equals(user.getEmail())){ //만약 메세지의 받는 사람이 자신이라면
                    for(int i=0;i<rcvAdap.getItems().size();i++){ //현재 RecyclerView의 아이템 개수만큼 반복
                        if(member.getName().equals(rcvAdap.getItems().get(i).getName())){ //만약 RecyclerView의 i 위치에 있던 메세지를 보낸 사람과 현재 메세지를 보낸 사람이 일치한다면
                            rcvAdap.delete(i); //RecyclerView에서 중복되는 기존의 메세지 아이템을 삭제
                        }
                    }
                    rcvAdap.add(model); //RecyclerView에 항목 추가
                }
                else if (member.getName().equals(user.getEmail())){ //혹은 메세지를 보낸 사람이 자신이라면

                    for(int i=0;i<rcvAdap.getItems().size();i++){ //현재 RecyclerView의 아이템 개수만큼 반복
                        if(member.getTo().equals(rcvAdap.getItems().get(i).getName())){ //만약 RecyclerView의 i 위치에 있던 메세지와 현재 메세지의 보낸 사람이 모두 자신이라면
                            rcvAdap.delete(i); //RecyclerView에서 중복되는 기존의 메세지 아이템을 삭제
                        }
                    }
                    model.setName(member.getTo()); //아이템의 보낸 사람 이메일을 받는 사람(상대)로 변경
                    model.setNickname(member.getNicknameto()); //아이템의 보낸 사람 닉네임을 받는 사람(상대)로 변경
                    model.setMsg("회원님: " + member.getMsg()); //메세지 내용에 자신이 전송한 메세지임을 표시
                    rcvAdap.add(model); //RecyclerView에 항목 추가
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


        RecycleClick_Chat.addRecycler(rcv).setOnItemClickListener(new RecycleClick_Chat.OnItemClickListener() { //RecyclerViewd에 onClickListener 추가
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getContext(), ChatPageActivity.class); //채팅 화면으로 Intent 선언

                //Intent에 RecyclerView의 클릭된 위치 아이템에서 가져온 메세지 보낸 사람/보낸 사람의 닉네임 첨부
                intent.putExtra("name", rcvAdap.getItems().get(position).getName());
                intent.putExtra("nickname", rcvAdap.getItems().get(position).getNickname());
                startActivity(intent); //채팅 화면 Activity 실행
            }
        });

        return v; //뷰 반환

    }





}
