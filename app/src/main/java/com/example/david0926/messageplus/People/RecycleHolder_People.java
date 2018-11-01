package com.example.david0926.messageplus.People;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david0926.messageplus.R;

public class RecycleHolder_People extends RecyclerView.ViewHolder{

    public TextView nickname, email, intro;
    public ImageView profile;
    public int profileId;

    public RecycleHolder_People(View itemView, int imageId) {
        super(itemView);
        nickname = itemView.findViewById(R.id.people_name);
        email = itemView.findViewById(R.id.people_email);
        intro = itemView.findViewById(R.id.people_msg);
        profile = itemView.findViewById(R.id.people_profile);
        profileId = imageId;


    }
}
