package com.example.david0926.messageplus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.david0926.messageplus.R;


public class Dialog_DevInfo extends DialogFragment{

    //개발 정보를 표시하는 Dialog

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //Dialog Builder 선언
        builder.setTitle("개발 정보") //Dialog 타이틀 설정
                .setMessage(R.string.dialog_devinfo) //Dialog 내용 설정
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() { //Dialog에 Negative 버튼 추가
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create(); //Builder 반환
    }
}
