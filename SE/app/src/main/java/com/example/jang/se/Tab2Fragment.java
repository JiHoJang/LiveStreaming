package com.example.jang.se;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab2Fragment extends Fragment {

    ArrayList<LectureItem> elementos = new ArrayList<LectureItem>();
    ArrayList<LectureItem> elementos2 = new ArrayList<LectureItem>();
    ArrayList<LectureItem> custom = null;
    ArrayAdapter adapter = null;
    ArrayAdapter adapter2 = null;
    ListView lv = null;
    ListView lv2 = null;
    String URL_for_student = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/getStudentList.php?studentEmail=";
    String URL_for_lecturer = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/getLecturerList.php?lecturerName=";
    MyApplication myapp;
    String strUserEmail;
    String strUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);
        myapp = (MyApplication)getActivity().getApplication();
        strUserEmail =  myapp.getUserEmail();
        strUserName =  myapp.getUserName();
        lectureRequest_for_student(strUserEmail);
        lectureRequest_for_lecturer(strUserName);
        lv = rootView.findViewById(R.id.listView);
        lv2 = rootView.findViewById(R.id.listView2);
        //] Inflate the layout for this fragment
        return rootView;
    }

    private void lectureRequest_for_student(final String keyword) {
        final JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.GET, URL_for_student + keyword, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray lectureArray = response.getJSONArray("data");

                            elementos.clear();

                            for (int i = 0; i < lectureArray.length(); i++) {

                                JSONObject lectureObject = lectureArray.getJSONObject(i);
                                Log.i("aaa",keyword);
                                int SN = lectureObject.getInt("SEND_SN");
                                String title = lectureObject.getString("SEND_TITLE");
                                String instructor = lectureObject.getString("SEND_INSTRUCTOR");
                                int price = lectureObject.getInt("SEND_PRICE");
                                int max_student = lectureObject.getInt("SEND_MAX_STUDENT");
                                int num_student = lectureObject.getInt("SEND_NUM_STUDENT");
                                String lecture_feature = lectureObject.getString("SEND_LECTURE_FEATURE");
                                LectureItem custom = new LectureItem(SN, title, instructor, max_student, num_student, R.drawable.home, price, lecture_feature);
                                elementos.add(custom);
                            }

                            adapter = new MyLectureListAdapter(getActivity(), R.layout.mylecture_listview, elementos);
                            lv.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.i("Getjson2", "실패");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("studentEmail", keyword);

                return params;
            }
        };

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(JsonRequest);

    }
    private void lectureRequest_for_lecturer(final String keyword) {
        final JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.GET, URL_for_lecturer + keyword, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray lectureArray = response.getJSONArray("data");

                            elementos2.clear();

                            for (int i = 0; i < lectureArray.length(); i++) {

                                JSONObject lectureObject = lectureArray.getJSONObject(i);
                                Log.i("aaa",keyword);
                                int SN = lectureObject.getInt("SEND_SN");
                                String title = lectureObject.getString("SEND_TITLE");
                                String instructor = lectureObject.getString("SEND_INSTRUCTOR");
                                int price = lectureObject.getInt("SEND_PRICE");
                                int max_student = lectureObject.getInt("SEND_MAX_STUDENT");
                                int num_student = lectureObject.getInt("SEND_NUM_STUDENT");
                                String lecture_feature = lectureObject.getString("SEND_LECTURE_FEATURE");
                                LectureItem custom = new LectureItem(SN, title, instructor, max_student, num_student, R.drawable.home, price, lecture_feature);
                                elementos2.add(custom);
                            }

                            adapter2 = new MyLectureListAdapter(getActivity(), R.layout.mylecture_listview2, elementos2);
                            lv2.setAdapter(adapter2);

                        } catch (JSONException e) {
                            Log.i("Getjson2", "실패");

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lecturerName", keyword);

                return params;
            }
        };

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(JsonRequest);

    }
}

