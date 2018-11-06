package com.example.david0926.messageplus.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david0926.messageplus.R;

public class RecycleHolder_Chat extends RecyclerView.ViewHolder{

    public TextView name, msg, time;
    public ImageView profile;

    public RecycleHolder_Chat(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.chat_name);
        msg = itemView.findViewById(R.id.chat_msg);
        time = itemView.findViewById(R.id.chat_time);
        profile = itemView.findViewById(R.id.chat_profile);



    }
}