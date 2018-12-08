package com.example.jang.se;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListViewFragment extends Fragment  {
    private static final int DIALOG_REQUEST_CODE = 1234;
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    ListView lv = null;
    FloatingActionButton addButton;
    ArrayAdapter adapter = null;
    ArrayList<LectureItem> elementos = new ArrayList<LectureItem>();
    String myJSON;
    String SeverURL = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/getAllList.php";
    private static final String TAG_RESULTS = "server_response";
    JSONArray jsonitems = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getData(SeverURL);

        lv = rootView.findViewById(R.id.listView);

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
                MyApplication myapp = (MyApplication)getActivity().getApplication();;
                String lectureName = data.getExtras().getString("lectureName");
                String lecturerName = myapp.getUserName();
                String info = data.getExtras().getString("info");
                int price = data.getExtras().getInt("Price");
                int numPeople = data.getExtras().getInt("numPeople");

                Toast.makeText(getActivity(), lectureName+"/"+lecturerName,Toast.LENGTH_LONG).show();
                elementos.clear();
                getData(SeverURL);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
//                LectureItem addItems= new LectureItem(lectureName, lecturerName, numPeople ,  R.drawable.home, price, info);
//                elementos.add(addItems);
            }

        }



    }


    public void showList() {

        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            jsonitems = jsonObj.getJSONArray(TAG_RESULTS);

            Log.i("Getjson", "진입성공");
            for (int i = 0; i < jsonitems.length(); i++) {
                JSONObject c = jsonitems.getJSONObject(i);
                Log.i("Getjson", c.getString("SEND_TITLE"));
                String title = c.getString("SEND_TITLE");
                String instructor = c.getString("SEND_INSTRUCTOR");
                int price = c.getInt("SEND_PRICE");
                int max_student = c.getInt("SEND_MAX_STUDENT");
                int num_student = c.getInt("SEND_NUM_STUDENT");
                String lecture_feature = c.getString("SEND_LECTURE_FEATURE");

                LectureItem custom = new LectureItem(title, instructor, max_student,num_student, R.drawable.home, price, lecture_feature);

                elementos.add(custom);
            }

            adapter = new CustomListAdapter(this.getContext(), elementos);
            lv.setAdapter(adapter);

        } catch (JSONException e) {
            Log.i("Getjson", "진입실패");
            e.printStackTrace();
        }

    }
    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {

                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }


    }
