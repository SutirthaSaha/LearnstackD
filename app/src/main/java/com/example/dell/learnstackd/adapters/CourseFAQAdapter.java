package com.example.dell.learnstackd.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.learnstackd.R;

import java.util.ArrayList;

public class CourseFAQAdapter  extends RecyclerView.Adapter<CourseFAQAdapter.CourseFAQHolder>{

    private ArrayList<String> quesDataSet;
    private ArrayList<String> ansDataSet;

    public CourseFAQAdapter(ArrayList<String> quesDataSet, ArrayList<String> ansDataSet) {
        this.quesDataSet = quesDataSet;
        this.ansDataSet = ansDataSet;
    }

    @NonNull
    @Override
    public CourseFAQHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_detail_faq_item_layout,parent,false);
        CourseFAQAdapter.CourseFAQHolder holder=new CourseFAQAdapter.CourseFAQHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseFAQHolder holder, int position) {
        holder.courseDetailFaqQues.setText(quesDataSet.get(position));
        holder.courseDetailFaqAns.setText(ansDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return quesDataSet.size();
    }

    public class CourseFAQHolder extends RecyclerView.ViewHolder{
        public TextView courseDetailFaqQues,courseDetailFaqAns;
        public CourseFAQHolder(View itemView) {
            super(itemView);
            courseDetailFaqQues=itemView.findViewById(R.id.courseDetailFaqQues);
            courseDetailFaqAns=itemView.findViewById(R.id.courseDetailFaqAns);
        }
    }
}
