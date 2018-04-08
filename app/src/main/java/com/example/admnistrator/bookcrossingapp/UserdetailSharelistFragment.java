package com.example.admnistrator.bookcrossingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Lyh
 */
public class UserdetailSharelistFragment extends Fragment {

    private View view;
    private BookDetailAdapter adapter;
    private List<BookDetail> sharelist = new ArrayList<>();
    private LinearLayout list_title_layout;

    public UserdetailSharelistFragment() {
        super();
        initShareList();
    }

    private void initShareList() {
        for (int i = 0; i < 3; i++) {
            BookDetail a = new BookDetail("Yvettemuki", "《The Great Gatsby》", "下拉刷新页面", "", "");
            sharelist.add(a);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list, container, false);
        list_title_layout = view.findViewById(R.id.list_title_layout);
        list_title_layout.setVisibility(View.GONE);//隐藏标题，为了代码复用
        initShareListRecyclerView();
        return view;
    }

    private void initShareListRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.booklist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookDetailAdapter(sharelist);
        recyclerView.setAdapter(adapter);
    }

}
