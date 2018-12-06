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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class LectureInfo extends AppCompatActivity {
    ImageView iv_profile;
    TextView tv_lecture_name;
    TextView tv_lecturer_name;
    TextView tv_price;
    TextView tv_num_people;
    TextView tv_info;
    String SeverURL = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/Subscribe.php";
    Button btn_subscribe;
    com.android.volley.RequestQueue requestQueue;
    ProgressDialog progressDialog;
    boolean CheckEditText;

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

        Intent intent = getIntent();
        final String studentEmail = ((MyApplication) LectureInfo.this.getApplication()).getUserEmail();
        final String lectureName = intent.getStringExtra("lectureName");
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

        requestQueue = Volley.newRequestQueue(LectureInfo.this);
        progressDialog = new ProgressDialog(LectureInfo.this);

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Subscription request complete");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, SeverURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String SeverResponse) {
                                progressDialog.dismiss();

                                Toast.makeText(LectureInfo.this, SeverResponse, Toast.LENGTH_LONG).show();
                                if (SeverResponse.equals("Success"))
                                    finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                                Toast.makeText(LectureInfo.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("lectureName", lectureName);
                        params.put("studentEmail", studentEmail);

                        return params;
                    }

                };

                com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(LectureInfo.this);

                requestQueue.add(stringRequest);
            }
        });

        //강의 구독 버튼 누르면 lecture과 student DB에 해당 정보 등록되게 하는 php만들어야 할 듯 여기에
        //어 그래 알겠다

    }

}
