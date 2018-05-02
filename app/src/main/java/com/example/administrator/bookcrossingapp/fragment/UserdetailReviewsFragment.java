package com.example.administrator.bookcrossingapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.ReviewItem;
import com.example.administrator.bookcrossingapp.adapter.ReviewItemAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Lyh
 */
public class UserdetailReviewsFragment extends Fragment {

    private View view;
    private ReviewItemAdapter adapter;
    private List<ReviewItem> reviewlist = new ArrayList<>();

    public UserdetailReviewsFragment() {
        super();
        initReviewList();
    }

    private void initReviewList() {
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
