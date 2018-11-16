package com.example.david0926.messageplus.People;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david0926.messageplus.Auth.UserModel;
import com.example.david0926.messageplus.Chat.RecycleModel_Chat;
import com.example.david0926.messageplus.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter_People extends RecyclerView.Adapter<RecycleHolder_People> {

    //People 탭의 RecyclerView Adapter

    List<UserModel> items = new ArrayList<>(); //RecyclerView에 들어갈 아이템 저장 ArrayList 선언

    public List<UserModel> getItems() {
        return items;
    } //List의 아이템을 반환하는 함수

    public void add(UserModel data){ //리스트에 값을 추가하는 함수
        items.add(data); //리스트에 UserModel 양식으로 전달받은 값 추가
        notifyDataSetChanged(); //RecyclerView 갱신
    }

    @NonNull
    @Override
    public RecycleHolder_People onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_people, parent, false); //RecyclerView에 뿌려줄 아이템 뷰 설정
        return new RecycleHolder_People(v); //뷰 반환
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder_People holder, int position) {

        UserModel item = items.get(position); //리스트의 position 위치 값을 UserModel 양식으로 가져오기

        //가져온 값을 holder에 대입
        holder.nickname.setText(item.getNickname());
        holder.email.setText(item.getEmail());
        holder.intro.setText(item.getIntro());
        holder.profile.setImageDrawable(new ColorDrawable(item.getProfilenum()));
    }

    @Override
    public int getItemCount() {
        return items.size(); //리스트 크기 반환
    }
}
