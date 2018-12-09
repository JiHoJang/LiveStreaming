package com.example.jang.se;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class Tab2Fragment extends Fragment {

    ArrayList<LectureItem> elementos = new ArrayList<LectureItem>();
    ArrayList<LectureItem> custom = null;
    ArrayAdapter adapter = null;
    ArrayAdapter adapter2 = null;
    ListView lv = null;
    ListView lv2 = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        if(custom==null)
            custom = addItems();
        adapter = new MyLectureListAdapter(this.getContext(),R.layout.mylecture_listview, custom);
        adapter2 = new MyLectureListAdapter(this.getContext(), R.layout.mylecture_listview2,custom);
        lv = rootView.findViewById(R.id.listView);
        lv2 = rootView.findViewById(R.id.listView2);
        lv.setAdapter(adapter);
        lv2.setAdapter(adapter2);


        //] Inflate the layout for this fragment
        return rootView;
    }

    private ArrayList<LectureItem> addItems(){
        //LectureItem(String title, String lecturer, int num_people, int icon, int price, String info)
        LectureItem custom = new LectureItem(1111,"basic English", "json", 1,0, R.drawable.home, 10000, "쉽게 배우는 영어");
        elementos.add(custom);
        custom = new LectureItem(1111,"English", "Hyeon minji",2,0, R.drawable.home, 0, "무료로 배우는 영어");
        elementos.add(custom);
        return elementos;
    }
}

