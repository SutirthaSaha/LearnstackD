package com.example.dell.learnstackd.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.learnstackd.R;
import com.example.dell.learnstackd.activities.CourseDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourseScrollAdapter extends RecyclerView.Adapter<CourseScrollAdapter.CourseHolder>  {
    private ArrayList<String> titleDataSet,imageDataSet,courseIdDataSet;
    private Context context;

    public CourseScrollAdapter(Context context,ArrayList<String> imageDataSet, ArrayList<String> titleDataSet,ArrayList<String> courseIdDataSet) {
        this.titleDataSet = titleDataSet;
        this.imageDataSet = imageDataSet;
        this.courseIdDataSet=courseIdDataSet;
        this.context=context;
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_course_layout,parent,false);
        CourseHolder holder=new CourseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, final int position) {
        holder.course_title.setText(titleDataSet.get(position));
        Picasso.with(context).load(imageDataSet.get(position)).into(holder.course_image);
        /*holder.course_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,CourseDetailActivity.class);
                i.putExtra("course_name",titleDataSet.get(position));
                i.putExtra("course_image",imageDataSet.get(position));
                i.putExtra("position",position);
                i.putExtra("course_id",courseIdDataSet.get(position));
                context.startActivity(i);
            }
        });*/
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,CourseDetailActivity.class);
                i.putExtra("course_name",titleDataSet.get(position));
                i.putExtra("course_image",imageDataSet.get(position));
                i.putExtra("position",position);
                i.putExtra("course_id",courseIdDataSet.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageDataSet.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder{
        public ImageView course_image;
        public TextView course_title;
        //public Button course_btn;
        private CardView cardview;
        public CourseHolder(View itemView) {
            super(itemView);
            course_image=itemView.findViewById(R.id.course_image);
            course_title=itemView.findViewById(R.id.course_title);
            //course_btn=itemView.findViewById(R.id.course_btn);
            cardview = itemView.findViewById(R.id.courseDetailCard);
        }
    }

}
