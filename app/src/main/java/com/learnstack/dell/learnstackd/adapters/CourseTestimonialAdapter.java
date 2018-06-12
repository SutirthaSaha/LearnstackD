package com.learnstack.dell.learnstackd.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnstack.dell.learnstackd.R;

public class CourseTestimonialAdapter extends RecyclerView.Adapter<CourseTestimonialAdapter.CourseTestimonialHolder> {


    private String[] titleDataSet;
    
    public CourseTestimonialAdapter(String[] titleDataSet) {
        this.titleDataSet = titleDataSet;
    }

    @NonNull
    @Override
    public CourseTestimonialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_detail_testi_item,parent,false);
        CourseTestimonialAdapter.CourseTestimonialHolder holder=new CourseTestimonialAdapter.CourseTestimonialHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTestimonialHolder holder, int position) {
        holder.courseDetailTestimonial.setText(titleDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return titleDataSet.length;
    }

    public class CourseTestimonialHolder extends RecyclerView.ViewHolder{
        public TextView courseDetailTestimonial;
        public CourseTestimonialHolder(View itemView) {
            super(itemView);
            courseDetailTestimonial=itemView.findViewById(R.id.courseDetailTestimonial);
        }
    }
}
