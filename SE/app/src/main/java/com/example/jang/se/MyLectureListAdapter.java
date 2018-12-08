package com.example.jang.se;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyLectureListAdapter extends ArrayAdapter<LectureItem> {

    private int resourceId ;
    private final Context context;
    private final ArrayList<LectureItem> elementos;

    public MyLectureListAdapter(Context context, int resource, ArrayList<LectureItem> elementos) {
        super(context, resource, elementos) ;
        this.resourceId = resource ;
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mylecture_listview, parent, false);


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

        TextView titleItem =  rowView.findViewById(R.id.title);
        TextView lecturerItem =  rowView.findViewById(R.id.lecturer);
        ImageView iconItem = rowView.findViewById(R.id.icon);

        titleItem.setText(elementos.get(position).getTitle());
        lecturerItem.setText(elementos.get(position).getLecturer());
        iconItem.setImageResource(elementos.get(position).getIcon());

        return rowView;
    }

}


