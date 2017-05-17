package com.example.admnistrator.bookcrossingapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yvemuki on 2017/4/29.
 */

public class DataGenerator {
    public static final int[] mTabRes = new int[]{R.drawable.tab_home_pressed, R.drawable.tab_friend_pressed, R.drawable.tab_pose_pressed, R.drawable.tab_search_pressed, R.drawable.tab_me_pressed};
    public static final int[] mTabResPressed = new int[]{R.drawable.tab_home_hover, R.drawable.tab_friend_hover, R.drawable.tab_pose_hover, R.drawable.tab_search_hover, R.drawable.tab_me_hover};
    //public static final String []mTabTitle = new String[]{"首页","发现","关注","我的"};

    public static Fragment[] getFragments(String from) {
        Fragment fragments[] = new Fragment[5];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = FriendFragment.newInstance(from);
        fragments[2] = PoseFragment.newInstance(from);
        fragments[3] = FriendFragment.newInstance(from);
        fragments[4] = FriendFragment.newInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_tab_icon, null);
        //这里本来还要获取文字的，只获取了自定义的image是因为切出来的图已经包含了文字
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
            /*TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
            tabText.setText(mTabTitle[position]);*/
        return view;
    }
}
