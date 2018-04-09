package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.TabSimpleAdapter;
import com.example.administrator.bookcrossingapp.fragment.UserdetailReviewsFragment;
import com.example.administrator.bookcrossingapp.fragment.UserdetailSharelistFragment;
import com.example.administrator.bookcrossingapp.fragment.UserdetailWantlistFragment;

import java.util.ArrayList;

public class UserDetailActivity extends AppCompatActivity {


    private TabLayout userdetail_tab_layout;
    private ViewPager userdetail_pager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    private TextView tv_username;
    private ImageView img_icon;
    private String usernameValue;
    private int imgIconPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initView();
        initData();
        UserdetailSharelistFragment fragment1 = new UserdetailSharelistFragment();
        UserdetailWantlistFragment fragment2 = new UserdetailWantlistFragment();
        UserdetailReviewsFragment fragment3 = new UserdetailReviewsFragment();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        list.add("shareList");
        list.add("wantList");
        list.add("reviewsList");
        userdetail_tab_layout.setupWithViewPager(userdetail_pager);
        userdetail_pager.setAdapter(new TabSimpleAdapter(getSupportFragmentManager(), mFragments, list));
    }

    private void initData() {
        //从上一个页面传过来的值
        Intent intent = getIntent();
        usernameValue = intent.getStringExtra("username");
        tv_username.setText(usernameValue);
    }

    private void initView() {
        userdetail_tab_layout = (TabLayout) findViewById(R.id.userdetail_tab_layout);
        userdetail_pager = (ViewPager) findViewById(R.id.userdetail_pager);
        tv_username = findViewById(R.id.user_detail_username);
        img_icon = findViewById(R.id.img_icon);

        userdetail_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragments.get(0);
                break;
            case 1:
                fragment = mFragments.get(1);
                break;
            case 2:
                fragment = mFragments.get(2);
                break;
        }
    }
}
