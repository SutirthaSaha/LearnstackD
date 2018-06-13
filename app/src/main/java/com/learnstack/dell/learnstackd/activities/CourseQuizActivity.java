package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.learnstack.dell.learnstackd.R;

public class CourseQuizActivity extends AppCompatActivity {

    private String quiz_name;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_quiz);

        Intent intent=getIntent();
        quiz_name=intent.getStringExtra("quiz_name");
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setToolbar() {
        toolbar=findViewById(R.id.quizToolbar);
        toolbar.setTitle(quiz_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }
}
