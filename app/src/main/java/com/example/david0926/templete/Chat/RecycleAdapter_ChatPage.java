package com.example.david0926.templete.Chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david0926.templete.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter_ChatPage extends RecyclerView.Adapter<RecycleHolder_ChatPage> {

    List<RecycleModel_ChatPage> items = new ArrayList<>();

    public void add(RecycleModel_ChatPage data){
        items.add(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecycleHolder_ChatPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_chatpage, parent, false);
        return new RecycleHolder_ChatPage(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder_ChatPage holder, int position) {
        RecycleModel_ChatPage item = items.get(position);
        holder.name.setText(item.getName());
        holder.msg.setText(item.getMsg());
        holder.time.setText(item.getTime());
        //holder.profile.setImageResource(items.get(position).getProfileId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
