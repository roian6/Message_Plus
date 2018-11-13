package com.example.david0926.messageplus.Auth;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class UserDB {

    //현재 유저에 대한 정보를 저장하는 DB

    static SharedPreferences getSharedPreferences(Context context) { //SharedPreferences 호출 함수
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void add(Context context, UserModel data){ //DB에 값을 추가하는 함수
        SharedPreferences.Editor editor = getSharedPreferences(context).edit(); //SharedPreference 에디터 생성
        //에디터에 받아온 값 저장
        editor.putString("nickname", data.getNickname())
        .putString("email", data.getEmail())
        .putString("userkey", data.getUserkey())
        .putString("intro", data.getIntro())
        .putInt("profilenum", data.getProfilenum())
        .apply(); //SharedPreference에 반영
}
    public void setSettingColor(Context context, String color){ //채팅방 색상 설정
        SharedPreferences.Editor editor = getSharedPreferences(context).edit(); //에디터 생성
        editor.putString("setting_color", color) //에디터에 받아온 값 저장
        .apply(); //값 반영
    }
    public String getSettingColor(Context context){ //채팅방 색상 호출
        return getSharedPreferences(context).getString("setting_color", "#ffffff"); //색상 반환(Default #FFFFFF)
    }

    public void setSettingPush(Context context, Boolean push){ //푸시알림 여부 설정
        SharedPreferences.Editor editor = getSharedPreferences(context).edit(); //에디터 생성
        editor.putBoolean("setting_push", push) //에디터에 받아온 값 저장
        .apply(); //값 반영
    }

    public Boolean isSettingPush(Context context){ //푸시알림 여부 호출
        return getSharedPreferences(context).getBoolean("setting_push", true); //푸시알림 여부 반환
    }

    public void setSettingVib(Context context, Boolean vib){ //진동 여부 설정
        SharedPreferences.Editor editor = getSharedPreferences(context).edit(); //에디터 생성
        editor.putBoolean("setting_vib", vib) //에디터에 받아온 값 저장
        .apply(); //값 반영
    }

    public Boolean isSettingVib(Context context){ //진동 여부 호출
        return getSharedPreferences(context).getBoolean("setting_vib", true); //진동 여부 반환
    }


    //유저 정보 반환 함수
    public String getUserNickname(Context context){
        return getSharedPreferences(context).getString("nickname", "");
    }
    public String getUserEmail(Context context){
        return getSharedPreferences(context).getString("email", "");
    }
    public String getUserKey(Context context){
        return getSharedPreferences(context).getString("userkey", "");
    }
    public String getIntro(Context context){
        return getSharedPreferences(context).getString("intro", "");
    }
    public int getProfilenum(Context context){
        return getSharedPreferences(context).getInt("profilenum", 0);
    }
}
