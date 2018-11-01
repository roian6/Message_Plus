package com.example.david0926.messageplus.Chat;


public class RecycleModel_Chat {
    private String name, msg, time, nickname;
    private int profilenum;

    public RecycleModel_Chat(){}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getProfilenum() {
        return profilenum;
    }

    public void setProfilenum(int profilenum) {
        this.profilenum = profilenum;
    }

    public RecycleModel_Chat(String name, String msg, String time, String nickname){
        this.name=name;
        this.msg=msg;
        this.time=time;
        this.nickname=nickname;
        this.profilenum=profilenum;
    }

    public int getProfileNum() {
        return profilenum;
    }

    public void setProfileNum() {
        this.profilenum = profilenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

