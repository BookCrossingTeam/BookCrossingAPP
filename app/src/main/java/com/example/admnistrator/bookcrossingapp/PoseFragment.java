package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 57010 on 2017/5/2.
 */

public class PoseFragment extends Fragment {
    private String mArgument;
    public static final String ARGUMENT = "argument";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pose_layout, container, false);
    }

    public static PoseFragment newInstance(String from)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        PoseFragment poseFragment = new PoseFragment();
        poseFragment.setArguments(bundle);
        return poseFragment;
    }
}
