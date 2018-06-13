package com.learnstack.dell.learnstackd.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.learnstack.dell.learnstackd.R;

import java.util.ArrayList;

public class ProfileSkillAdapter extends RecyclerView.Adapter<ProfileSkillAdapter.ProfileSkillHolder> {

    private ArrayList<String> skillNameDataSet,skillRatingDataSet;

    public ProfileSkillAdapter(ArrayList<String> skillNameDataSet, ArrayList<String> skillRatingDataSet) {
        this.skillNameDataSet = skillNameDataSet;
        this.skillRatingDataSet = skillRatingDataSet;
    }

    @NonNull
    @Override
    public ProfileSkillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_skill_item,parent,false);
        ProfileSkillAdapter.ProfileSkillHolder holder=new ProfileSkillAdapter.ProfileSkillHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileSkillHolder holder, int position) {
        holder.profileSkillName.setText(skillNameDataSet.get(position));
        holder.profileSkillRating.setRating(Float.parseFloat(skillRatingDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return skillNameDataSet.size();
    }

    public class ProfileSkillHolder extends RecyclerView.ViewHolder{
        public TextView profileSkillName;
        public RatingBar profileSkillRating;
        public ProfileSkillHolder(View itemView) {
            super(itemView);
            profileSkillName=itemView.findViewById(R.id.profileSkillName);
            profileSkillRating=itemView.findViewById(R.id.profileSkillRating);
        }
    }
}
