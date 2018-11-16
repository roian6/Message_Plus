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

    //PreferenceFragmentCompat을 이용한 설정 기능 구현

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference); //설정 양식 가져오기

        Preference colorSetting = findPreference("setting_color"); //색상 설정 findPreference로 가져오기
        colorSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) { //설정이 변경되었다면
                preference.setSummary((String)newValue); //설정에 변경된 값 적용
                UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기
                userDB.setSettingColor(getContext(), (String)preference.getSummary()); //DB의 채팅방 색상 항목을 새로운 값으로 갱신
                Toast.makeText(getContext(), "채팅방 색상을 " + preference.getSummary()+"로 변경했습니다!", Toast.LENGTH_SHORT).show(); //변경 완료 토스트
                return true;
            }
        });

        Preference pushSetting = findPreference("setting_push"); //푸시 알림 설정 findPreference로 가져오기
        pushSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) { //설정이 변경되었다면

                UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기
                userDB.setSettingPush(getContext(), Boolean.valueOf(newValue.toString())); //DB의 푸시 알림 항목을 새로운 값으로 갱신

                if(userDB.isSettingPush(getContext())){ //설정이 활성화되었다면
                    Toast.makeText(getContext(), "푸시 알림이 활성화되었습니다", Toast.LENGTH_SHORT).show(); //활성화 안내 토스트
                }
                else{ //설정이 비활성화되었다면
                    Toast.makeText(getContext(), "푸시 알림이 비활성화되었습니다", Toast.LENGTH_SHORT).show(); //비활성화 안내 토스트
                }
                return true;
            }
        });

        Preference vibSetting = findPreference("setting_vib"); //진동 설정 findPreference로 가져오기
        vibSetting.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) { //설정이 변경되었다면

                UserDB userDB = new UserDB(); //현재 유저 정보가 담긴 DB 가져오기
                userDB.setSettingVib(getContext(), Boolean.valueOf(newValue.toString())); //DB의 진동 항목을 새로운 값으로 갱신
                if(userDB.isSettingVib(getContext())){ //설정이 활성화되었다면
                    Toast.makeText(getContext(), "진동이 활성화되었습니다", Toast.LENGTH_SHORT).show(); //활성화 안내 토스트
                }
                else{ //설정이 비활성화되었다면
                    Toast.makeText(getContext(), "진동이 비활성화되었습니다", Toast.LENGTH_SHORT).show(); //비활성화 안내 토스트
                }
                return true;
            }
        });



        //설정을 가져와 클릭되었을 때 각각 해당하는 Dialog를 띄움
        Preference userinfoSetting = findPreference("setting_userinfo"); //유저 정보
        userinfoSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialogFragment = new Dialog_User();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_user");
                return true;
            }
        });
        Preference newpasswordSetting = findPreference("setting_newpassword"); //비밀번호 변경
        newpasswordSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialogFragment = new Dialog_NewPassword();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog_newpassword");
                return true;
            }
        });
        Preference deleteuserSetting = findPreference("setting_deleteuser"); //회원 탈퇴
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
