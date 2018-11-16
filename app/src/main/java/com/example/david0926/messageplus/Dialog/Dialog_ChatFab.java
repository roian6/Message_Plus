package com.example.david0926.messageplus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.david0926.messageplus.R;


public class Dialog_ChatFab extends DialogFragment{

    //채팅 탭의 Fab를 눌렀을 때 표시되는 Dialog

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //Dialog Builder 선언
        builder.setTitle("대화 시작하기") //Dialog 타이틀 설정
                .setMessage("프로필 클릭 -> 대화하기를 통해 대화를 시작할 수 있습니다.") //Dialog 내용 설정
                .setNegativeButton("확인", new DialogInterface.OnClickListener() { //Dialog에 Negative 버튼 추가
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create(); //Builder 반환
    }
}
