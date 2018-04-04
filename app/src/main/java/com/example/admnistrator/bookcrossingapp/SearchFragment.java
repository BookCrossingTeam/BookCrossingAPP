package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    public static final String ARGUMENT = "SearchFragment";
    private String mArgument;
    private View view;
    private SearchAdapter adapter;
    private List<Search> searchList = new ArrayList<>();

    public SearchFragment() {
        super();
        initSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initSearchRecyclerView();
        return view;
    }

    public static SearchFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    private void initSearch() {
        for (int i = 0; i < 3; i++) {
            Search search1 = new Search(android.R.drawable.ic_menu_search, "搜索内容");
            searchList.add(search1);
            Search search2 = new Search(android.R.drawable.ic_menu_search, "Angelababy");
        }
    }

    public void initSearchRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_list_recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        adapter = new SearchAdapter(searchList);
        recyclerView.setAdapter(adapter);
    }
}
