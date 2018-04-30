package com.example.administrator.bookcrossingapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.ReviewItem;
import com.example.administrator.bookcrossingapp.adapter.ReviewItemAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * @author Lyh
 */
public class ReviewsArticleFragment extends Fragment {

    private View view;
    private ReviewItemAdapter adapter;
    private int userid;
    private List<ReviewItem> reviewlist = new ArrayList<>();


    public ReviewsArticleFragment() {
        super();
    }

    //创建newInstance方法的方法来向fragment传递参数(注意这个方法是属于类方法）
    public static final ReviewsArticleFragment newInstance(int userid){
        ReviewsArticleFragment fragment = new ReviewsArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userid",userid);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid =getArguments().getInt("userid");
        initReviewListTest();

    }

    private void initReviewListTest() {
        String test = Integer.toString(userid);
        Log.i("Reviewtest1111",test);
        for (int i = 0; i < 3; i++) {
            ReviewItem reviewItem = new ReviewItem("中国最美大学：中国海洋大学", "作者： "+test, R.drawable.ouc_background);
            reviewlist.add(reviewItem);
            ReviewItem reviewItem2 = new ReviewItem("一起来海大看樱花吧", "作者：hhh", R.drawable.ouc_background2);
            reviewlist.add(reviewItem2);
        }
    }
    private void initReviewList(){
        //从服务器端获取数据
       new Thread(new Runnable() {
           @Override
           public void run() {
               OkHttpClient client = new OkHttpClient();
               //不懂这里为什么要加“”
               RequestBody requestBody = new FormBody.Builder().add("userid", userid+"").build();
               Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/queryGet").post(requestBody).build();
           }
       });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews, container, false);
        initReviewListRecyclerView();
        return view;
    }

    private void initReviewListRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.reviews_article_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReviewItemAdapter(reviewlist);
        recyclerView.setAdapter(adapter);
    }
}
