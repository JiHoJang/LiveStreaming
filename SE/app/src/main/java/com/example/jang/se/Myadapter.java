package com.example.jang.se;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Myadapter extends BaseAdapter {
    Context context;
    List<Board> data = new ArrayList<Board>();

    Myadapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position){
        return data.get(position).gettype();
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);

        if (convertView == null) {
            if (viewType == 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_boardread, parent, false);
            } else if (viewType == 1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_boardwrite, parent, false);
            }
        }

        if(viewType == 1) {
            TextView textview1 = (TextView) convertView.findViewById(R.id.title);
            TextView textview2 = (TextView) convertView.findViewById(R.id.date);
            TextView textview3 = (TextView) convertView.findViewById(R.id.content);

            Board board = data.get(position);
            textview1.setText(board.title);
            textview2.setText(board.date);
            textview3.setText(board.content);
        }
        else if(viewType == 0) {
            Button button21 = (Button) convertView.findViewById(R.id.button1);
            final EditText edittext21 = (EditText) convertView.findViewById(R.id.edittext1);
            final EditText edittext22 = (EditText) convertView.findViewById(R.id.edittext2);
            button21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat dateform = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    //DB연동
                    Board board = new Board(edittext21.getText().toString(), dateform.format(date).toString(), edittext22.getText().toString(), 1);
                    //db연동
                    edittext21.setText("");
                    edittext22.setText("");

                    Toast.makeText(context, "질문을 등록하였습니다", Toast.LENGTH_SHORT).show();
                }
            });


        }

        return convertView;
    }

}
