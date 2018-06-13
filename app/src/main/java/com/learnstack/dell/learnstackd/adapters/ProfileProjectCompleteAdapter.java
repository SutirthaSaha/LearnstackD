package com.learnstack.dell.learnstackd.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learnstack.dell.learnstackd.R;

import java.util.ArrayList;

public class ProfileProjectCompleteAdapter extends RecyclerView.Adapter<ProfileProjectCompleteAdapter.ProfileProjectCompleteHolder> {

    private ArrayList<String> projectNameDataSet,projectDateDataSet,projectStatusDataSet,projectImageDataSet;
    private Context context;

    public ProfileProjectCompleteAdapter(ArrayList<String> projectNameDataSet, ArrayList<String> projectDateDataSet, ArrayList<String> projectStatusDataSet, ArrayList<String> projectImageDataSet, Context context) {
        this.projectNameDataSet = projectNameDataSet;
        this.projectDateDataSet = projectDateDataSet;
        this.projectStatusDataSet = projectStatusDataSet;
        this.projectImageDataSet = projectImageDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileProjectCompleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_completed_project_item,parent,false);
        ProfileProjectCompleteAdapter.ProfileProjectCompleteHolder holder=new ProfileProjectCompleteAdapter.ProfileProjectCompleteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileProjectCompleteHolder holder, int position) {
        holder.profileProjectCompleteImage.setImageResource(Integer.parseInt(projectImageDataSet.get(position)));
        holder.profileProjectCompleteName.setText(projectNameDataSet.get(position));
        holder.profileProjectCompleteStatus.setText(projectStatusDataSet.get(position));
        holder.profileProjectCompleteDate.setText(projectDateDataSet.get(position));
        holder.profileDownloadProjectLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Downloading Project Letter...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectImageDataSet.size();
    }


    public class ProfileProjectCompleteHolder extends RecyclerView.ViewHolder{
        public TextView profileProjectCompleteName,profileProjectCompleteDate,profileProjectCompleteStatus,profileDownloadProjectLetter;
        public ImageView profileProjectCompleteImage;

        public ProfileProjectCompleteHolder(View itemView) {
            super(itemView);
            profileProjectCompleteName=itemView.findViewById(R.id.profileProjectCompleteName);
            profileProjectCompleteDate=itemView.findViewById(R.id.profileProjectCompleteDate);
            profileProjectCompleteStatus=itemView.findViewById(R.id.profileProjectCompleteStatus);
            profileProjectCompleteStatus=itemView.findViewById(R.id.profileProjectCompleteStatus);

            profileProjectCompleteImage=itemView.findViewById(R.id.profileProjectCompleteImage);

            profileDownloadProjectLetter=itemView.findViewById(R.id.profileDownloadProjectLetter);
        }
    }
}
