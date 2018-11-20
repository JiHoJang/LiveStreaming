package com.example.jang.se;

import android.graphics.drawable.Drawable;

public class LectureItem {
    String title ;
    String lecturer;
    int id;
    int icon;

    public LectureItem(String title, String lecturer, int id, int icon){
        this.title = title;
        this.lecturer = lecturer;
        this.id = id;
        this.icon = icon;
    }

    public String getTitle(){
        return title;
    }

    public String getLecturer(){
        return lecturer;
    }

    public int getId(){
        return id;
    }

    public int getIcon(){
        return icon;
    }

}
