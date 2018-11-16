package com.example.david0926.messageplus.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.david0926.messageplus.R;

public class RecycleClick_Chat {

    //채팅 탭의 RecyclerView 클릭 리스너

    //RecyclerView, OnItemClickListener 선언
    private final RecyclerView rcv;
    private OnItemClickListener onItemClickListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) { //RecyclerView 아이템 클릭 시
            if (onItemClickListener != null) { //OnItemClickListener가 null이 아니라면
                RecyclerView.ViewHolder holder = rcv.getChildViewHolder(v); //해당 아이템의 holder 호출
                rcv.setTag(R.id.chat_recycler, this); //태그 설정
                onItemClickListener.onItemClicked(rcv, holder.getAdapterPosition(), v); //아이템 클릭 전달
            }
        }
    };


    private RecyclerView.OnChildAttachStateChangeListener stateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) { //아이템 뷰가 추가되었을 때
            if (onItemClickListener != null) { //OnItemClickListener가 null이 아니라면
                view.setOnClickListener(onClickListener); //해당 뷰에 OnClickListener 추가
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private RecycleClick_Chat(RecyclerView recyclerView) { //RecyclerView onClick 함수
        rcv = recyclerView; //RecyclerView 가져오기
        rcv.setTag(R.id.chat_recycler, this); //태그 설정
        rcv.addOnChildAttachStateChangeListener(stateChangeListener); //OnChildAttachStateChangeListener 추가
    }

    public static RecycleClick_Chat addRecycler(RecyclerView view) { //RecyclerView에 해당 함수 추가하기

        //전달받은 RecyclerView에 RecycleClick_Chat 추가
        RecycleClick_Chat support = (RecycleClick_Chat) view.getTag(R.id.chat_recycler);
        if (support == null) {
            support = new RecycleClick_Chat(view);
        }
        return support;
    }


    public RecycleClick_Chat setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener; //OnItemClickListener 설정
        return this;
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v); //아이템 클릭 전달 함수
    }


}
