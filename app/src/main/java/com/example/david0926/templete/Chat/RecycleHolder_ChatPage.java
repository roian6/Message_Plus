package com.example.david0926.templete.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david0926.templete.R;

public class RecycleHolder_ChatPage extends RecyclerView.ViewHolder{

    public TextView name, msg, time;
//    public ImageView profile;
//    public int profileId;

    public RecycleHolder_ChatPage(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.chatpage_name);
        msg = itemView.findViewById(R.id.chatpage_msg);
        time = itemView.findViewById(R.id.chatpage_time);
//        profile = itemView.findViewById(R.id.chat_profile);
//        profileId = imageId;


    }
}
