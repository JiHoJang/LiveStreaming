package com.example.jang.se;

import android.graphics.drawable.Drawable;

public class LectureItem {
    String title ;
    String lecturer;
    String info;
    String stream_key;
    int id;
    int icon;
    int num_people;
    int now_people;
    int price;
    int time;

    public LectureItem(String title, String lecturer, int num_people,int now_people, int icon, int price, String info){
        this.title = title;
        this.lecturer = lecturer;
        this.num_people = num_people;
        this.icon = icon;
        this.price = price;
        this.info = info;

    }

    public String getTitle(){
        return title;
    }

    public String getLecturer(){
        return lecturer;
    }

    public String getInfo(){
        return info;
    }

    public String getStreamKey(){return stream_key; }

    public void setStreamKey(String stream_key){
        this.stream_key = stream_key;
    }

    public int getId(){
        return id;
    }

    public int getIcon(){
        return icon;
    }

    public int getNum_people() {return num_people;  }

    public int getNow_people() {return now_people;  }

    public int getPrice() {return price;  }

    public int getTime() {return time;  }




}
