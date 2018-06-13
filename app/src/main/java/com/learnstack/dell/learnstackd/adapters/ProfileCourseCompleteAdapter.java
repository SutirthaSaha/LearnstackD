package com.learnstack.dell.learnstackd.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learnstack.dell.learnstackd.R;

import java.util.ArrayList;

public class ProfileCourseCompleteAdapter extends RecyclerView.Adapter<ProfileCourseCompleteAdapter.ProfileCourseCompleteHolder> {

    private ArrayList<String> nameDataSet;
    private ArrayList<String> dateDataSet;
    private Context context;

    public ProfileCourseCompleteAdapter(ArrayList<String> nameDataSet, ArrayList<String> dateDataSet, Context context) {
        this.nameDataSet = nameDataSet;
        this.dateDataSet = dateDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileCourseCompleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_completed_course_item,parent,false);
        ProfileCourseCompleteAdapter.ProfileCourseCompleteHolder holder=new ProfileCourseCompleteAdapter.ProfileCourseCompleteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileCourseCompleteHolder holder, int position) {
        holder.profileCourseCompleteName.setText(nameDataSet.get(position));
        holder.profileCourseCompleteDate.setText(dateDataSet.get(position));
        holder.profileDownloadCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Project Download Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateDataSet.size();
    }


    public class ProfileCourseCompleteHolder extends RecyclerView.ViewHolder{
        public TextView profileCourseCompleteName,profileCourseCompleteDate,profileDownloadCertificate;
        public ProfileCourseCompleteHolder(View itemView) {
            super(itemView);
            profileCourseCompleteDate=itemView.findViewById(R.id.profileCourseCompleteDate);
            profileCourseCompleteName=itemView.findViewById(R.id.profileCourseCompleteName);
            profileDownloadCertificate=itemView.findViewById(R.id.profileDownloadCertificate);
        }
    }
}
