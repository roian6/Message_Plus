package com.example.david0926.messageplus.Auth;

public class UserModel {

    private String nickname, email, userkey, intro;
    int profilenum;


    public UserModel(String nickname, String email, String userkey, String intro, int profilenum){
        this.nickname = nickname;
        this.email = email;
        this.userkey = userkey;
        this.intro=intro;
        this.profilenum=profilenum;
    }

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



    public UserModel(){}

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
