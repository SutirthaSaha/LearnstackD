package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.User;
import com.learnstack.dell.learnstackd.adapters.ViewPagerAdapter;
import com.learnstack.dell.learnstackd.fragments.CompletedCourseFragment;
import com.learnstack.dell.learnstackd.fragments.CompletedProjectFragment;
import com.learnstack.dell.learnstackd.fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    private DrawerLayout drawerLayoutProfile;
    private Toolbar toolbar;
    private TextView headerTitle;
    private TabLayout tabLayoutProfile;
    private ViewPager viewPagerProfile;
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayoutProfile=findViewById(R.id.drawerLayoutProfile);
        tabLayoutProfile=findViewById(R.id.tabLayoutProfile);
        viewPagerProfile=findViewById(R.id.viewPagerProfile);
        setToolbar();
        setNavigationDrawer();
        setViewPager();
    }

    private void setViewPager() {
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ProfileFragment(),"Profile");
        viewPagerAdapter.addFragments(new CompletedCourseFragment(),"Courses");
        viewPagerAdapter.addFragments(new CompletedProjectFragment(),"Projects");
        viewPagerProfile.setAdapter(viewPagerAdapter);
        tabLayoutProfile.setupWithViewPager(viewPagerProfile);
    }

    private void setNavigationDrawer() {
        final NavigationView navigationView;
        navigationView = findViewById(R.id.navigationViewProfile);
        User user=new User(ProfileActivity.this);
        View header=navigationView. getHeaderView(0);
        headerTitle=header.findViewById(R.id.headerTitle);
        headerTitle.setText(user.getFname());
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(ProfileActivity.this,drawerLayoutProfile,toolbar,R.string.drawer_open,R.string.drawer_close);

        drawerLayoutProfile.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(ProfileActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                Intent intent;
                if (item.getTitle().equals("Featured")){
                    intent=new Intent(ProfileActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Dashboard")){
                    intent=new Intent(ProfileActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Profile")){
                    intent=new Intent(ProfileActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
                if(item.getTitle().equals("Sign Out")){
                    User user=new User(ProfileActivity.this);
                    user.logOut();
                    intent=new Intent(ProfileActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Feedback")){
                    intent=new Intent(ProfileActivity.this,FeedBackActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Invite and Earn")){
                    intent=new Intent(ProfileActivity.this,InviteAndEarnActivity.class);
                    startActivity(intent);
                }
                drawerLayoutProfile.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.profileToolbar);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(Color.WHITE);
    }
}
