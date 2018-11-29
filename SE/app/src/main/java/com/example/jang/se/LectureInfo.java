package com.example.jang.se;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;


public class LectureInfo extends AppCompatActivity {
    TextView textView_lecture_title;
    TextView textView_lecturer_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_info);

        textView_lecture_title = (TextView)findViewById(R.id.lectureInfo_Title);
        textView_lecturer_name = (TextView)findViewById(R.id.lectureInfo_LecturerName);

        Intent intent=getIntent();
        String data = intent.getStringExtra(""); // 인텐트에서 전달된 데이터 로드
        String lectureName = intent.getStringExtra("lectureName");
        String lecturerName = intent.getStringExtra("lecturerName");

        textView_lecture_title.setText(lectureName);
        textView_lecturer_name.setText(lecturerName);

        Toast toast = Toast.makeText(this, data, Toast.LENGTH_SHORT);

        toast.show();


    }

}
