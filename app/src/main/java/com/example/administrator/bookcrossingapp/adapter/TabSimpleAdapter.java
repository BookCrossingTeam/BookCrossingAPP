package com.example.administrator.bookcrossingapp.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/7.
 */
public class TabSimpleAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private ArrayList<String> list;

    public TabSimpleAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> list) {
        super(fm);
        this.fragments = fragments;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
