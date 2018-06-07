package com.example.dell.learnstackd.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.learnstackd.R;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    private String[] nameDataset,dateDataSet;
    private Context context;

    public DashboardAdapter(Context context,String[] nameDataset, String[] dateDataSet) {
        this.nameDataset = nameDataset;
        this.dateDataSet = dateDataSet;
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
    public void onBindViewHolder(@NonNull DashboardHolder holder, int position) {
        holder.dashboardCourseName.setText(nameDataset[position]);
        holder.dashboardStartDate.setText(dateDataSet[position]);
        holder.dashboardAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Assessment Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.dashboardProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Project Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameDataset.length;
    }

    public class DashboardHolder extends RecyclerView.ViewHolder{
        public TextView dashboardCourseName,dashboardStartDate;
        public Button dashboardAssessmentBtn,dashboardProjectBtn;
        public DashboardHolder(View itemView) {
            super(itemView);
            dashboardCourseName=itemView.findViewById(R.id.dashboardCourseName);
            dashboardStartDate=itemView.findViewById(R.id.dashboardCourseStartDate);
            dashboardAssessmentBtn=itemView.findViewById(R.id.dashboardAssesmentBtn);
            dashboardProjectBtn=itemView.findViewById(R.id.dashboardProjectBtn);
        }
    }
}
