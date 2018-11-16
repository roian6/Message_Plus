package com.example.david0926.messageplus.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david0926.messageplus.R;

public class RecycleHolder_ChatPage extends RecyclerView.ViewHolder{

    //채팅 화면 RecyclerView의 ViewHolder
    
    public TextView msg, time; //TextView 선언

    //ViewHolder
    public RecycleHolder_ChatPage(View itemView) {
        super(itemView);
        //각 아이템들을 RecyclerView 아이템 뷰의 항목과 연결
        msg = itemView.findViewById(R.id.chatpage_msg);
        time = itemView.findViewById(R.id.chatpage_time);
    }
}
