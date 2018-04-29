package com.example.administrator.bookcrossingapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Lyh
 */
public class UserdetailWantlistFragment extends Fragment {

    public static final String ARGUMENT = "UserdetailWantlistFragment";
    private View view;
    private BookDetailAdapter adapter;
    private List<BookDetail> wantlist = new ArrayList<>();
    private LinearLayout list_title_layout;

    public UserdetailWantlistFragment() {
        super();
        initWantList();
    }

    private void initWantList() {
        /*for (int i = 0; i < 3; i++) {
            BookDetail a = new BookDetail("Yvettemuki", "《The Great Gatsby》", "下拉刷新页面", "", "");
            wantlist.add(a);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list, container, false);
        list_title_layout = view.findViewById(R.id.list_title_layout);
        list_title_layout.setVisibility(View.GONE);//隐藏标题，为了代码复用
        initWantListRecyclerView();
        return view;
    }

    private void initWantListRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.booklist_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookDetailAdapter(wantlist);
        recyclerView.setAdapter(adapter);
    }

    public static UserdetailWantlistFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        UserdetailWantlistFragment userdetailWantlistFragment = new UserdetailWantlistFragment();
        userdetailWantlistFragment.setArguments(bundle);
        return userdetailWantlistFragment;
    }
}
