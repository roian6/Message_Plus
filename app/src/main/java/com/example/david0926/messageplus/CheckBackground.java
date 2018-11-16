package com.example.david0926.messageplus;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class CheckBackground {

    //어플리케이션이 백그라운드에 있는지 확인하는 클래스

    public boolean isAppBackground(Context context) { //백그라운드 여부 반환 함수
        boolean appBackground = true; //백그라운드 여부를 저장할 boolean 변수 선언
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); //ActivityManager 가져오기

        // 현재 실행중인 프로세스의 목록을 List로 가져옴
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) { //현재 foreground에서 실행중인 프로세스 검색
                    if (activeProcess.equals(context.getPackageName())) { //해당 앱이 foreground에서 실행중이라면
                        appBackground = false; //백그라운드 여부를 false로 설정
                    }
                }
            }
        }
        return appBackground; //백그라운드 여부 반환
    }

}
