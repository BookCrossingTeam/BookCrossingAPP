package com.example.administrator.bookcrossingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.SearchDetailActivity;
import com.example.administrator.bookcrossingapp.adapter.SearchAdapter;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.example.administrator.bookcrossingapp.datamodel.Search;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
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
                if (content.equals("")) {
                    Toast.makeText(getActivity(), "搜索内容不能为空白", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), SearchDetailActivity.class);
                intent.putExtra("searchContent", content);
                startActivityForResult(intent, 1);
            }
        });

        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchList.clear();
                if (!editContent.getText().toString().equals(""))
                    researchList(editContent.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    public void researchList(String content) {

        HashSet<String> hs = new HashSet<String>();

        List<BookDetail> bookDetailList = DataSupport.where("bookName like ? ", content + "%").find(BookDetail.class);
        for (BookDetail bookDetail : bookDetailList) {
            if (hs.contains(bookDetail.getBookName()))
                continue;
            hs.add(bookDetail.getBookName());
            Search search = new Search(R.drawable.button_search, bookDetail.getBookName());
            search.setDisPlay(bookDetail.getBookName());
            search.setType(Search.LOCAL);
            searchList.add(search);
        }

        bookDetailList = DataSupport.where("bookName like ? ", "%" + content + "%").find(BookDetail.class);
        for (BookDetail bookDetail : bookDetailList) {
            if (hs.contains(bookDetail.getBookName()))
                continue;
            hs.add(bookDetail.getBookName());
            Search search = new Search(R.drawable.button_search, bookDetail.getBookName());
            search.setDisPlay(bookDetail.getBookName());
            search.setType(Search.LOCAL);
            searchList.add(search);
        }

        bookDetailList = DataSupport.where("author like ? ", content + "%").find(BookDetail.class);
        for (BookDetail bookDetail : bookDetailList) {
            if (hs.contains(bookDetail.getAuthor()))
                continue;
            hs.add(bookDetail.getAuthor());
            Search search = new Search(R.drawable.button_search, bookDetail.getAuthor());
            search.setDisPlay(bookDetail.getAuthor());
            search.setType(Search.LOCAL);
            searchList.add(search);
        }

        bookDetailList = DataSupport.where("author like ? ", "%" + content + "%").find(BookDetail.class);
        for (BookDetail bookDetail : bookDetailList) {
            if (hs.contains(bookDetail.getAuthor()))
                continue;
            hs.add(bookDetail.getAuthor());
            Search search = new Search(R.drawable.button_search, bookDetail.getAuthor());
            search.setDisPlay(bookDetail.getAuthor());
            search.setType(Search.LOCAL);
            searchList.add(search);
        }

        Search search = new Search(R.drawable.button_search, content);
        search.setDisPlay("去豆瓣搜索");
        search.setType(Search.DOUBAN);
        searchList.add(search);

        adapter.notifyDataSetChanged();
    }
}
