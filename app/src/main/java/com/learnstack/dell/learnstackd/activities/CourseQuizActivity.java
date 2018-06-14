package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.learnstack.dell.learnstackd.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseQuizActivity extends AppCompatActivity {

    private String quiz_name,course_id,course_name;
    private Toolbar toolbar;
    private HashMap<String,HashMap<String,String>> quizDetails=new HashMap<>();
    private HashMap<String,ArrayList<String>> questionOptionsSet=new HashMap<>();
    private HashMap<String,String> optionDetails=new HashMap<>();
    private ArrayList<String> optionsDataSet=new ArrayList<String>();
    private ArrayList<String> questionDataSet=new ArrayList<String>(){};
    private ArrayList<String> answerDataSet=new ArrayList<String>(){};
    private TextView courseQuizQuestion;
    private RadioButton courseQuizOption1,courseQuizOption2,courseQuizOption3,courseQuizOption4;
    private RadioGroup courseQuizOptions;
    private Button courseQuizSubmitBtn;
    private int count=0;

    private int optionIdSelected;
    private RadioButton optionSelected;
    private String userAnswer;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_quiz);

        Intent intent=getIntent();

        quiz_name=intent.getStringExtra("quiz_name");
        course_id=intent.getStringExtra("course_id");
        course_name=intent.getStringExtra("course_name");

        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        courseQuizQuestion=findViewById(R.id.courseQuizQuestion);
        courseQuizOption1=findViewById(R.id.courseQuizOption1);
        courseQuizOption2=findViewById(R.id.courseQuizOption2);
        courseQuizOption3=findViewById(R.id.courseQuizOption3);
        courseQuizOption4=findViewById(R.id.courseQuizOption4);
        courseQuizOptions=findViewById(R.id.courseQuizOptions);
        courseQuizSubmitBtn=findViewById(R.id.courseQuizSubmitBtn);

        for(int i=0;i<5;i++){
            questionDataSet.add("Question "+(i+1));
            answerDataSet.add(Integer.toString(1));
        }

        for(int i=0;i<5;i++){
            optionDetails=new HashMap<>();
            optionsDataSet=new ArrayList<String>();
            for(int j=1;j<=4;j++){
                optionDetails.put("Option "+j,Integer.toString(j));
                optionsDataSet.add("Option "+j);
            }
            quizDetails.put(questionDataSet.get(i),optionDetails);
            questionOptionsSet.put(questionDataSet.get(i),optionsDataSet);
        }
        courseQuizQuestion.setText(questionDataSet.get(0));
        courseQuizOption1.setText(questionOptionsSet.get(questionDataSet.get(0)).get(0));
        courseQuizOption2.setText(questionOptionsSet.get(questionDataSet.get(0)).get(1));
        courseQuizOption3.setText(questionOptionsSet.get(questionDataSet.get(0)).get(2));
        courseQuizOption4.setText(questionOptionsSet.get(questionDataSet.get(0)).get(3));

    }
    private void setToolbar() {
        toolbar=findViewById(R.id.quizToolbar);
        toolbar.setTitle(quiz_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }

    public void onQuizSubmitBtnClick(View view) {
        if(count<questionDataSet.size()-1){
            optionIdSelected=courseQuizOptions.getCheckedRadioButtonId();
            optionSelected=findViewById(optionIdSelected);
            userAnswer=quizDetails.get(courseQuizQuestion.getText().toString()).get(optionSelected.getText().toString());
            if(userAnswer!=answerDataSet.get(count)){
                flag=1;
            }
            count++;
            courseQuizOptions.clearCheck();
            if(count==questionDataSet.size()-2){
                courseQuizSubmitBtn.setText("Submit");
            }
            setNextQuestion(count);
        }
        else if(count==questionDataSet.size()-1){
            Intent intent;
            if(flag==1){
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CourseQuizActivity.this);
                alertBuilder.setMessage("\nSahi kar\n");
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.show();
                intent=new Intent(CourseQuizActivity.this,CourseQuizActivity.class);
                intent.putExtra("course_id",course_id);
                intent.putExtra("course_name",course_name);
                intent.putExtra("quiz_name",quiz_name);
                startActivity(intent);
                finish();
            }
            else{
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CourseQuizActivity.this);
                alertBuilder.setMessage("\nMubarak Ho\n");
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.show();
                intent=new Intent(CourseQuizActivity.this,CourseLearnActivity.class);
                intent.putExtra("course_id",course_id);
                intent.putExtra("course_name",course_name);
                startActivity(intent);
                finish();
            }
        }

    }
    private void setNextQuestion(int count) {
        courseQuizQuestion.setText(questionDataSet.get(count));
        courseQuizOption1.setText(questionOptionsSet.get(questionDataSet.get(count)).get(0));
        courseQuizOption2.setText(questionOptionsSet.get(questionDataSet.get(count)).get(1));
        courseQuizOption3.setText(questionOptionsSet.get(questionDataSet.get(count)).get(2));
        courseQuizOption4.setText(questionOptionsSet.get(questionDataSet.get(count)).get(3));
    }
}
