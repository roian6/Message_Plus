package com.example.david0926.messageplus.Chat;


public class RecycleModel_ChatPage {

    //채팅 화면의 아이템 정보를 담을 양식 클래스

    //정보를 저장할 변수 선언
    private String name, uid, to, msg, time, date, nickname, nicknameto;
    private int profilenum;


    public RecycleModel_ChatPage(String name, String uid, String to, String msg, String time, String date, String nickname, String nicknameto, int profilenum){ //클래스 양식 지정
        this.name=name;
        this.uid=uid;
        this.to=to;
        this.msg=msg;
        this.time=time;
        this.date=date;
        this.nickname=nickname;
        this.nicknameto=nicknameto;
        this.profilenum=profilenum;
    }

    //Getter & Setter
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

    public String getNicknameto() {
        return nicknameto;
    }

    public void setNicknameto(String nicknameto) {
        this.nicknameto = nicknameto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RecycleModel_ChatPage(){}

    public int getProfilenum() {
        return profilenum;
    }

    public void setProfilenum(int profilenum) {
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
