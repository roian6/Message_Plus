package com.example.david0926.templete.Chat;


public class RecycleModel_ChatPage {
    private String name, msg, time;
//    private int profileId;
//
//    public int getProfileId() {
//        return profileId;
//    }
//
//    public void setProfileId(int profileId) {
//        this.profileId = profileId;
//    }


    public RecycleModel_ChatPage(){}

    public RecycleModel_ChatPage(String name, String msg, String time){
        this.name=name;
        this.msg=msg;
        this.time=time;

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
