package com.example.administrator.bookcrossingapp.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.SearchDetailActivity;
import com.example.administrator.bookcrossingapp.datamodel.Search;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Search> mSearchList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchContent;
        View searchView; //用来设置整个item的点击事件

        public ViewHolder(View v) {
            super(v);
            searchView = v;
            searchContent = (TextView) v.findViewById(R.id.search_item_contnet);
        }
    }

    public SearchAdapter(List<Search> searchList) {
        mSearchList = searchList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //为item设置点击事件
        holder.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Search search = mSearchList.get(position);
                if (search.getType() == Search.DOUBAN) {
                    Intent intent = new Intent(v.getContext(), SearchDetailActivity.class);
                    //这里会添加需要传递的消息
                    intent.putExtra("type",Search.DOUBAN);
                    intent.putExtra("searchContent",search.getSearchContent());
                    view.getContext().startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(v.getContext(), SearchDetailActivity.class);
                    //这里会添加需要传递的消息
                    intent.putExtra("searchContent",search.getSearchContent());
                    view.getContext().startActivity(intent);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Search search = mSearchList.get(position);
        holder.searchContent.setText(search.getDisPlay());
    }

    @Override
    public int getItemCount() {
        return mSearchList.size();
    }

}
