package com.example.david0926.messageplus.People;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.david0926.messageplus.R;

public class RecycleClick_People {
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

    private RecycleClick_People(RecyclerView recyclerView) {
        rcv = recyclerView;
        rcv.setTag(R.id.people_recycler, this);
        rcv.addOnChildAttachStateChangeListener(stateChangeListener);
    }

    public static RecycleClick_People addTo(RecyclerView view) {
        RecycleClick_People support = (RecycleClick_People) view.getTag(R.id.people_recycler);
        if (support == null) {
            support = new RecycleClick_People(view);
        }
        return support;
    }

    public static RecycleClick_People removeFrom(RecyclerView view) {
        RecycleClick_People support = (RecycleClick_People) view.getTag(R.id.people_recycler);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RecycleClick_People setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(stateChangeListener);
        view.setTag(R.id.people_recycler, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }




}
