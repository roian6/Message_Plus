package com.example.david0926.messageplus.People;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david0926.messageplus.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleHolder_People extends RecyclerView.ViewHolder{

    //People 탭의 RecyclerView ViewHolder

    //TextView, ImageView 선언
    public TextView nickname, email, intro;
    public CircleImageView profile;

    //ViewHolder
    public RecycleHolder_People(View itemView) {
        super(itemView);
        //각 아이템들을 RecyclerView 아이템 뷰의 항목과 연결
        nickname = itemView.findViewById(R.id.people_name);
        email = itemView.findViewById(R.id.people_email);
        intro = itemView.findViewById(R.id.people_msg);
        profile = itemView.findViewById(R.id.people_profile_circle);
    }
}
