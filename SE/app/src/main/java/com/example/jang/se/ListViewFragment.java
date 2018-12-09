package com.example.jang.se;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class ListViewFragment extends Fragment  {
    private static final int DIALOG_REQUEST_CODE = 1234;
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;

    ArrayList<LectureItem> custom = null;
    ListView lv = null;
    FloatingActionButton addButton;
    ArrayAdapter adapter = null;
    ArrayList<LectureItem> elementos = new ArrayList<LectureItem>();
    private String URL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        URL = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/Search.php?keyword=";
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        searchBar = (MaterialSearchBar) rootView.findViewById(R.id.searchBar);
        lv = rootView.findViewById(R.id.listView);
        if(custom==null)
            custom = addItems();
        adapter = new CustomListAdapter(this.getContext(), custom);
        lv.setAdapter(adapter);

        addButton = (FloatingActionButton)rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("item", "add 클릭" );
                show();
            }
        });
        lv.setChoiceMode(1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("item", position+"클릭" );
                Intent intent = new Intent(getActivity(), LectureInfo.class);
                intent.putExtra ("lectureName", elementos.get(position).getTitle() );
                intent.putExtra ("lecturerName", elementos.get(position).getLecturer());
                intent.putExtra("price",elementos.get(position).getPrice());
                intent.putExtra("numPeople",elementos.get(position).getNum_people());
                intent.putExtra("nowPeople",elementos.get(position).getNow_people());
                intent.putExtra("info",elementos.get(position).getInfo());
                intent.putExtra("profile",elementos.get(position).getIcon());

                startActivity(intent);
            }
        });

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                lectureRequest(searchBar.getText());
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
                MyApplication myapp = (MyApplication)getActivity().getApplication();
                String lectureName = data.getExtras().getString("lectureName");
                String lecturerName = myapp.getUserName();
                String info = data.getExtras().getString("info");
                int price = data.getExtras().getInt("Price");
                int numPeople = data.getExtras().getInt("numPeople");

                Toast.makeText(getActivity(), lectureName+"/"+lecturerName,Toast.LENGTH_LONG).show();
                LectureItem addItems= new LectureItem(lectureName, lecturerName, numPeople ,  R.drawable.home, price, info);
                elementos.add(addItems);
            }
        }
    }

    private void lectureRequest(final String keyword) {

        final JsonObjectRequest JsonRequest = new JsonObjectRequest(Request.Method.GET, URL + keyword, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray lectureArray = response.getJSONArray("data");
                            //if(custom==null)
                            //  custom = addItems();
                            elementos.clear();

                            for(int i = 0; i < lectureArray.length(); i++) {
                                JSONObject lectureObject = lectureArray.getJSONObject(i);

                                String title = lectureObject.getString("SEND_TITLE");
                                String instructor = lectureObject.getString("SEND_INSTRUCTOR");

                                LectureItem custom = new LectureItem(title, instructor, 30, R.drawable.home, 3, "asdf");
                                elementos.add(custom);
                            }

                            adapter = new CustomListAdapter(getActivity(), elementos);
                            lv.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }
        };

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(JsonRequest);
    }

    private ArrayList<LectureItem> addItems(){
        //LectureItem(String title, String lecturer, int num_people, int icon, int price, String info)
        LectureItem custom = new LectureItem("basic English", "json", 1, R.drawable.home, 10000, "쉽게 배우는 영어");
        elementos.add(custom);
        custom = new LectureItem("English", "Hyeon minji",2, R.drawable.home, 0, "무료로 배우는 영어");
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