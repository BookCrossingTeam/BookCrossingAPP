package com.example.administrator.bookcrossingapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.bookcrossingapp.MessageManagement;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.FriendAdapter;
import com.example.administrator.bookcrossingapp.datamodel.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yvemuki on 2017/4/29.
 */

public class FriendFragment extends Fragment {
    private static final String TAG = "FriendFragment";
    public static final String ARGUMENT = "FriendFragment";
    private String mArgument;
    private View view;
    private FriendAdapter adapter;
    private List<Friend> friendList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefresh;

    public FriendFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        initFriends();
        initFriendsRecyclerView();
        initSwipe_refresh();
        return view;
    }

    public static FriendFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        FriendFragment friendFragment = new FriendFragment();
        friendFragment.setArguments(bundle);
        return friendFragment;
    }

    private void initFriends() {
        Log.i(TAG, "initFriends: ");
        MessageManagement.getInstance(getActivity()).initFrinedList(friendList);
        for (Friend friend : friendList)
            Log.i(TAG, "initFriends: " + friend.getIsread() + friend.getFriendName());
    }

    public void initFriendsRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friend_list_recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        adapter = new FriendAdapter(friendList);
        recyclerView.setAdapter(adapter);
    }


    public void initSwipe_refresh() {
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.friend_list_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MessageManagement.getInstance(getActivity()).getMsgFromRemote();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //initBookDetailData();
                                //adapter.notifyDataSetChanged();
                                initFriends();
                                adapter.notifyDataSetChanged();
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initFriends();
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
}
