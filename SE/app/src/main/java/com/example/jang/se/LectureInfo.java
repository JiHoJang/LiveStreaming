package com.example.jang.se;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;


public class LectureInfo extends AppCompatActivity {
    ImageView iv_profile;
    TextView tv_lecture_name;
    TextView tv_lecturer_name;
    TextView tv_price;
    TextView tv_num_people;
    TextView tv_info;
    Button btn_subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_info);
        iv_profile = (ImageView)findViewById(R.id.ivProfile);
        tv_lecture_name = (TextView)findViewById(R.id.tvLectureName);
        tv_lecturer_name = (TextView)findViewById(R.id.tvLecturerName);
        tv_price=(TextView)findViewById(R.id.tvPrice);
        tv_num_people=(TextView)findViewById(R.id.tvNumPeople);
        tv_info=(TextView)findViewById(R.id.tvInfo);
        btn_subscribe = (Button)findViewById(R.id.btnSubscribe);

        Intent intent=getIntent();
        String data = intent.getStringExtra(""); // 인텐트에서 전달된 데이터 로드
        String lectureName = intent.getStringExtra("lectureName");
        String lecturerName = intent.getStringExtra("lecturerName");
        int price = intent.getIntExtra("price",0);
        int numPeople = intent.getIntExtra("numPeople",1);
        int nowPeople = intent.getIntExtra("nowPeople", 0);
        String info = intent.getStringExtra("info");
        int icon = intent.getIntExtra("icon", R.drawable.no_profile);

        iv_profile.setImageResource(icon);
        tv_lecture_name.setText(lectureName);
        tv_lecturer_name.setText(lecturerName);
        tv_price.setText(price+"원");
        tv_num_people.setText(nowPeople+"명/"+numPeople+"명");
        tv_info.setText(info);

        //강의 구독 버튼 누르면 lecture과 student DB에 해당 정보 등록되게 하는 php만들어야 할 듯 여기에


    }

}
