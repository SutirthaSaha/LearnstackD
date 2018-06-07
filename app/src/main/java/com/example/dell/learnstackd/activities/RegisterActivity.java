package com.example.dell.learnstackd.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.dell.learnstackd.MySingleton;
import com.example.dell.learnstackd.R;
import com.example.dell.learnstackd.adapters.CourseScrollAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText fNameRegister,lNameRegister,emailRegister,passwordRegister;

    private Button registerBtn;

    private String urlCollege="http://nfly.in/gapi/load_all_rows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fNameRegister=findViewById(R.id.fNameRegister);
        lNameRegister=findViewById(R.id.lNameRegister);
        emailRegister=findViewById(R.id.emailRegister);
        passwordRegister=findViewById(R.id.passwordRegister);
        
        registerBtn=findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,OnBoardRegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    }