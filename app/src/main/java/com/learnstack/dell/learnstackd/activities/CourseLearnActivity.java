package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.learnstack.dell.learnstackd.MySingleton;
import com.learnstack.dell.learnstackd.PlayerConfig;
import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.adapters.CourseContentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseLearnActivity extends YouTubeBaseActivity {

    private YouTubePlayerView courseLearnVideo;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private ExpandableListView courseLearnExpandableListView;

    HashMap<String,List<String>> contentDetails=new HashMap<String, List<String>>(){};
    HashMap<String,String> topicUrl=new HashMap<String,String>(){};

    private String urlSubTopic = "http://nfly.in/gapi/load_all_rows";
    private String urlContent = "http://nfly.in/gapi/load_rows_one";
    
    private ArrayList<String> contentTopicDataSet = new ArrayList<String>() {
    };
    private ArrayList<ArrayList<String>> contentSubTopicDataSet=new ArrayList<ArrayList<String>>(){};
    private ArrayList<String> subTopicDataSet = new ArrayList<String>() {
    };
    private ArrayList<String> urlDataSet=new ArrayList<String>(){};

    private ArrayList<String> moduleNumTopicDataSet = new ArrayList<String>() {
    };
    private ArrayList<String> moduleNumSubTopicDataSet=new ArrayList<String>(){};
    
    private String  course_id,course_name,subTopicSelected;
    private CourseContentAdapter courseContentAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_learn);

        courseLearnVideo=findViewById(R.id.courseLearnVideo);
        courseLearnExpandableListView=findViewById(R.id.courseLearnExpandableListView);

        Intent intent = getIntent();
        course_name = intent.getStringExtra("course_name");
        course_id = intent.getStringExtra("course_id");
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        initializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("eOsBgDd57DE");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        setContentValues();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_course_learn,menu);
        return true;
    }
*/

    private void setToolbar() {
        toolbar=findViewById(R.id.courseLearnToolbar);
        toolbar.setTitle(course_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_course_learn);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.resources:
                        Toast.makeText(CourseLearnActivity.this, "Resources", Toast.LENGTH_SHORT).show();
                        intent=new Intent(CourseLearnActivity.this,CourseResourcesActivity.class);
                        intent.putExtra("course_id",course_id);
                        intent.putExtra("course_name",course_name);
                        startActivity(intent);
                        break;
                    case R.id.about:
                        Toast.makeText(CourseLearnActivity.this,"About",Toast.LENGTH_SHORT).show();
                        intent=new Intent(CourseLearnActivity.this,CourseDetailActivity.class);
                        intent.putExtra("course_id",course_id);
                        intent.putExtra("course_name",course_name);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }

    @Override
    protected void onStart() {
        super.onStart();
        courseLearnVideo.initialize(PlayerConfig.API_KEY,initializedListener);
    }

    private void setContentValues() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlContent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(CourseLearnActivity.this, "Content Hit Successful", Toast.LENGTH_SHORT).show();
                    JSONObject arrayObject;
                    JSONArray parentArray = new JSONArray(response);

                    for (int i = 0; i < parentArray.length(); i++) {
                        arrayObject = parentArray.getJSONObject(i);
                        contentTopicDataSet.add(arrayObject.getString("module_name"));
                        moduleNumTopicDataSet.add(arrayObject.getString("module_id"));
                    }
                    //Toast.makeText(CourseLearnActivity.this, contentTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(CourseLearnActivity.this, moduleNumTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                    setSubtopicValues();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseLearnActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(CourseLearnActivity.this).addToRequestQueue(stringRequest);
    }

    private void setSubtopicValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlSubTopic, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(CourseLearnActivity.this, response, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(CourseLearnActivity.this, subTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(CourseLearnActivity.this, moduleNumSubTopicDataSet.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CourseLearnActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(CourseLearnActivity.this).addToRequestQueue(stringRequest);
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
        courseContentAdapter=new CourseContentAdapter(CourseLearnActivity.this,contentDetails,contentTopicDataSet);
        if(contentDetails!=null)
            courseLearnExpandableListView.setAdapter(courseContentAdapter);
        courseLearnExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                subTopicSelected=contentDetails.get(contentTopicDataSet.get(groupPosition)).get(childPosition);
                Toast.makeText(CourseLearnActivity.this, contentDetails.get(contentTopicDataSet.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
