package com.example.jang.se;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<LectureItem> {
    private final Context context;
    private final ArrayList<LectureItem> elementos;

    public CustomListAdapter(Context context, ArrayList<LectureItem> elementos){
        super(context, R.layout.custom_listview, elementos);
        this.context = context;
        this.elementos = elementos;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_listview, parent, false);

        TextView titleItem =  rowView.findViewById(R.id.title);
        TextView lecturerItem =  rowView.findViewById(R.id.lecturer);
        ImageView iconItem = rowView.findViewById(R.id.icon);

        titleItem.setText(elementos.get(position).getTitle());
        lecturerItem.setText(elementos.get(position).getLecturer());
        iconItem.setImageResource(elementos.get(position).getIcon());

        return rowView;
    }
}
