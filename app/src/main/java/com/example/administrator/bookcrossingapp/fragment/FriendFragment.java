package com.example.administrator.bookcrossingapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private View view;
    private FriendAdapter adapter;
    private List<Friend> friendList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefresh;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    private Handler handler;
    private Runnable runnable;

    public FriendFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        //initFriends();
        initFriendsRecyclerView();
        initSwipe_refresh();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                initFriends();
                adapter.notifyDataSetChanged();
                handler.postDelayed(runnable, 10000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
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

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "onRefresh run: ");
                        if (MessageManagement.getInstance(getActivity()).getMsgFromRemote()>-1) {
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
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //initBookDetailData();
                                    //adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    swipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }
                }).start();
            }
        };

        swipeRefresh.setOnRefreshListener(onRefreshListener);
    }

}
