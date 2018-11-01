package com.example.david0926.messageplus.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.david0926.messageplus.R;

public class RecycleClick_Chat {
    private final RecyclerView rcv;
    private OnItemClickListener onItemClickListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                RecyclerView.ViewHolder holder = rcv.getChildViewHolder(v);
                onItemClickListener.onItemClicked(rcv, holder.getAdapterPosition(), v);
            }
        }
    };


    private RecyclerView.OnChildAttachStateChangeListener stateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (onItemClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private RecycleClick_Chat(RecyclerView recyclerView) {
        rcv = recyclerView;
        rcv.setTag(R.id.chat_recycler, this);
        rcv.addOnChildAttachStateChangeListener(stateChangeListener);
    }

    public static RecycleClick_Chat addTo(RecyclerView view) {
        RecycleClick_Chat support = (RecycleClick_Chat) view.getTag(R.id.chat_recycler);
        if (support == null) {
            support = new RecycleClick_Chat(view);
        }
        return support;
    }

    public static RecycleClick_Chat removeFrom(RecyclerView view) {
        RecycleClick_Chat support = (RecycleClick_Chat) view.getTag(R.id.chat_recycler);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RecycleClick_Chat setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(stateChangeListener);
        view.setTag(R.id.chat_recycler, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }




}
