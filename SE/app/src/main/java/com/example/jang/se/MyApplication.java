package com.example.jang.se;

import android.app.Application;

public class MyApplication extends Application {
    private String UserEmail;
    private String UserName;

    public String getUserEmail() {
        return UserEmail;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
