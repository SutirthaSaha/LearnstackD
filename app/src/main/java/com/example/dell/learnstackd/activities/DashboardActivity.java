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

import com.example.dell.learnstackd.R;
import com.example.dell.learnstackd.User;
import com.example.dell.learnstackd.adapters.DashboardAdapter;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayoutDashboard;
    private Toolbar toolbar;
    private RecyclerView dashboardRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String[] nameDataset={"HaaAAAAaSdASDASdasd","HaaAAAAaSdASDASdasd","HaaAAAAaSdASDASdasd","HaaAAAAaSdASDASdasd","HaaAAAAaSdASDASdasd","HaaAAAAaSdASDASdasd","HaaAAAAaSdASDASdasd"};
    private String[] dateDataset={"11/12/13","11/12/13","11/12/13","11/12/13","11/12/13","11/12/13","11/12/13"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        drawerLayoutDashboard=findViewById(R.id.drawerLayoutDashboard);
        dashboardRecyclerView=findViewById(R.id.dashboardRecyclerView);

        layoutManager=new LinearLayoutManager(DashboardActivity.this,LinearLayoutManager.VERTICAL,false);
        dashboardRecyclerView.setLayoutManager(layoutManager);
        adapter=new DashboardAdapter(DashboardActivity.this,nameDataset,dateDataset);
        dashboardRecyclerView.setAdapter(adapter);

        setToolbar();
        setNavigationDrawer();
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
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.dashboardToolbar);
        toolbar.setTitle("Dashboard");
        toolbar.setTitleTextColor(Color.WHITE);
    }
}
