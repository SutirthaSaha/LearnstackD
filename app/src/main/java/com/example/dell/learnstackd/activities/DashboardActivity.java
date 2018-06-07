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

import java.util.ArrayList;
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

    private User user;
    private String urlDashBoard="http://nfly.in/gapi/load_rows_one";
    private String user_id;
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
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        nameDataSet.add(arrayObject.getString("course_id"));
                        dateDataSet.add(arrayObject.getString("start_date"));
                        progressDataSet.add(arrayObject.getString("progress"));
                    }
                    adapter=new DashboardAdapter(nameDataSet,dateDataSet,progressDataSet,DashboardActivity.this);
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
                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table", "ls_enrollment");
                return params;
            }
        };
        MySingleton.getmInstance(DashboardActivity.this).addToRequestQueue(stringRequest);
    }
}
