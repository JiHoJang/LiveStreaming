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
