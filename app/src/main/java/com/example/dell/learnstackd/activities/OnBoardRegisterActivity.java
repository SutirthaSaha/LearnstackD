package com.example.dell.learnstackd.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dell.learnstackd.MySingleton;
import com.example.dell.learnstackd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OnBoardRegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView collegeRegister;
    private Spinner courseRegister,branchRegister,passingYearRegister;

    private String urlCollege="http://nfly.in/gapi/load_all_rows";
    
    private ArrayList<String> collegeList=new ArrayList<String>(){};
    private String[] courseList={"Select Course","Bachelor of Engineering/Technology","Master of Engineering/Technology","Bachelor in Computer Applications","Master in Computer Applications"};
    private String[] branchList={"Select Branch","Computer Science and Engineering","Information Technology","Electronics & Communication Engineering","Electrical & Electronics Engineering","Applied Electronics & Instrumentation","Mechanical Engineering","Civil Engineering","Other"};
    private String[] passingYearList={"Select Passing Year","2013","2014","2015","2016","2017","2017","2018","2019","2020","2021","2022"};

    private ArrayAdapter<String> courseAdapter,branchAdapter,passingYearAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_register);

        collegeRegister=findViewById(R.id.collegeRegister);
        courseRegister=findViewById(R.id.courseRegister);
        branchRegister=findViewById(R.id.branchRegister);
        passingYearRegister=findViewById(R.id.passingYearRegsiter);

        courseAdapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,android.R.layout.simple_spinner_item,courseList);
        courseRegister.setAdapter(courseAdapter);


        branchAdapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,android.R.layout.simple_spinner_item,branchList);
        branchRegister.setAdapter(branchAdapter);


        passingYearAdapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,android.R.layout.simple_spinner_item,passingYearList);
        passingYearRegister.setAdapter(passingYearAdapter);

        setValues();
    }
    private void setValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlCollege, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(OnBoardRegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    Toast.makeText(OnBoardRegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        collegeList.add(arrayObject.getString("college_name"));
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,android.R.layout.simple_dropdown_item_1line,collegeList.toArray(new String[collegeList.size()]));
                    collegeRegister.setThreshold(2);
                    collegeRegister.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "college_id");
                params.put("order", "ASC");
                params.put("table", "college_name");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }
}
