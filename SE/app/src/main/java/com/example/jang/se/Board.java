package com.example.jang.se;

import android.view.View;

class Board {
    String title;
    String date;
    String content;
    int type;

    Board(){}

    Board(String title, String date, String content, int layouttype){
        this.title = title;
        this.date = date;
        this.content = content;
        this.type = layouttype;
    }
    public String gettitle(){
        return title;
    }
    public String getdate(){
        return this.date;
    }
    public String getcontent(){
        return content;
    }
    public int gettype(){
        return type;
    }

    public void settitle(String title){
        this.title = title;
    }
    public void setdate(String date){
        this.date = date;
    }
    public void setcontent(String content){
        this.content = content;
    }
    public void settype(int rayouttype){
        this.type = rayouttype;
    }
}
