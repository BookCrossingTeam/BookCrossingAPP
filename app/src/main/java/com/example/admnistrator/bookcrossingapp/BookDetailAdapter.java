package com.example.admnistrator.bookcrossingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 57010 on 2017/5/17.
 */

public class BookDetailAdapter extends RecyclerView.Adapter<BookDetailAdapter.ViewHolder> {

    private List<BookDetail> mBookDetailList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView bookName;
        TextView author;

        public ViewHolder(View view)
        {
            super(view);
            username = (TextView) view.findViewById(R.id.textView9);
            bookName = (TextView) view.findViewById(R.id.textView10);
            author = (TextView) view.findViewById(R.id.textView11);
        }
    }

    public BookDetailAdapter(List<BookDetail> BookDetailList) {
        mBookDetailList = BookDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        BookDetail bookdetail = mBookDetailList.get(position);
        holder.username.setText(bookdetail.getUsername());
        holder.bookName.setText(bookdetail.getBookName());
        holder.author.setText(bookdetail.getAuthor());
    }

    @Override
    public int getItemCount()
    {
        return mBookDetailList.size();
    }
}
