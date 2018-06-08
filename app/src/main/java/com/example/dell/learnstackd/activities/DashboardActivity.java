package com.example.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dell.learnstackd.MySingleton;
import com.example.dell.learnstackd.R;
import com.example.dell.learnstackd.User;
import com.example.dell.learnstackd.adapters.CourseScrollAdapter;
import com.example.dell.learnstackd.adapters.DashboardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayoutDashboard;
    private Toolbar toolbar;
    private RecyclerView dashboardRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> nameDataSet=new ArrayList<String>(){};
    private ArrayList<String> dateDataSet=new ArrayList<String>(){};
    private ArrayList<String> progressDataSet=new ArrayList<String>(){};
    private ArrayList<String> courseIdDataSet=new ArrayList<String>(){};

    private User user;
    private String urlDashBoard="http://nfly.in/testapi/load_rows_on_join_22";
    private String user_id;
    private TextView headerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        drawerLayoutDashboard=findViewById(R.id.drawerLayoutDashboard);
        dashboardRecyclerView=findViewById(R.id.dashboardRecyclerView);

        layoutManager=new LinearLayoutManager(DashboardActivity.this,LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);

        user=new User(DashboardActivity.this);
        user_id=user.getUser_id();
        setToolbar();
        setNavigationDrawer();
        setValues();
    }

    private void setNavigationDrawer() {
        final NavigationView navigationView;
        navigationView = findViewById(R.id.navigationViewDashboard);

        User user=new User(DashboardActivity.this);
        View header=navigationView. getHeaderView(0);
        headerTitle=header.findViewById(R.id.headerTitle);
        headerTitle.setText(user.getFname());
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(DashboardActivity.this,drawerLayoutDashboard,toolbar,R.string.drawer_open,R.string.drawer_close);

        drawerLayoutDashboard.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(DashboardActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                Intent intent;
                if (item.getTitle().equals("Featured")){
                    intent=new Intent(DashboardActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Dashboard")){
                    intent=new Intent(DashboardActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Profile")){
                    intent=new Intent(DashboardActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
                if(item.getTitle().equals("Sign Out")){
                    User user=new User(DashboardActivity.this);
                    user.logOut();
                    intent=new Intent(DashboardActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                drawerLayoutDashboard.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.dashboardToolbar);
        toolbar.setTitle("Dashboard");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void setValues(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlDashBoard, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DashboardActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                String inputDateStr,outputDataStr = null;
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
                Date date;
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        nameDataSet.add(arrayObject.getString("name"));
                        courseIdDataSet.add(arrayObject.getString("course_id"));
                        inputDateStr=arrayObject.getString("start_date");
                        try {
                            date = inputFormat.parse(inputDateStr);
                            outputDataStr=outputFormat.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dateDataSet.add(outputDataStr);
                        progressDataSet.add(arrayObject.getString("progress"));
                    }
                    adapter=new DashboardAdapter(nameDataSet,dateDataSet,progressDataSet,courseIdDataSet,DashboardActivity.this);
                    dashboardRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("table1", "ls_enrollment");
                params.put("table2", "ls_coursev1");
                params.put("join_key_table1", "course_id");
                params.put("join_key_table2", "course_id");
                params.put("key1", "user_id");
                params.put("value1", user_id);
                params.put("key2", "progress<");
                params.put("value2", Integer.toString(100));
                return params;
            }
        };
        MySingleton.getmInstance(DashboardActivity.this).addToRequestQueue(stringRequest);
    }
}
