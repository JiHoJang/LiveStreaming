/*
package com.example.jang.se;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class DialogFragmentAddLecture extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_lecture, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.buttonSubmit);
        final EditText lectureName = (EditText) view.findViewById(R.id.edittextLectureName);
        final EditText price = (EditText) view.findViewById(R.id.edittextPrice);
        final EditText numPeople = (EditText) view.findViewById(R.id.edittextNumPeople);
        final EditText info = (EditText) view.findViewById(R.id.edittextInfo);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strLectureName = lectureName.getText().toString();
                String strPrice = price.getText().toString();
                String strNumPeople = numPeople.getText().toString();
                String strInfo = info.getText().toString();

                Intent data = new Intent();
                data.putExtra ("lectureName", strLectureName );
                data.putExtra ("lecturePrice", strPrice);
                data.putExtra ("numPeople", strNumPeople );
                data.putExtra("info", strInfo);

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);

                dismiss();
            }
        });

        return builder.create();
    }
}
*/

package com.example.jang.se;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class DialogFragmentAddEvent extends DialogFragment {

    String ServerURL = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/AddLecture.php";
    com.android.volley.RequestQueue requestQueue;
    ProgressDialog progressDialog;
    int year, month, day, hour, minute;
    View view = null;
    EditText et_date = null;
    EditText et_time =null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_add_event, null);
        builder.setView(view);
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        et_date = view.findViewById(R.id.et_date);
        et_time = view.findViewById(R.id.et_time);
        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();

            }

        });

        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getContext(), timeSetListener, hour, minute, false).show();

            }
        });

        return builder.create();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year, monthOfYear + 1, dayOfMonth);
            et_date.setText(msg);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            String msg = String.format("%d : %d", hourOfDay, minute);
            et_time.setText(msg);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };
}

/*
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        progressDialog = new ProgressDialog(getContext().getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MyApplication myapp = (MyApplication)getActivity().getApplication();;
                final String strLectureName = lectureName.getText().toString();
                final String strPrice = price.getText().toString().trim();
                final String strNumPeople = numPeople.getText().toString().trim();
                final String strInfo = info.getText().toString();
                final String strLecturerName =  myapp.getUserName();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String SeverResponse) {
                                progressDialog.dismiss();

                                if (SeverResponse.equals("Success")) {

                                    Intent data = new Intent();

                                    data.putExtra ("lectureName", strLectureName );
                                    data.putExtra("lecturerName",strLecturerName);
                                    data.putExtra ("lecturePrice", strPrice);
                                    data.putExtra ("numPeople", strNumPeople );
                                    data.putExtra("info", strInfo);

                                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);

                                    dismiss();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext().getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        Log.i("loglog",strLecturerName);
                        params.put("lectureName", strLectureName);
                        params.put("lecturerName", strLecturerName);
                        params.put("lecturePrice", strPrice);
                        params.put("numPeople", strNumPeople);
                        params.put("info", strInfo);
                        return params;
                    }
                };

                com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

                requestQueue.add(stringRequest);
            }
        });

        return builder.create();
    }
    */
