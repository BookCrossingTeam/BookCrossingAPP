package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.fragment.ReviewsArticleFragment;
import com.example.administrator.bookcrossingapp.fragment.ReviewsOwnFragment;
import com.example.administrator.bookcrossingapp.adapter.TabSimpleAdapter;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
    private TabLayout reviews_tab_layout;
    private ViewPager reviews_pager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        initView();

        Intent intent = getIntent();
        int userid = intent.getIntExtra("userid",0);
        ReviewsArticleFragment fragment1 = ReviewsArticleFragment.newInstance(userid);
        ReviewsOwnFragment fragment2 = ReviewsOwnFragment.newInstance(userid);
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        list.add("文章");
        list.add("个人");
        reviews_tab_layout.setupWithViewPager(reviews_pager);
        reviews_pager.setAdapter(new TabSimpleAdapter(getSupportFragmentManager(), mFragments, list));


    }


    private void initView() {
        reviews_tab_layout = (TabLayout) findViewById(R.id.reviews_tab_layout);
        reviews_pager = (ViewPager) findViewById(R.id.reviews_pager);
        reviews_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        }
    }

}
