package com.example.david0926.messageplus.Setting;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Button;
import android.widget.Toast;

import com.example.david0926.messageplus.Auth.UserDB;
import com.example.david0926.messageplus.Dialog.Dialog_DeleteUser;
import com.example.david0926.messageplus.Dialog.Dialog_NewPassword;
import com.example.david0926.messageplus.Dialog.Dialog_User;
import com.example.david0926.messageplus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingFrag extends PreferenceFragmentCompat{


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference);

        Preference colorSetting = findPreference("setting_color");
        colorSetting.setSummary(colorSetting.getSummary());
        colorSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String)newValue);
                UserDB userDB = new UserDB();
                userDB.setSettingColor(getContext(), (String)preference.getSummary());
                Toast.makeText(getContext(), preference.getSummary(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference pushSetting = findPreference("setting_push");
        pushSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                UserDB userDB = new UserDB();

                userDB.setSettingPush(getContext(), Boolean.valueOf(newValue.toString()));
                if(userDB.isSettingPush(getContext())){
                    Toast.makeText(getContext(), "푸시 알림이 활성화되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "푸시 알림이 비활성화되었습니다", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Preference vibSetting = findPreference("setting_vib");
        vibSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                UserDB userDB = new UserDB();

                userDB.setSettingVib(getContext(), Boolean.valueOf(newValue.toString()));
                if(userDB.isSettingVib(getContext())){
                    Toast.makeText(getContext(), "진동이 활성화되었습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "진동이 비활성화되었습니다", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Preference userinfoSetting = findPreference("setting_userinfo");
        userinfoSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialogFragment = new Dialog_User();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_user");
                return true;
            }
        });

        Preference newpasswordSetting = findPreference("setting_newpassword");
        newpasswordSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialogFragment = new Dialog_NewPassword();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_newpassword");
                return true;
            }
        });

        Preference deleteuserSetting = findPreference("setting_deleteuser");
        deleteuserSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialogFragment = new Dialog_DeleteUser();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_deleteuser");
                return true;
            }
        });

    }
}
