package com.learnstack.dell.learnstackd.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnstack.dell.learnstackd.R;
import com.learnstack.dell.learnstackd.adapters.ProfileCourseCompleteAdapter;
import com.learnstack.dell.learnstackd.adapters.ProfileProjectCompleteAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedProjectFragment extends Fragment {

    private ArrayList<String> projectNameDataSet=new ArrayList<String>(){{add("Java");add("C++");add("Python");add("Machine Learning");add("Java");add("C++");add("Python");add("Machine Learning");}};
    private ArrayList<String> projectDateDataSet=new ArrayList<String>(){{add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");}};
    private ArrayList<String> projectImageDataSet=new ArrayList<String>(){{add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));add(Integer.toString(R.drawable.ic_computer_black));}};
    private ArrayList<String> projectStatusDataSet=new ArrayList<String>(){{add("Submitted");add("Evaluated");add("Submitted");add("Evaluated");add("Submitted");add("Evaluated");add("Submitted");add("Evaluated");}};

    private RecyclerView profileCompletedProjectRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public CompletedProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_completed_project, container, false);
        profileCompletedProjectRecyclerView=v.findViewById(R.id.profileCompletedProjectRecyclerView);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        profileCompletedProjectRecyclerView.setLayoutManager(layoutManager);
        adapter=new ProfileProjectCompleteAdapter(projectNameDataSet,projectDateDataSet,projectStatusDataSet,projectImageDataSet,getContext());
        profileCompletedProjectRecyclerView.setAdapter(adapter);
        return v;
    }

}
