package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yvemuki on 2017/4/29.
 */

public class HomeFragment extends Fragment {
    private String mArgument;

    private ViewPager viewPager;
    private ArrayList<View> pageview;
    private View view;

    private ImageView[] tips;//提示性点点数组
    private int currentPage = 0;//当前展示的页码

    private List<BookDetail> BookDetailList = new ArrayList<>();
    private BookDetailAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    public static final String ARGUMENT = "argument";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initViewPager();
        initRecyclerView();
        initSwipe_refresh();
        return view;
    }

    public static HomeFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    public void initViewPager() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        final int[] images = new int[]{R.drawable.banner, R.drawable.banner, R.drawable.banner, R.drawable.banner};//图片ID数组


        //存放点点的容器
        LinearLayout tipsBox = (LinearLayout) view.findViewById(R.id.tipsBox);
        //初始化 提示点点
        tips = new ImageView[4];
        for (int i = 0; i < tips.length; i++) {
            ImageView img = new ImageView(getContext()); //实例化一个点点
            img.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = img;
            if (i == 0) {
                img.setBackgroundResource(R.drawable.banner_page_now);//蓝色背景
            } else {
                img.setBackgroundResource(R.drawable.banner_page);//黑色背景
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(150, 25));
            params.leftMargin = 5;
            params.rightMargin = 5;
            tipsBox.addView(img, params); //把点点添加到容器中
        }

        //-----初始化PagerAdapter------
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return images.length;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object o) {
                //container.removeViewAt(position);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView im = new ImageView(getContext());

                im.setImageResource(images[position]);
                container.addView(im);
                return im;
            }

        };

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                tips[currentPage].setBackgroundResource(R.drawable.banner_page);
                currentPage = position;
                tips[position].setBackgroundResource(R.drawable.banner_page_now);
            }
        });
    }

    public void initRecyclerView() {
        initBookDetailData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        adapter = new BookDetailAdapter(BookDetailList);
        recyclerView.setAdapter(adapter);
    }

    public void initBookDetailData() {
        for (int i = 0; i < 1; i++) {
            BookDetail a = new BookDetail("Yvettemuki", "<The Great Gatsby>", "Francis Scott Key Fitzgerald", "", "");
            BookDetailList.add(a);
        }
    }

    public void initSwipe_refresh() {
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBook();
            }
        });
    }

    public void refreshBook() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ;// do somethings

                initBookDetailData();
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        }).start();
    }
}
