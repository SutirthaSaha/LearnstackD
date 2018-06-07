package com.example.dell.learnstackd.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dell.learnstackd.User;
import com.example.dell.learnstackd.adapters.CourseDetailFeatureAdapter;
import com.example.dell.learnstackd.adapters.CourseFAQAdapter;
import com.example.dell.learnstackd.adapters.CourseTestimonialAdapter;
import com.example.dell.learnstackd.MySingleton;
import com.example.dell.learnstackd.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CourseDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String courseImage, courseName;
    private int position;
    private int num_rows;
    private ImageView courseDetailImage;
    private ArrayList<String> courseTakeawaysTitleDataset = new ArrayList<String>() {{
        add("Life Time Access");
        add("Placement Guide");
        add("Project Files");
        add("Study Material");
    }};
    private int[] courseTakeawaysImageDataset = {R.drawable.ic_computer_black, R.drawable.ic_computer_black, R.drawable.ic_computer_black, R.drawable.ic_computer_black};

    private ArrayList<String> courseFeatureTitleDataset = new ArrayList<String>() {{
        add("Certified Course");
        add("Placement Assistant");
        add("24*7 Support");
        add("Affordable");
        add("Practical Approach");
        add("Assessment Driven");
    }};
    private int[] courseFeatureImageDataset = {R.drawable.ic_computer_black, R.drawable.ic_computer_black, R.drawable.ic_computer_black, R.drawable.ic_computer_black, R.drawable.ic_computer_black, R.drawable.ic_computer_black};

    private ArrayList<String> quesDataSet = new ArrayList<String>() {
    };
    private ArrayList<String> ansDataSet = new ArrayList<String>() {
    };

    private String urlRegister="http://nfly.in/gapi/insert";
    private String urlCourse = "http://nfly.in/gapi/load_all_rows";
    private String urlContent = "http://nfly.in/gapi/load_rows_one";
    private String userEnroll="http://nfly.in/gapi/data_exists_two";
    private TextView courseDetailOverview, courseDetailLearnings;
    private RecyclerView.LayoutManager layoutManager, featureLayoutManager, testimonialLayoutManager, faqLayoutManager, contentLayoutManager;
    private RecyclerView courseTakeawayRecyclerView, courseFeaturesRecyclerView, courseTestimonialRecyclerView, courseFAQRecyclerView, courseContentRecyclerView;
    private RecyclerView.Adapter adapter, featureAdapter, testimonialAdapter, faqAdapter, contentAdapter;
    private String[] testimonialDataSet, faqDataSet;
    public ArrayList<String> contentTopicDataSet = new ArrayList<String>() {
    };
    public ArrayList<String> contentSubTopicDataSet = new ArrayList<String>() {
    };
    public ArrayList<String> moduleNumDataSet = new ArrayList<String>() {
    };
    private Button enrollBtn;
    private int enrollStatus,status;
    private String user_id,course_id,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent intent = getIntent();
        courseImage = intent.getStringExtra("course_image");
        courseName = intent.getStringExtra("course_name");
        position = intent.getIntExtra("position", 0);
        course_id=intent.getStringExtra("course_id");

        courseDetailImage = findViewById(R.id.courseDetailImage);
        courseDetailOverview = findViewById(R.id.courseDetailOverview);
        courseDetailLearnings = findViewById(R.id.courseDetailLearnings);
        courseTakeawayRecyclerView = findViewById(R.id.courseTakeawayRecyclerView);
        courseFeaturesRecyclerView = findViewById(R.id.courseFeaturesRecyclerView);
        courseTestimonialRecyclerView = findViewById(R.id.courseTestimonialRecyclerView);
        courseFAQRecyclerView = findViewById(R.id.courseFAQRecyclerView);
        courseContentRecyclerView = findViewById(R.id.courseContentRecyclerView);
        enrollBtn=findViewById(R.id.enrollBtn);

        layoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        courseTakeawayRecyclerView.setLayoutManager(layoutManager);
        adapter = new CourseDetailFeatureAdapter(courseTakeawaysTitleDataset, courseTakeawaysImageDataset);
        courseTakeawayRecyclerView.setAdapter(adapter);

        featureLayoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        courseFeaturesRecyclerView.setLayoutManager(featureLayoutManager);
        featureAdapter = new CourseDetailFeatureAdapter(courseFeatureTitleDataset, courseFeatureImageDataset);
        courseFeaturesRecyclerView.setAdapter(featureAdapter);

        testimonialLayoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        courseTestimonialRecyclerView.setLayoutManager(testimonialLayoutManager);

        faqLayoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        courseFAQRecyclerView.setLayoutManager(faqLayoutManager);

        contentLayoutManager = new LinearLayoutManager(CourseDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        courseContentRecyclerView.setLayoutManager(contentLayoutManager);
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        User user=new User(CourseDetailActivity.this);
        user_id=user.getUser_id();
        setEnrollBtnParam();

        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

        Picasso.with(CourseDetailActivity.this).load(courseImage).into(courseDetailImage);
        setValues();
        setContentValues();
    }

    private void setEnrollBtnParam() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,userEnroll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    enrollStatus=jsonObject.getInt("status");
                    if(enrollStatus==200){
                        enrollBtn.setBackgroundColor(getResources().getColor(R.color.colorButtonBackEnrolled));
                        enrollBtn.setTextColor(getResources().getColor(R.color.colorButtonTextEnrolled));
                        enrollBtn.setText("Enrolled");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("key1","user_id");
                params.put("value1",user_id);
                params.put("key2","course_id");
                params.put("value2",course_id);
                params.put("table","ls_enrollment");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
    }
    private void setModuleNo() {
        for(int i=0;i<moduleNumDataSet.size();i++){
            contentSubTopicDataSet.add(setSubtopics(moduleNumDataSet.get(i)));
            Toast.makeText(this, contentTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
        }
        contentAdapter=new CourseFAQAdapter(contentTopicDataSet,contentSubTopicDataSet);
        courseContentRecyclerView.setAdapter(contentAdapter);
    }

    private void setContentValues() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlContent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(CourseDetailActivity.this, "Content Hit Successful", Toast.LENGTH_SHORT).show();

                    JSONObject arrayObject;
                    JSONArray parentArray = new JSONArray(response);

                    for (int i = 0; i < parentArray.length(); i++) {
                        arrayObject = parentArray.getJSONObject(i);
                        contentTopicDataSet.add(arrayObject.getString("module_name"));
                        moduleNumDataSet.add(arrayObject.getString("module_id"));
                    }
                    setModuleNo();
                    Toast.makeText(CourseDetailActivity.this, contentTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(CourseDetailActivity.this, moduleNumDataSet.toString(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "course_id");
                params.put("value", Integer.toString(position+1));
                params.put("table", "ls_modulev1");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
    }

    private String setSubtopics(final String moduleId) {

        final String[] subTopic = {""};
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlContent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CourseDetailActivity.this, "Content Sub Topic Hit Successful", Toast.LENGTH_SHORT).show();
                try {
                    int i;
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for (i = 0; i < parentArray.length(); i++) {
                        arrayObject = parentArray.getJSONObject(i);
                        subTopic[0] = subTopic[0] +"\n\n"+arrayObject.getString("topic_name");
                    }
                    Toast.makeText(CourseDetailActivity.this, subTopic[0], Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("key", "topic_id");
                params.put("value", moduleId);
                params.put("table", "ls_topicv1");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
        return subTopic[0];
    }


    private void setToolbar() {
        toolbar=findViewById(R.id.courseDetailToolbar);
        toolbar.setTitle(courseName);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }

    private void setValues(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlCourse, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int i;
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    arrayObject=parentArray.getJSONObject(position);
                    courseDetailOverview.setText(arrayObject.getString("overview").replace("<br><br>",""));
                    courseDetailLearnings.setText(arrayObject.getString("curriculum").replace("/","\n\n"));

                    testimonialDataSet=arrayObject.getString("testimonials").split("/");
                    testimonialAdapter=new CourseTestimonialAdapter(testimonialDataSet);
                    courseTestimonialRecyclerView.setAdapter(testimonialAdapter);

                    faqDataSet=arrayObject.getString("faqs").split("/");
                    for(i=0;i<faqDataSet.length;i++){
                        if(i%2==0){
                            quesDataSet.add(faqDataSet[i]);
                        }
                        else{
                            ansDataSet.add(faqDataSet[i]);
                        }
                    }

                    faqAdapter=new CourseFAQAdapter(quesDataSet,ansDataSet);
                    courseFAQRecyclerView.setAdapter(faqAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "course_id");
                params.put("order", "ASC");
                params.put("table", "ls_coursev1");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
    }

    public void onEnrollClick(View view) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,userEnroll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status=jsonObject.getInt("status");
                    AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CourseDetailActivity.this);
                    if(status==200 && enrollStatus!=200){
                        //alertBuilder.setMessage("Successfully Enrolled");
                        checkEnrollments();
                    }
                    else if(enrollStatus==200){
                        alertBuilder.setMessage("You are already enrolled");
                    }
                    else{
                        alertBuilder.setMessage("Subscribe First").setCancelable(true).setPositiveButton("Subscribe Now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CourseDetailActivity.this, "Link To Payment Gateway", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CourseDetailActivity.this,PaymentActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    AlertDialog alertDialog=alertBuilder.create();
                    alertDialog.setTitle("For You");
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CourseDetailActivity.this);
                alertBuilder.setMessage("Subscribe First").setCancelable(true).setPositiveButton("Subscribe Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CourseDetailActivity.this, "Link to Payment Gateway", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),PaymentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.setTitle("For You");
                alertDialog.show();
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("key1","user_id");
                params.put("value1",user_id);
                params.put("key2","payment_status");
                params.put("value2","Complete");
                params.put("table","ls_membership");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
    }

    private void checkEnrollments() {
        String userCheckRows="http://nfly.in/gapi/return_num_rows_on_condition";
        StringRequest stringRequest=new StringRequest(Request.Method.POST,userCheckRows, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    num_rows=jsonObject.getInt("num_rows");
                    if(num_rows==2){
                        enrollCourse();
                    }
                    else{
                        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CourseDetailActivity.this);
                        alertBuilder.setMessage("You already have two incomplete courses").setCancelable(true).setPositiveButton("OK",null);
                        AlertDialog alertDialog=alertBuilder.create();
                        alertDialog.setTitle("For You");
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(CourseDetailActivity.this);
                alertBuilder.setMessage("Subscribe First").setCancelable(true).setPositiveButton("Subscribe Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CourseDetailActivity.this, "Link to Payment Gateway", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),PaymentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.setTitle("For You");
                alertDialog.show();
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("key1","user_id");
                params.put("value1",user_id);
                params.put("key2","progress<");
                params.put("value2","100");
                params.put("table","ls_enrollment");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
    }

    private void enrollCourse() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        Toast.makeText(CourseDetailActivity.this, "Enrollment Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CourseDetailActivity.this,EnrollmentSuccessActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(CourseDetailActivity.this, "Enrollment Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[user_id]", user_id);
                params.put("insert_array[course_id]",course_id);
                params.put("insert_array[start_date]",date);
                params.put("insert_array[progress]",Integer.toString(0));
                params.put("table","ls_enrollment");
                return params;
            }
        };
        MySingleton.getmInstance(CourseDetailActivity.this).addToRequestQueue(stringRequest);
    }
}
