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

    private FirebaseAuth firebaseAuth;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserDB userDB = new UserDB();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Email: "+user.getEmail() +"\nUid: "+ user.getUid()+"\nNickname: "+userDB.getUserNickname(getContext()))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
