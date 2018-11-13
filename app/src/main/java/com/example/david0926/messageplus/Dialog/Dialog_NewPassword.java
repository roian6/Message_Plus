package com.example.david0926.messageplus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Dialog_NewPassword extends DialogFragment{

    private FirebaseAuth firebaseAuth;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("비밀전호 재설정")
                .setMessage("이메일 주소로 비밀번호 재설정 메일을 전송합니다")
                .setPositiveButton("전송", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.sendPasswordResetEmail(user.getEmail());
                        Toast.makeText(getContext(), "성공적으로 전송했습니다!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
