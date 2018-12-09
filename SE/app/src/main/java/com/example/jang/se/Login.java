package com.example.jang.se;

import android.app.ProgressDialog;
import android.content.Intent;
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


public class Login extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    Boolean CheckEditText;
    com.android.volley.RequestQueue requestQueue;
    String EmailHolder, PWHolder, NameHolder;
    ProgressDialog progressDialog;
    String SeverURL = "http://ec2-54-180-31-90.ap-northeast-2.compute.amazonaws.com/LogIn.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.Login_email);
        password = (EditText) findViewById(R.id.Login_password);
        loginButton = (Button) findViewById(R.id.login);
        requestQueue = Volley.newRequestQueue(Login.this);
        progressDialog = new ProgressDialog(Login.this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                CheckEditTextIsEmptyOrNot();
                if(!CheckEditText) {
                    Toast.makeText(Login.this, "Please fill in the blank", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Please Wait, We are checking your ID");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, SeverURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String SeverResponse) {
                                progressDialog.dismiss();

                                //Toast.makeText(Login.this, SeverResponse, Toast.LENGTH_LONG).show();

                                if (SeverResponse.equals("Success")) {
                                    int idx = EmailHolder.indexOf("@");
                                    NameHolder = EmailHolder.substring(0, idx);

                                    ((MyApplication) Login.this.getApplication()).setUserEmail(EmailHolder);
                                    ((MyApplication) Login.this.getApplication()).setUserName(NameHolder);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", EmailHolder);
                        params.put("password", PWHolder);

                        return params;
                    }
                };


                com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot() {
        EmailHolder = email.getText().toString();
        PWHolder = password.getText().toString();

        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PWHolder))
            CheckEditText = false;
        else
            CheckEditText = true;
    }

    public void onClick_sign(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
}
