package com.example.david0926.messageplus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.LoginActivity;
import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Dialog_DeleteUser extends DialogFragment{

    private FirebaseAuth firebaseAuth;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("회원 탈퇴")
                .setMessage("정말로 탈퇴하시겠습니까? 유저 정보는 삭제되지 않습니다.")
                .setPositiveButton("탈퇴", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        user.delete();
                        Toast.makeText(getContext(), "탈퇴하였습니다.", Toast.LENGTH_SHORT).show();
                        getActivity().finishAffinity();
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
