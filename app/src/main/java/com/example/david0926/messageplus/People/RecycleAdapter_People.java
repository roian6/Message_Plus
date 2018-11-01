package com.example.david0926.messageplus.People;

import android.support.annotation.NonNull;
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

    List<UserModel> items = new ArrayList<>();

    public List<UserModel> getItems() {
        return items;
    }

    public void add(UserModel data){
        items.add(data);
        notifyDataSetChanged();
    }

    public void delete(int position){
        items.remove(items.get(position));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecycleHolder_People onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_people, parent, false);
        return new RecycleHolder_People(v, R.drawable.ic_person);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder_People holder, int position) {
        UserModel item = items.get(position);
        holder.nickname.setText(item.getNickname());
        holder.email.setText(item.getEmail());
        holder.intro.setText(item.getIntro());
        //holder.msg.setText(item.getMsg());
        //holder.profile.setImageResource(items.get(position).getProfileId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
