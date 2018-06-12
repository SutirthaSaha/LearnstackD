package com.learnstack.dell.learnstackd.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.learnstack.dell.learnstackd.MySingleton;
import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.activities.CourseDetailActivity;
import com.learnstack.dell.learnstackd.adapters.CourseContentAdapter;
import com.learnstack.dell.learnstackd.adapters.CourseFAQAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseContentFragment extends Fragment {

    String course_id,subTopicSelected;
    HashMap<String,List<String>> contentDetails;
    ExpandableListView courseStudyExpandableListView;

    OnDataPass dataPasser;
    public ArrayList<String> contentTopicDataSet = new ArrayList<String>() {
    };
    private CourseContentAdapter courseContentAdapter;

    public CourseContentFragment() {

        // Required empty public constructor
    }

    public static CourseContentFragment newInstance(String course_id,HashMap<String,List<String>> contentDetails ,ArrayList<String> contentTopicDataSet){
        CourseContentFragment fragment = new CourseContentFragment();
        fragment.course_id=course_id;
        fragment.contentDetails=contentDetails;
        fragment.contentTopicDataSet=contentTopicDataSet;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_course_content, container, false);
        courseStudyExpandableListView=v.findViewById(R.id.courseStudyExpandableListView);
        courseContentAdapter=new CourseContentAdapter(getContext(),contentDetails,contentTopicDataSet);
        if(contentDetails!=null)
        courseStudyExpandableListView.setAdapter(courseContentAdapter);
        courseStudyExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getContext(), contentTopicDataSet.get(groupPosition), Toast.LENGTH_SHORT).show();
            }
        });
        courseStudyExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getContext(), contentTopicDataSet.get(groupPosition), Toast.LENGTH_SHORT).show();
            }
        });

        courseStudyExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                subTopicSelected=contentDetails.get(contentTopicDataSet.get(groupPosition)).get(childPosition);
                dataPasser.onDataPass(subTopicSelected);
                Toast.makeText(getContext(), contentDetails.get(contentTopicDataSet.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return v;
    }

    public interface OnDataPass{
        public void onDataPass(String data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPasser = (OnDataPass) context;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void onDataPass(String data){
        dataPasser.onDataPass(data);
    }
}
