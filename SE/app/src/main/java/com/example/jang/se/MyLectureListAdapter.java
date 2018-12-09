package com.example.jang.se;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyLectureListAdapter extends ArrayAdapter<LectureItem> {

    private int resourceId ;
    private final Context context;
    private final ArrayList<LectureItem> elementos;
    private static final int DIALOG_REQUEST_CODE = 1234;

    public MyLectureListAdapter(Context context, int resource, ArrayList<LectureItem> elementos) {
        super(context, resource, elementos) ;
        this.resourceId = resource ;
        this.context = context;
        this.elementos = elementos;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.resourceId, parent, false);


        Button bntVideoPlay = (Button) rowView.findViewById(R.id.bnt_video_play);
        bntVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, Video.class);
                context.startActivity(intent);
            }
        });

        Button bntBoard = (Button)rowView.findViewById(R.id.bnt_board);
        bntBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BoardActivity.class);
                context.startActivity(intent);
            }
        });

        if(this.resourceId==R.layout.mylecture_listview2){
            Button bntAdd = (Button) rowView.findViewById(R.id.bnt_add_evnet);
            bntAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment newFragment = new DialogFragmentAddEvent();
                    newFragment.show(((AppCompatActivity) context).getSupportFragmentManager(),"dialog");
                }
            });

        }

        TextView titleItem =  rowView.findViewById(R.id.title);
        TextView lecturerItem =  rowView.findViewById(R.id.lecturer);
        ImageView iconItem = rowView.findViewById(R.id.icon);

        titleItem.setText(elementos.get(position).getTitle());
        lecturerItem.setText(elementos.get(position).getLecturer());
        iconItem.setImageResource(elementos.get(position).getIcon());

        return rowView;
    }

    void show(){
        DialogFragment newFragment = new DialogFragmentAddLecture();
        newFragment.show(((AppCompatActivity) context).getSupportFragmentManager(),"dialog");
    }

}


