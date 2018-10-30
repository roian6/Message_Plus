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

    List<RecycleModel_ChatPage> items = new ArrayList<>();

    private FirebaseAuth firebaseAuth;

    public void add(RecycleModel_ChatPage data){

        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(items.get(position).getUid().equals(user.getUid())){
            return 1;
        }
        else return 0;
    }

    @NonNull
    @Override
    public RecycleHolder_ChatPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                View v1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item_chatpage_get, parent, false);
                return new RecycleHolder_ChatPage(v1);
            case 1:
                View v2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item_chatpage_send, parent, false);
                return new RecycleHolder_ChatPage(v2);
            default:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycle_item_chatpage_get, parent, false);
                return new RecycleHolder_ChatPage(v);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder_ChatPage holder, int position) {
        RecycleModel_ChatPage item = items.get(position);
        holder.msg.setText(item.getMsg());
        holder.time.setText(item.getTime());
        //holder.profile.setImageResource(items.get(position).getProfileId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
