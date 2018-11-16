package com.example.david0926.messageplus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Dialog_User extends DialogFragment{

    //Firebase Authentication 가져오기
    private FirebaseAuth firebaseAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) { //Dialog Builder 선언

        firebaseAuth = FirebaseAuth.getInstance(); //Firebase 현재 Auth 정보 가져오기
        FirebaseUser user = firebaseAuth.getCurrentUser(); //현재 유저 정보 가져오기
        UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //Dialog Builder 선언
        builder.setMessage("Email: "+user.getEmail() +"\nUid: "+ user.getUid()+"\nNickname: "+userDB.getUserNickname(getContext())) //Dialog 내용 설정
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //Dialog에 Negative 버튼 추가

            }
        });
        return builder.create(); //Builder 반환
    }
}
