package com.example.jang.se;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ListViewFragment extends Fragment {
        ArrayList<LectureItem> custom = null;
        ListView lv = null;
        ArrayAdapter adapter = null;
        ArrayList<LectureItem> elementos = new ArrayList<LectureItem>();

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            lv = rootView.findViewById(R.id.listView);
            custom = addItems();
            adapter = new CustomListAdapter(this.getContext(), custom);
            lv.setAdapter(adapter);

            return rootView;
        }

        private ArrayList<LectureItem> addItems(){
            LectureItem custom = new LectureItem("기초영어", "json", 1, R.drawable.home);
            elementos.add(custom);
            custom = new LectureItem("영어", "현민지",2, R.drawable.home);
            elementos.add(custom);
            return elementos;
        }
/*
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();


            if (id == R.id.action_add){
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                //builder.setTitle("Adicionar novo item"); //THESE ARE NOT WORKING
                //final EditText input = new EditText(this); //THIS SHOULD BE 2 Text Fields
                //builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //NEED TO IMPLEMENT HERE
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }


            return super.onOptionsItemSelected(item);
        }
        */
    }
