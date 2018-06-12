package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.learnstack.dell.learnstackd.R;

public class CourseResourcesActivity extends AppCompatActivity {

    Intent intent;
    private String course_name,course_id;
    private Toolbar toolbar;
    private ExpandableListView courseLearnExpandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_resources);

        intent=getIntent();
        course_id=intent.getStringExtra("course_id");
        course_name=intent.getStringExtra("course_name");
        toolbar=findViewById(R.id.courseResourcesToolbar);
        courseLearnExpandableListView=findViewById(R.id.courseLearnExpandableListView);
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.courseResourcesToolbar);
        toolbar.setTitle(course_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }
}
