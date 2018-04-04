package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Search> mSearchList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView searchPic;
        TextView searchContent;
        View searchView; //用来设置整个item的点击事件

        public ViewHolder(View v) {
            super(v);
            searchView = v;
            searchPic = (ImageView) v.findViewById(R.id.search_item_pic);
            searchContent = (TextView) v.findViewById(R.id.search_item_contnet);
        }
    }

    public SearchAdapter(List<Search> searchList) {
        mSearchList = searchList;
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item, parent, false);
        final SearchAdapter.ViewHolder holder = new SearchAdapter.ViewHolder(view);
        //为item设置点击事件
        holder.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂时先不传递消息
//                int position = holder.getAdapterPosition();
//                Search search = mSearchList.get(position);
                Intent intent = new Intent(v.getContext(), SearchDetailActivity.class);
                //这里会添加需要传递的消息
                view.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Search search = mSearchList.get(position);
        holder.searchPic.setImageResource(search.getSearchPic());
        holder.searchContent.setText(search.getSearchContent());
    }

    @Override
    public int getItemCount() {
        return mSearchList.size();
    }

}
