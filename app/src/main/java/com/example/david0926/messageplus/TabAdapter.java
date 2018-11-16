package com.example.david0926.messageplus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.david0926.messageplus.Chat.Tab_Chat;
import com.example.david0926.messageplus.People.Tab_People;
import com.example.david0926.messageplus.Setting.Tab_Setting;

public class TabAdapter extends FragmentPagerAdapter{

    //탭 기능 Adapter

    private int num;

    public TabAdapter(FragmentManager fm, int num) { //탭 어댑터 함수
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int position) { //탭에서 아이템을 반환하는 함수
        switch (position){
            case 0: //0번 포지션이 호출되었을 때
                //People 탭 반환
                Tab_People tab1 = new Tab_People();
                return tab1;

            case 1: //1번 포지션이 호출되었을 때
                //Chat 탭 반환
                Tab_Chat tab2 = new Tab_Chat();
                return tab2;
            case 2: //2번 포지션이 호출되었을 때
                //Setting 탭 반환
                Tab_Setting tab3 = new Tab_Setting();
                return tab3;

            default: return null; //default

        }
    }

    @Override
    public int getCount() { //탭 개수 반환 함수
        return num;
    }
}
