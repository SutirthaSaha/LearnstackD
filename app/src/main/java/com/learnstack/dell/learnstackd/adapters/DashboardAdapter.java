package com.learnstack.dell.learnstackd.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.activities.CourseLearnActivity;
import com.learnstack.dell.learnstackd.activities.CourseStudyActivity;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    private ArrayList<String> nameDataset,dateDataSet,progressDataSet,courseIdDataSet;
    private Context context;

    public DashboardAdapter(ArrayList<String> nameDataset, ArrayList<String> dateDataSet, ArrayList<String> progressDataSet, ArrayList<String> courseIdDataSet,Context context) {
        this.nameDataset = nameDataset;
        this.dateDataSet = dateDataSet;
        this.progressDataSet = progressDataSet;
        this.courseIdDataSet=courseIdDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public DashboardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard_layout,parent,false);
        DashboardAdapter.DashboardHolder holder=new DashboardAdapter.DashboardHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardHolder holder, final int position) {
        holder.dashboardCourseName.setText(nameDataset.get(position));
        holder.dashboardStartDate.setText(dateDataSet.get(position));
        holder.dashboardProgressText.setText(progressDataSet.get(position)+"% ");
        holder.dashboardProgressBar.setProgress(Math.round(Float.parseFloat(progressDataSet.get(position))));
        holder.dashboardAssessmentClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Assessment Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.dashboardProjectClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Project Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.dashBoardCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, courseIdDataSet.get(position)+" "+nameDataset.get(position)+" Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, CourseLearnActivity.class);
                intent.putExtra("course_name",nameDataset.get(position));
                intent.putExtra("course_id",courseIdDataSet.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameDataset.size();
    }

    public class DashboardHolder extends RecyclerView.ViewHolder{
        public TextView dashboardCourseName,dashboardStartDate,dashboardAssessmentClickable,dashboardProjectClickable,dashboardProgressText;
        public ProgressBar dashboardProgressBar;
        private CardView dashBoardCard;
        public DashboardHolder(View itemView) {

            super(itemView);

            dashboardCourseName=itemView.findViewById(R.id.dashboardCourseName);
            dashboardStartDate=itemView.findViewById(R.id.dashboardCourseStartDate);
            dashboardAssessmentClickable=itemView.findViewById(R.id.dashboardAssesmentClickable);
            dashboardProgressBar=itemView.findViewById(R.id.dashboardProgressBar);
            dashboardProjectClickable=itemView.findViewById(R.id.dashboardProjectClickable);
            dashboardProgressText=itemView.findViewById(R.id.dashboardProgressText);

            dashBoardCard=itemView.findViewById(R.id.dashBoardCard);
        }
    }
}
