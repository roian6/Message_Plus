package com.example.david0926.messageplus.Chat;


public class RecycleModel_Chat {
    private String name, msg, time;
    private int profileId;

    public RecycleModel_Chat(){}

    public RecycleModel_Chat(String name, String msg, String time){
        this.name=name;
        this.msg=msg;
        this.time=time;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
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

