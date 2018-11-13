package com.example.david0926.messageplus.Auth;

public class UserModel {

    //유저 정보를 담을 양식 클래스

    //유저 정보 저장에 필요한 변수 정의
    private String nickname, email, userkey, intro;
    int profilenum;

    public UserModel(){}

    public UserModel(String nickname, String email, String userkey, String intro, int profilenum){ //클래스 양식 지정
        this.nickname = nickname;
        this.email = email;
        this.userkey = userkey;
        this.intro=intro;
        this.profilenum=profilenum;
    }


    //Getter & Setter
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getProfilenum() {
        return profilenum;
    }

    public void setProfilenum(int profilenum) {
        this.profilenum = profilenum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }






}
