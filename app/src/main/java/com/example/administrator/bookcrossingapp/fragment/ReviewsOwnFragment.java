package com.example.administrator.bookcrossingapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.ReviewItem;
import com.example.administrator.bookcrossingapp.activity.ReviewsEditActivity;
import com.example.administrator.bookcrossingapp.adapter.ReviewItemAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Lyh
 */
public class ReviewsOwnFragment extends Fragment {


    private View view;
    private ReviewItemAdapter adapter;
    private List<ReviewItem> reviewlist = new ArrayList<>();
    private Button btn_write;

    public ReviewsOwnFragment() {
        super();
        initReviewList();
    }

    private void initReviewList() {
        for (int i = 0; i < 3; i++) {
            ReviewItem reviewItem = new ReviewItem("我的：中国最美大学：中国海洋大学", "作者：yyy", R.drawable.ouc_background);
            reviewlist.add(reviewItem);
            ReviewItem reviewItem2 = new ReviewItem("我的：一起来海大看樱花吧", "作者：hhh", R.drawable.ouc_background2);
            reviewlist.add(reviewItem2);
        }
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

        btn_write = view.findViewById(R.id.reviews_write);//可发表
        btn_write.setVisibility(View.VISIBLE);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ReviewsEditActivity.class);
                startActivity(intent);
            }
        });

    }
}
