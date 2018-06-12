package com.learnstack.dell.learnstackd.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.learnstack.dell.learnstackd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseContentAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String,List<String>> contentDetails;

    public CourseContentAdapter(Context context, HashMap<String, List<String>> contentDetails, ArrayList<String> contentTopicDataSet) {
        this.context = context;
        this.contentDetails = contentDetails;
        this.contentTopicDataSet = contentTopicDataSet;
    }

    private ArrayList<String> contentTopicDataSet = new ArrayList<String>() {};

    @Override
    public int getGroupCount() {
        return contentDetails.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return contentDetails.get(contentTopicDataSet.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return contentTopicDataSet.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return contentDetails.get(contentTopicDataSet.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String topic= (String) getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.course_topic_name,parent,false);
        }
        TextView topicTextView=convertView.findViewById(R.id.topicTextView);
        topicTextView.setText(topic);
        topicTextView.setTypeface(null, Typeface.BOLD);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String subTopic= (String) getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.course_subtopic_name,parent,false);
        }
        TextView subTopicTextView=convertView.findViewById(R.id.subTopicTextView);
        subTopicTextView.setText(subTopic);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
