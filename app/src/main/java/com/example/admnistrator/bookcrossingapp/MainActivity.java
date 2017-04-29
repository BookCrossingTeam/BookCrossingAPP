package com.example.admnistrator.bookcrossingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private Fragment[]mFragmensts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        setContentView(R.layout.bottom_tab_layout);
        mFragmensts = DataGenerator.getFragments("TabLayout Tab");

        initView();

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

                //改变Tab 状态
                for(int i=0;i< mTabLayout.getTabCount();i++){
                    if(i == tab.getPosition()){
                        mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabResPressed[i]));
                    }else{
                        mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabRes[i]));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_home_pressed)).setText(DataGenerator.mTabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_friend_pressed)).setText(DataGenerator.mTabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_pose_pressed)).setText(DataGenerator.mTabTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_search_pressed)).setText(DataGenerator.mTabTitle[3]));*/
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_home_pressed)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_friend_pressed)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_pose_pressed)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_search_pressed)));


    }

    private void onTabItemSelected(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;
            case 3:
                fragment = mFragmensts[3];
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }


        /**
         * 获取Tab 显示的内容
         * @param context
         * @param position
         * @return

        public static View getTabView(Context context, int position){
            View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
            ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
            tabIcon.setImageResource(DataGenerator.mTabRes[position]);
            TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
            tabText.setText(mTabTitle[position]);
            return view;
        }*/

}
