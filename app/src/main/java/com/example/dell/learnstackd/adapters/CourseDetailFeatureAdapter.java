package com.example.dell.learnstackd.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.learnstackd.R;

import java.util.ArrayList;

public class CourseDetailFeatureAdapter extends RecyclerView.Adapter<CourseDetailFeatureAdapter.CourseDetailHolder>{
    private ArrayList<String> titleDataSet;
    private int[] imageDataSet;

    public CourseDetailFeatureAdapter(ArrayList<String> titleDataSet,int[] imageDataSet) {
        this.titleDataSet = titleDataSet;
        this.imageDataSet=imageDataSet;
    }

    @NonNull
    @Override
    public CourseDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_detail_feature_item,parent,false);
        CourseDetailHolder holder=new CourseDetailHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailHolder holder, int position) {
        holder.courseDetailFeatureTitle.setText(titleDataSet.get(position));
        holder.courseDetailFeatureImage.setImageResource(imageDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return titleDataSet.size();
    }

    public class CourseDetailHolder extends RecyclerView.ViewHolder{
        public TextView courseDetailFeatureTitle;
        public ImageView courseDetailFeatureImage;
        public CourseDetailHolder(View itemView) {
            super(itemView);
            courseDetailFeatureTitle=itemView.findViewById(R.id.courseDetailFeatureTitle);
            courseDetailFeatureImage=itemView.findViewById(R.id.courseDetailFeatureImage);
           }
    }
}
