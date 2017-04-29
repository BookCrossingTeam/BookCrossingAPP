package com.example.admnistrator.bookcrossingapp;

import android.support.v4.app.Fragment;

/**
 * Created by yvemuki on 2017/4/29.
 */

public class DataGenerator {
    public static final int []mTabRes = new int[]{R.drawable.tab_home_pressed,R.drawable.tab_friend_pressed,R.drawable.tab_pose_pressed,R.drawable.tab_search_pressed};
    public static final int []mTabResPressed = new int[]{R.drawable.tab_home_pressed,R.drawable.tab_friend_pressed,R.drawable.tab_pose_pressed,R.drawable.tab_search_pressed};
    //public static final String []mTabTitle = new String[]{"首页","发现","关注","我的"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[4];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = FriendFragment.newInstance(from);
        fragments[2] = HomeFragment.newInstance(from);
        fragments[3] = FriendFragment.newInstance(from);
        return fragments;
    }
}
