package com.example.jang.se;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    ListView listview1;
    List<Board> board1 = new ArrayList<Board>();

    //@Override
    protected void onCrete(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        listview1 = (ListView) findViewById(R.id.board_listview);
        Myadapter adapter = new Myadapter(this);
        listview1.setAdapter(adapter);
        //등록 레이아웃 생성
        board1.add(new Board("","","",0));

        //데이터베이스 연결 필요
    }
}
