package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yvemuki on 2017/4/29.
 */

public class FriendFragment extends Fragment {
    private String mArgument;
    public static final String ARGUMENT = "argument";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_splash, container, false);
    }
    public static FriendFragment newInstance(String from)
    {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        FriendFragment friendFragment = new FriendFragment();
        friendFragment.setArguments(bundle);
        return friendFragment;
    }
}
