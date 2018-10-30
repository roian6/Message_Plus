package com.example.david0926.messageplus.Auth;

public class UserModel {

    private String nickname, email, userkey;

    public UserModel(String nickname, String email, String userkey){
        this.nickname = nickname;
        this.email = email;
        this.userkey = userkey;

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
