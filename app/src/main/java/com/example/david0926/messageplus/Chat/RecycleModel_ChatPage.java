package com.example.david0926.messageplus.Chat;


public class RecycleModel_ChatPage {
    private String name, uid, to, msg, time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
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

    public RecycleModel_ChatPage(String name, String uid, String to, String msg, String time){
        this.name=name;
        this.uid=uid;
        this.to=to;
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
