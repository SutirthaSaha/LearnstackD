package com.example.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dell.learnstackd.R;

public class EnrollmentSuccessActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_success);
        toolbar=findViewById(R.id.enrollToolbar);

        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.paymentToolbar);
        toolbar.setTitle("Subscription");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }

    public void onDashboardBtnClick(View view) {
        Intent intent=new Intent(EnrollmentSuccessActivity.this,DashboardActivity.class);
        startActivity(intent);
    }

    public void onCoursesBtnClick(View view) {
        Intent intent=new Intent(EnrollmentSuccessActivity.this,DashboardActivity.class);
        startActivity(intent);
    }
}
