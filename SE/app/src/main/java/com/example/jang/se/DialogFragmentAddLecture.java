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
        final EditText lecturerName = (EditText) view.findViewById(R.id.edittextLecturerName);
        final EditText numPeople = (EditText) view.findViewById(R.id.edittextNumPeople);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strLectureName = lectureName.getText().toString();
                String strLecturerName = lecturerName.getText().toString();
                String strNumPeople = numPeople.getText().toString();

                Intent data = new Intent();
                data.putExtra ("lectureName", strLectureName );
                data.putExtra ("lecturerName", strLecturerName );
                data.putExtra ("numPeople", strNumPeople );

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);

                dismiss();
            }
        });

        return builder.create();
    }
}
