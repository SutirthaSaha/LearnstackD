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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedCourseFragment extends Fragment {


    private ArrayList<String> nameDataSet=new ArrayList<String>(){{add("Java");add("C++");add("Python");add("Machine Learning");add("Java");add("C++");add("Python");add("Machine Learning");}};
    private ArrayList<String> dateDataSet=new ArrayList<String>(){{add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");add("11/12/13");}};

    private RecyclerView profileCompletedCourseRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public CompletedCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_completed_course, container, false);
        profileCompletedCourseRecyclerView=v.findViewById(R.id.profileCompletedCourseRecyclerView);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        profileCompletedCourseRecyclerView.setLayoutManager(layoutManager);
        adapter=new ProfileCourseCompleteAdapter(nameDataSet,dateDataSet,getContext());
        profileCompletedCourseRecyclerView.setAdapter(adapter);
        return v;
    }

}
