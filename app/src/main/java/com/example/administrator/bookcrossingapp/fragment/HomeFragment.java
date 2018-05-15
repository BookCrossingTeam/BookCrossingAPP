package com.example.administrator.bookcrossingapp.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.ReviewsDetailActivity;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yvemuki on 2017/4/29.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private String mArgument;

    private ViewPager viewPager;
    private ArrayList<View> pageview;
    private View view;

    private ImageView[] tips;//提示性点点数组
    private int currentPage = 0;//当前展示的页码

    private List<BookDetail> BookDetailList = new ArrayList<>();
    private BookDetailAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private long lastLoadTime;
    private int bookType;

    public static final String ARGUMENT = "argument";

    private int offsetStep;
    private int currentPos = 1;

    private Handler handler;
    private Runnable runnable;

    public HomeFragment() {
        super();
        offsetStep = 10;
        bookType = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.loading);
        initViewPager();
        initRecyclerView();
        initBookDetailData();
        initSwipe_refresh();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currentPos+1);
                handler.postDelayed(runnable, 5 * 1000);
            }
        };
        handler.postDelayed(runnable,5*1000);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
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

        final String[] imgURL = new String[]{
                "5D22EF068FD1C305FC558206E1D40AB2042d6b52758bee578b7280f6d679a49c",  //4
                "6AE928349E802D3DF59B43CBC6AA06A747ff07e2322c28831256cd998c0942e",  //1
                "636AA8EA3912C94AC690F9A0DFD03F9C45fef0e29b71bebcba0e122fbc3a5f29",  //2
                "88B8310B63FBDB4E75575A12474E9DAA26de893cf007f13ed02b8e735ceefe72",  //3
                "5D22EF068FD1C305FC558206E1D40AB2042d6b52758bee578b7280f6d679a49c",  //4
                "6AE928349E802D3DF59B43CBC6AA06A747ff07e2322c28831256cd998c0942e"  //1
        };


        //存放点点的容器
        LinearLayout tipsBox = (LinearLayout) view.findViewById(R.id.tipsBox);
        //初始化 提示点点
        tips = new ImageView[4];
        for (int i = 0; i < tips.length; i++) {
            ImageView img = new ImageView(getContext()); //实例化一个点点
            img.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = img;
            if (i == currentPage) {
                img.setBackgroundResource(R.drawable.banner_page_now);//蓝色背景
            } else {
                img.setBackgroundResource(R.drawable.banner_page);//黑色背景
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(100, 15));
            params.leftMargin = 5;
            params.rightMargin = 5;
            tipsBox.addView(img, params); //把点点添加到容器中
        }

        //-----初始化PagerAdapter------
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 6;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object o) {
                Log.i(TAG, "destroyItem: " + position);
                Glide.clear((View) o);
                container.removeView((View) o);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                Log.i(TAG, "instantiateItem: " + position);
                ImageView im = new ImageView(getContext());

                Glide.with(getContext()).load("http://120.24.217.191/Book/img/reviewImg/" + imgURL[position]).into(im);
                im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                im.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ReviewsDetailActivity.class);
                        intent.putExtra("articleId", 7);
                        intent.putExtra("cover", imgURL[position]);
                        getActivity().startActivity(intent);
                    }
                });
                container.addView(im);
                return im;
            }

        };

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int FIRST_ITEM_INDEX = 1;
            int LAST_ITEM_INDEX = 4;
            boolean isChanged;


            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                if (ViewPager.SCROLL_STATE_IDLE == arg0) {
                    Log.d(TAG, "onPageScrollStateChanged: " + arg0);
                    if (isChanged) {
                        isChanged = false;
                        viewPager.setCurrentItem(currentPos, false);
                    }
                }

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                // TODO Auto-generated method stub
                if (position > LAST_ITEM_INDEX) {
                    isChanged = true;
                    currentPos = FIRST_ITEM_INDEX;
                } else if (position < FIRST_ITEM_INDEX) {
                    isChanged = true;
                    currentPos = LAST_ITEM_INDEX;
                } else {
                    currentPos = position;
                }
                tips[currentPage].setBackgroundResource(R.drawable.banner_page);
                currentPage = currentPos - 1;
                tips[currentPage].setBackgroundResource(R.drawable.banner_page_now);
            }
        });
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        adapter = new BookDetailAdapter(BookDetailList, getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = LayoutManager.findLastVisibleItemPosition();
                    if (BookDetailList.size() > 3 && lastItemPosition == BookDetailList.size() - 1) {
                        progressBar.setVisibility(View.VISIBLE);
                        Log.i(TAG, "onScrollStateChanged: updateList");
                        updateList();
                    }
                }
            }

        });
    }

    public void initBookDetailData() {
        BookDetailList.clear();
        List<BookDetail> dbList = null;
        if (bookType == 0)
            dbList = DataSupport.order("posetime desc").limit(offsetStep).find(BookDetail.class);
        else
            dbList = DataSupport.where("bookType = ? ", bookType + "").order("posetime desc").limit(offsetStep).find(BookDetail.class);
        //username, bookName, author, press, recommendedReason,imgUrl
        lastLoadTime = 0;
        if (!dbList.isEmpty())
            lastLoadTime = dbList.get(dbList.size() - 1).getPosetime();
        BookDetailList.addAll(dbList);
    }

    public void initSwipe_refresh() {
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark);

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
                // do somethings
                try {
                    long infTime = 0;
                    List<BookDetail> bookDetaidblList = null;
                    if (bookType == 0)
                        bookDetaidblList = DataSupport.order("posetime desc").limit(1).find(BookDetail.class);
                    else
                        bookDetaidblList = DataSupport.where("bookType = ? ", bookType + "").order("posetime desc").limit(1).find(BookDetail.class);
                    if (!bookDetaidblList.isEmpty())
                        infTime = bookDetaidblList.get(0).getPosetime();

                    OkHttpClient client = new OkHttpClient();
                    client.retryOnConnectionFailure();
                    //String Time1 = request.getParameter("infTime");
                    //		String Time2 = request.getParameter("supTime");
                    //		String Type = request.getParameter("bookType");
                    RequestBody requestBody = new FormBody.Builder().add("infTime", infTime + "").add("supTime", "7226553600000").add("bookType", bookType + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryPose").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        //Log.i(TAG, "run: " + response.body());
                        String responseData = response.body().string();

                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String username = jsonObject.getString("username");
                            String bookName = jsonObject.getString("bookName");
                            String author = jsonObject.getString("author");
                            String press = jsonObject.getString("publish");
                            String recommendedReason = jsonObject.getString("reason");
                            String imgUrl = jsonObject.getString("imgUrl");
                            long posetime = Long.parseLong(jsonObject.getString("poseTime"));
                            int userId = Integer.parseInt(jsonObject.getString("userId"));
                            int bookId = Integer.parseInt(jsonObject.getString("id"));
                            int bookType = Integer.parseInt(jsonObject.getString("bookType"));
                            String userheadpath = jsonObject.getString("headImgPath");

                            BookDetail book = new BookDetail();
                            book.setUsername(username);
                            book.setBookName(bookName);
                            book.setAuthor(author);
                            book.setPress(press);
                            book.setRecommendedReason(recommendedReason);
                            book.setBookImageUrl(imgUrl);
                            book.setPosetime(posetime);
                            book.setUserid(userId);
                            book.setUserheadpath(userheadpath);
                            book.setBookid(bookId);
                            book.setBookType(bookType);
                            book.save();
                        }
                        initBookDetailData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public void updateList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do somethings
                try {
                    long dbTime = 0;
                    List<BookDetail> bookDetaildbList = null;
                    if (bookType == 0)
                        bookDetaildbList = DataSupport.where("posetime < ?", lastLoadTime + "").order("posetime desc").limit(1).find(BookDetail.class);
                    else
                        bookDetaildbList = DataSupport.where("bookType = ? and posetime < ?", bookType + "", lastLoadTime + "").order("posetime desc").limit(1).find(BookDetail.class);
                    if (!bookDetaildbList.isEmpty())
                        dbTime = bookDetaildbList.get(0).getPosetime();

                    OkHttpClient client = new OkHttpClient();
                    client.retryOnConnectionFailure();
                    //String Time1 = request.getParameter("infTime");
                    //		String Time2 = request.getParameter("supTime");
                    //		String Type = request.getParameter("bookType");
                    RequestBody requestBody = new FormBody.Builder().add("infTime", dbTime + "").add("supTime", lastLoadTime + "").add("bookType", bookType + "").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryPose").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String username = jsonObject.getString("username");
                            String bookName = jsonObject.getString("bookName");
                            String author = jsonObject.getString("author");
                            String press = jsonObject.getString("publish");
                            String recommendedReason = jsonObject.getString("reason");
                            String imgUrl = jsonObject.getString("imgUrl");
                            long posetime = Long.parseLong(jsonObject.getString("poseTime"));
                            int userId = Integer.parseInt(jsonObject.getString("userId"));
                            int bookId = Integer.parseInt(jsonObject.getString("id"));
                            int bookType = Integer.parseInt(jsonObject.getString("bookType"));
                            String userheadpath = jsonObject.getString("headImgPath");

                            BookDetail book = new BookDetail();
                            book.setUsername(username);
                            book.setBookName(bookName);
                            book.setAuthor(author);
                            book.setPress(press);
                            book.setRecommendedReason(recommendedReason);
                            book.setBookImageUrl(imgUrl);
                            book.setPosetime(posetime);
                            book.setUserid(userId);
                            book.setUserheadpath(userheadpath);
                            book.setBookid(bookId);
                            book.setBookType(bookType);
                            book.save();

                            lastLoadTime = posetime;
                            BookDetailList.add(book);
                        }
                        int num = offsetStep - jsonArray.length();
                        List<BookDetail> dbList = null;
                        if (bookType == 0)
                            dbList = DataSupport.where("posetime < ? ", lastLoadTime + "").order("posetime desc").limit(num).find(BookDetail.class);
                        else
                            dbList = DataSupport.where("bookType = ? and posetime < ? ", bookType + "", lastLoadTime + "").order("posetime desc").limit(num).find(BookDetail.class);
                        //username, bookName, author, press, recommendedReason,imgUrl
                        if (!dbList.isEmpty())
                            lastLoadTime = dbList.get(dbList.size() - 1).getPosetime();
                        BookDetailList.addAll(dbList);
                    }
                    //Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    public void smoothScroll() {
        Log.i(TAG, "smoothScroll: ");
        recyclerView.smoothScrollToPosition(0);
    }

    public void selectBookType(int type) {
        if (type == bookType)
            return;
        recyclerView.smoothScrollToPosition(0);
        bookType = type;
        Log.i(TAG, "selectBookType: " + BookDetail.bookTypeName[type]);
        initBookDetailData();
        adapter.notifyDataSetChanged();
    }
}
