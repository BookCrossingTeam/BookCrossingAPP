package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class MeFragment extends Fragment {
    public static final String ARGUMENT = "MeFragment";
    private String mArgument;
    private View view;
    //private FriendAdapter adapter;
    //private List<Friend> friendList = new ArrayList<>();

    public MeFragment(){
        super();
        //initMe();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_splash, container, false);
        //initMeRecyclerView();
        return view;
    }

    public static MeFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        MeFragment meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        return meFragment;
    }

//    private void initMe(){
//        for (int i = 0; i < 3; i++){
//            Friend friend1 = new Friend(R.drawable.friend_list_icon, "Yvettemuki");
//            friendList.add(friend1);
//            Friend friend2 = new Friend(R.drawable.friend_list_icon,"Angelababy");
//        }
//    }

//    public void initFriendsRecyclerView(){
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friend_list_recyclerView);
//        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(LayoutManager);
//        adapter = new FriendAdapter(friendList);
//        recyclerView.setAdapter(adapter);
//    }
}
