package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.learnstack.dell.learnstackd.MySingleton;
import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.adapters.CourseContentAdapter;
import com.learnstack.dell.learnstackd.adapters.ViewPagerAdapter;
import com.learnstack.dell.learnstackd.fragments.CourseContentFragment;
import com.learnstack.dell.learnstackd.fragments.MoreFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseStudyActivity extends AppCompatActivity implements CourseContentFragment.OnDataPass{

    private TabLayout tabLayoutCourseStudy;
    private ViewPager viewPagerCourseStudy;
    private ViewPagerAdapter viewPagerAdapter;
    //private TextView courseStudyVideo;
    private Toolbar toolbar;
    private String course_id,course_name;

    HashMap<String,List<String>> contentDetails=new HashMap<String, List<String>>(){};
    HashMap<String,String> topicUrl=new HashMap<String,String>(){};

    private String urlSubTopic = "http://nfly.in/gapi/load_all_rows";
    private String urlContent = "http://nfly.in/gapi/load_rows_one";

    private String API_KEY="AIzaSyBUgJXtOtzTai5bpF6CIur8DPqPa-cNjNo";

    public ArrayList<String> contentTopicDataSet = new ArrayList<String>() {
    };
    public ArrayList<ArrayList<String>> contentSubTopicDataSet=new ArrayList<ArrayList<String>>(){};
    public ArrayList<String> subTopicDataSet = new ArrayList<String>() {
    };
    public ArrayList<String> urlDataSet=new ArrayList<String>(){};

    public ArrayList<String> moduleNumTopicDataSet = new ArrayList<String>() {
    };
    public ArrayList<String> moduleNumSubTopicDataSet=new ArrayList<String>(){};

    //private YouTubePlayerView youTubePlayerView;
    //private YouTubePlayer.OnInitializedListener initializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_study);

        Intent intent = getIntent();
        course_name = intent.getStringExtra("course_name");
        course_id = intent.getStringExtra("course_id");
        //courseStudyVideo=findViewById(R.id.courseStudyVideo);

        setToolbar();
        tabLayoutCourseStudy = findViewById(R.id.tabLayoutCourseStudy);
        viewPagerCourseStudy = findViewById(R.id.viewPagerCourseStudy);
        setContentValues();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
        youTubePlayerView = findViewById(R.id.courseStudyVideo);

        initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("eOsBgDd57DE");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };*/

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                if (!wasRestored) {
                    player.loadVideo("eOsBgDd57DE");
                    player.play();
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
    }

    private void setViewPager() {
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        if(contentDetails!=null) {
            viewPagerAdapter.addFragments(CourseContentFragment.newInstance(course_id, contentDetails, contentTopicDataSet), "Course Content");
        }
        viewPagerAdapter.addFragments(new MoreFragment(),"More");
        viewPagerCourseStudy.setAdapter(viewPagerAdapter);
        tabLayoutCourseStudy.setupWithViewPager(viewPagerCourseStudy);
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.courseStudyToolbar);
        toolbar.setTitle(course_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }
    private void setContentValues() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlContent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(CourseStudyActivity.this, "Content Hit Successful", Toast.LENGTH_SHORT).show();

                    JSONObject arrayObject;
                    JSONArray parentArray = new JSONArray(response);

                    for (int i = 0; i < parentArray.length(); i++) {
                        arrayObject = parentArray.getJSONObject(i);
                        contentTopicDataSet.add(arrayObject.getString("module_name"));
                        moduleNumTopicDataSet.add(arrayObject.getString("module_id"));
                    }
                    //Toast.makeText(CourseStudyActivity.this, contentTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(CourseStudyActivity.this, moduleNumTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                    setSubtopicValues();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseStudyActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("value",course_id);
                params.put("table", "ls_modulev1");
                return params;
            }
        };
        MySingleton.getmInstance(CourseStudyActivity.this).addToRequestQueue(stringRequest);
    }

    private void setSubtopicValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlSubTopic, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(CourseStudyActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                    int i;
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for (i = 0; i < parentArray.length(); i++) {
                        arrayObject = parentArray.getJSONObject(i);
                        subTopicDataSet.add(arrayObject.getString("topic_name"));
                        urlDataSet.add(arrayObject.getString("video_url"));
                        moduleNumSubTopicDataSet.add(arrayObject.getString("module_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<moduleNumSubTopicDataSet.size();i++){
                    topicUrl.put(subTopicDataSet.get(i),urlDataSet.get(i));
                }
                setSubTopicValues();
                //Toast.makeText(CourseStudyActivity.this, subTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(CourseStudyActivity.this, moduleNumSubTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseStudyActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("order", "ASC");
                params.put("table", "ls_topicv1");
                return params;
            }
        };
        MySingleton.getmInstance(CourseStudyActivity.this).addToRequestQueue(stringRequest);
    }

    private void setSubTopicValues() {
        int i,j;

        ArrayList<String> subTopic;
        for(i=0;i<moduleNumTopicDataSet.size()-1;i++){
            subTopic=new ArrayList<String>(){};
            for(j=0;j<moduleNumSubTopicDataSet.size()-1;j++){
                if(moduleNumTopicDataSet.get(i+1).equals(moduleNumSubTopicDataSet.get(j+1))){
                    subTopic.add(subTopicDataSet.get(j));
                }
            }
            contentSubTopicDataSet.add(subTopic);
        }

        for(i=0;i<moduleNumTopicDataSet.size()-1;i++){
            contentDetails.put(contentTopicDataSet.get(i),contentSubTopicDataSet.get(i));
        }
        setViewPager();
    }

    @Override
    public void onDataPass(String data) {
        //courseStudyVideo.setText(topicUrl.get(data));
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                if (!wasRestored) {
                    player.loadVideo("eOsBgDd57DE");
                    player.play();
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
    }
}
