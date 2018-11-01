package com.example.david0926.messageplus.Chat;


public class RecycleModel_ChatPage {
    private String name, uid, to, msg, time, nickname, nicknameto;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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


    public RecycleModel_ChatPage(){}

    public String getNicknameto() {
        return nicknameto;
    }

    public void setNicknameto(String nicknameto) {
        this.nicknameto = nicknameto;
    }

    public RecycleModel_ChatPage(String name, String uid, String to, String msg, String time, String nickname, String nicknameto){
        this.name=name;
        this.uid=uid;
        this.to=to;
        this.msg=msg;
        this.time=time;
        this.nickname=nickname;
        this.nicknameto=nicknameto;

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
