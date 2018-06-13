package com.learnstack.dell.learnstackd.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.activities.DashboardActivity;
import com.learnstack.dell.learnstackd.adapters.ProfileBadgeAdapter;
import com.learnstack.dell.learnstackd.adapters.ProfileSkillAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ArrayList<String> skillNameDataSet=new ArrayList<String>(){{add("Java");add("C++");add("Python");add("Machine Learning");}};
    private ArrayList<String> skillRatingDataSet=new ArrayList<String>(){{add("2.5");add("1.0");add("4.5");add("3.5");}};

    private ArrayList<String> badgeNameDataSet=new ArrayList<String>(){{add("Novice");add("HardWorking");add("Amatuer");add("Professional");}};
    private ArrayList<String> badgeImageDataSet=new ArrayList<String>(){{add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));}};

    private ProfileSkillAdapter profileSkillAdapter;
    private RecyclerView profileSkillsRecyclerView,profileBadgesRecyclerView;
    private RecyclerView.Adapter skillAdapter,badgeAdapter;
    private RecyclerView.LayoutManager skillLayoutManager,badgeLayoutManager;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        profileSkillsRecyclerView=v.findViewById(R.id.profileSkillsRecyclerView);
        profileSkillsRecyclerView.setNestedScrollingEnabled(false);

        profileBadgesRecyclerView=v.findViewById(R.id.profileBadgesRecyclerView);
        profileBadgesRecyclerView.setNestedScrollingEnabled(false);

        skillLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        profileSkillsRecyclerView.setLayoutManager(skillLayoutManager);

        badgeLayoutManager=new GridLayoutManager(getContext(),3);
        profileBadgesRecyclerView.setLayoutManager(badgeLayoutManager);

        skillAdapter=new ProfileSkillAdapter(skillNameDataSet,skillRatingDataSet);
        profileSkillsRecyclerView.setAdapter(skillAdapter);

        badgeAdapter=new ProfileBadgeAdapter(badgeImageDataSet,badgeNameDataSet);
        profileBadgesRecyclerView.setAdapter(badgeAdapter);

        return v;
    }

}
