package com.example.administrator.bookcrossingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.Search;
import com.example.administrator.bookcrossingapp.adapter.SearchAdapter;
import com.example.administrator.bookcrossingapp.activity.SearchDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment {
    public static final String ARGUMENT = "SearchFragment";
    private String mArgument;
    private View view;
    private SearchAdapter adapter;
    private List<Search> searchList = new ArrayList<>();
    private View viewSearchTitle;
    private ImageButton btnSearch;
    private EditText editContent;
    private String content;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initSearchRecyclerView();
        initSearch();
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
        viewSearchTitle = view.findViewById(R.id.search_title);
        btnSearch = viewSearchTitle.findViewById(R.id.btnSearch);
        editContent = viewSearchTitle.findViewById(R.id.editContent);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = editContent.getText().toString();
                if (content.equals("") ) {
                    Toast.makeText(getActivity(), "搜索内容不能为空白", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), SearchDetailActivity.class);
                intent.putExtra("searchContent", content);
                startActivityForResult(intent, 1);
            }
        });


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

    //回传的值
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnContent = data.getStringExtra("searchContent");
                    editContent.setText(returnContent);
                }
                break;
            default:
        }
    }
}
