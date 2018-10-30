package com.example.david0926.messageplus.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david0926.messageplus.R;

public class RecycleHolder_ChatPage extends RecyclerView.ViewHolder{

    public TextView msg, time;
//    public ImageView profile;
//    public int profileId;

    public RecycleHolder_ChatPage(View itemView) {
        super(itemView);
        msg = itemView.findViewById(R.id.chatpage_msg);
        time = itemView.findViewById(R.id.chatpage_time);
//        profile = itemView.findViewById(R.id.chat_profile);
//        profileId = imageId;


    }
}
