package com.learnstack.dell.learnstackd.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.learnstack.dell.learnstackd.R;

import java.util.ArrayList;

public class ProfileBadgeAdapter extends RecyclerView.Adapter<ProfileBadgeAdapter.ProfileBadgeHolder> {

    private ArrayList<String> badgeImageDataset,badgeNameDataset;

    public ProfileBadgeAdapter(ArrayList<String> badgeImageDataset, ArrayList<String> badgeNameDataset) {
        this.badgeImageDataset = badgeImageDataset;
        this.badgeNameDataset = badgeNameDataset;
    }

    @NonNull
    @Override
    public ProfileBadgeAdapter.ProfileBadgeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_badges_item,parent,false);
        ProfileBadgeAdapter.ProfileBadgeHolder holder=new ProfileBadgeAdapter.ProfileBadgeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileBadgeAdapter.ProfileBadgeHolder holder, int position) {
        holder.profileBadgeName.setText(badgeNameDataset.get(position));
        holder.profileBadgeImage.setImageResource(Integer.parseInt(badgeImageDataset.get(position)));
    }

    @Override
    public int getItemCount() {
        return badgeImageDataset.size();
    }

    public class ProfileBadgeHolder extends RecyclerView.ViewHolder{
        public TextView profileBadgeName;
        public ImageView profileBadgeImage;
        public ProfileBadgeHolder(View itemView) {
            super(itemView);
            profileBadgeName=itemView.findViewById(R.id.profileBadgeName);
            profileBadgeImage=itemView.findViewById(R.id.profileBadgeImage);
        }
    }
}
