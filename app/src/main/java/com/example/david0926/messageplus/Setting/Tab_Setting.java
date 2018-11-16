package com.example.david0926.messageplus.Setting;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.david0926.messageplus.R;


public class Tab_Setting extends Fragment{

    //설정 탭 Fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_setting, container, false); //설정 탭의 레이아웃 뷰 반환
    }



}
