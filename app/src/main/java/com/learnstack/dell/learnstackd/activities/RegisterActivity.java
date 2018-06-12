package com.learnstack.dell.learnstackd.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.learnstack.dell.learnstackd.MySingleton;
import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.adapters.CourseScrollAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText fNameRegister,lNameRegister,emailRegister,passwordRegister,passwordConfirmRegister;
    private TextInputLayout fNameWrapper,lNameWrapper,emailRegisterWrapper,passWordRegisterWrapper;

    private Button registerBtn;

    private String urlCollege="http://nfly.in/gapi/load_all_rows";
    private String urlCheck="http://nfly.in/gapi/data_exists_one";
    private String urlRegister="http://nfly.in/gapi/insert";

    private String fname,lname,email,password,passwordConfirm;

    private String date;
    private String[] regData={};

    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fNameRegister=findViewById(R.id.fNameRegister);
        lNameRegister=findViewById(R.id.lNameRegister);
        emailRegister=findViewById(R.id.emailRegister);
        passwordRegister=findViewById(R.id.passwordRegister);
        passwordConfirmRegister=findViewById(R.id.passwordConfirmRegister);

        fNameWrapper=findViewById(R.id.fNameWrappper);
        lNameWrapper=findViewById(R.id.lNameWrapper);
        emailRegisterWrapper=findViewById(R.id.emailRegisterWrapper);
        passWordRegisterWrapper=findViewById(R.id.passWordRegisterWrapper);

        fNameWrapper.setHint("First Name");
        lNameWrapper.setHint("Last Name");
        emailRegisterWrapper.setHint("Email");
        passWordRegisterWrapper.setHint("Password");


        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        registerBtn=findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues();
            }
        });
    }

    private void setValues() {
        fname=fNameRegister.getText().toString().trim();
        lname=lNameRegister.getText().toString().trim();
        email=emailRegister.getText().toString().trim();
        password=passwordRegister.getText().toString().trim();
        passwordConfirm=passwordConfirmRegister.getText().toString().trim();

        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            String[] reg1Data = {fname, lname, email, password, date};
            regData = reg1Data;
            if(password.equals(passwordConfirm)) {
                password = md5(passwordRegister.getText().toString().trim());
                fname = fname.substring(0, 1).toUpperCase() + fname.substring(1).toLowerCase();
                lname = lname.substring(0, 1).toUpperCase() + lname.substring(1).toLowerCase();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCheck, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getInt("status");
                            if (status == 200) {
                            } else {
                                registerUser();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        registerUser();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("key", "email");
                        params.put("value", email);
                        params.put("table", "user");
                        return params;
                    }
                };
                MySingleton.getmInstance(RegisterActivity.this).addToRequestQueue(stringRequest);
            }
            else{
                    Toast.makeText(this, "The passwords don't match", Toast.LENGTH_SHORT).show();
                    }
        }
        else {
            Toast.makeText(this, "Enter the Values Properly", Toast.LENGTH_SHORT).show();
        }

    }

    private void registerUser() {
        Toast.makeText(this, "Am Here", Toast.LENGTH_SHORT).show();
        for(int i=0;i<regData.length;i++){
            Toast.makeText(this, regData[i], Toast.LENGTH_SHORT).show();

        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences=getSharedPreferences("Login Details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("email",email);
                        editor.putString("fname",fname);
                        editor.apply();

                        Intent intent=new Intent(RegisterActivity.this,OnBoardRegisterActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[fname]", fname);
                params.put("insert_array[lname]",lname);
                params.put("insert_array[email]",email);
                params.put("insert_array[password]",password);
                params.put("insert_array[reg_time]",date);
                params.put("table","user");
                return params;
            }
        };
        MySingleton.getmInstance(RegisterActivity.this).addToRequestQueue(stringRequest);
    }

    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }


}