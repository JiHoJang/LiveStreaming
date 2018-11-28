package com.example.jang.se;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment  {
    private static final int DIALOG_REQUEST_CODE = 1234;
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    ArrayList<LectureItem> custom = null;
    ListView lv = null;
    FloatingActionButton addButton;
    ArrayAdapter adapter = null;
    ArrayList<LectureItem> elementos = new ArrayList<LectureItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        lv = rootView.findViewById(R.id.listView);
        if(custom==null)
            custom = addItems();
        adapter = new CustomListAdapter(this.getContext(), custom);
        lv.setAdapter(adapter);

        addButton = (FloatingActionButton)rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });


        return rootView;

    }


    void show(){
        DialogFragment newFragment = new DialogFragmentAddLecture();
        newFragment.setTargetFragment(this, DIALOG_REQUEST_CODE);
        newFragment.show(getFragmentManager(),"dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                String lectureName = data.getExtras().getString("lectureName");
                String lecturerName = data.getExtras().getString("lecturerName");
                int numPeople = data.getExtras().getInt("numPeople");

                Toast.makeText(getActivity(), lectureName+"/"+lecturerName,Toast.LENGTH_LONG).show();
                LectureItem addItems= new LectureItem(lectureName, lecturerName, numPeople, R.drawable.home);
                elementos.add(addItems);
            }

        }



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
