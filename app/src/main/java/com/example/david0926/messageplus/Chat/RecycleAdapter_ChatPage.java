package com.example.david0926.messageplus.Chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter_ChatPage extends RecyclerView.Adapter<RecycleHolder_ChatPage> {

    //채팅 화면의 RecyclerView Adapter

    List<RecycleModel_ChatPage> items = new ArrayList<>(); //RecyclerView에 들어갈 아이템 저장 ArrayList 선언

    private FirebaseAuth firebaseAuth; //Firebase Authentication 가져오기

    public void add(RecycleModel_ChatPage data){ //리스트에 값을 추가하는 함수
        items.add(data); //리스트에 RecycleModel_ChatPage 양식으로 전달받은 값 추가
        notifyDataSetChanged(); //RecyclerView 갱신
    }

    @Override
    public int getItemViewType(int position) { //메세지의 뷰 형태 구분

        firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기
        FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기
        if(items.get(position).getTime().equals("")){ //만약 해당 메세지의 시간 값이 주어지지 않았다면(대화 시작을 알리는 메세지)
            return 2; //2 반환(대화 시작을 알리는 아이템 뷰)
        }
        if(items.get(position).getUid().equals(user.getUid())){ //만약 해당 메세지를 자신이 보냈다면
            return 1; //1 반환(자신이 보낸 말풍선 아이템 뷰)
        }
        else return 0; //만약 자신에게 온 메세지라면 0 반환(상대가 보낸 말풍선 아이템 뷰)
    }

    @NonNull
    @Override
    public RecycleHolder_ChatPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0: //반환된 값이 0일때
                View v1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item_chatpage_get, parent, false);
                return new RecycleHolder_ChatPage(v1); //상대가 보낸 말풍선 아이템 뷰 반환
            case 1: //반환된 값이 1일때
                View v2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item_chatpage_send, parent, false);
                return new RecycleHolder_ChatPage(v2); //자신이 보낸 말풍선 아이템 뷰 반환
            case 2: //반환된 값이 2일때
                View v3 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item_chatpage_new, parent, false);
                return new RecycleHolder_ChatPage(v3); //대화 시작을 알리는 아이템 뷰 반환
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder_ChatPage holder, int position) {
        RecycleModel_ChatPage item = items.get(position); //리스트의 position 위치 값을 RecycleModel_ChatPage 양식으로 가져오기

        //가져온 값을 holder에 대입
        holder.msg.setText(item.getMsg());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    } //리스트 크기 반환
}
