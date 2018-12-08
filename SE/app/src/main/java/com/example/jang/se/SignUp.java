package com.example.jang.se;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    String SeverURL = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/LogIn.php";
    EditText name, email, password, chkpassword;
    Button button;
    com.android.volley.RequestQueue requestQueue;
    String NameHolder, EmailHolder, PWHolder;
    ProgressDialog progressDialog;
    boolean CheckEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.Sign_name);
        email= (EditText) findViewById(R.id.Sign_email);
        password = (EditText) findViewById(R.id.Sign_pw);
        chkpassword = (EditText) findViewById(R.id.Sign_chkpw);
        button = (Button) findViewById(R.id.Sign_sign);

        requestQueue = Volley.newRequestQueue(SignUp.this);
        progressDialog = new ProgressDialog(SignUp.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if (!CheckEditText) {
                    Toast.makeText(SignUp.this, "Please fill in the blank", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.getText().toString().equals(chkpassword.getText().toString())) {
                    Toast.makeText(SignUp.this, "Please check your password", Toast.LENGTH_LONG).show();
                    return;
                }
                progressDialog.setMessage("Please Wait, We are Inserting your data on Server");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, SeverURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String SeverResponse) {
                                progressDialog.dismiss();

                                Toast.makeText(SignUp.this, SeverResponse, Toast.LENGTH_LONG).show();
                                if (SeverResponse.equals("Success"))
                                    finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", NameHolder);
                        params.put("email", EmailHolder);
                        params.put("password", PWHolder);

                        return params;
                    }
                };

                com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);

                requestQueue.add(stringRequest);
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot() {
        EmailHolder = email.getText().toString();
        PWHolder = password.getText().toString();
        NameHolder = name.getText().toString().trim();

        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PWHolder) || TextUtils.isEmpty(NameHolder))
            CheckEditText = false;
        else
            CheckEditText = true;
    }
}
