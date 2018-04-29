package com.example.administrator.bookcrossingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.ExchangeBookDetailsActivity;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 57010 on 2017/5/17.
 */

public class BookDetailAdapter extends RecyclerView.Adapter<BookDetailAdapter.ViewHolder> {

    private List<BookDetail> mBookDetailList;
    private static final String TAG = "BookDetailAdapter";
    private Context context;
    private boolean flagIntent = true;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView bookName;
        TextView author;
        TextView posetime;
        ImageView bookimg;
        ImageView nameheadImg;
        View bookView;


        public ViewHolder(View view) {
            super(view);
            bookView = view;
            username = (TextView) view.findViewById(R.id.book_detail_username);
            bookName = (TextView) view.findViewById(R.id.book_detail_bookname);
            author = (TextView) view.findViewById(R.id.book_detail_author);
            bookimg = (ImageView) view.findViewById(R.id.book_detail_pic);
            posetime = (TextView) view.findViewById(R.id.book_detail_posetime);
            nameheadImg = (ImageView) view.findViewById(R.id.book_detail_icon);
        }
    }

    public BookDetailAdapter(List<BookDetail> BookDetailList) {
        mBookDetailList = BookDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        if(flagIntent)
        holder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BookDetail bookDetail = mBookDetailList.get(position);
                Intent intent = new Intent(v.getContext(), ExchangeBookDetailsActivity.class);
                intent.putExtra("Username", bookDetail.getUsername());
                intent.putExtra("BookName", bookDetail.getBookName());
                intent.putExtra("Author", bookDetail.getAuthor());
                intent.putExtra("Press", bookDetail.getPress());
                intent.putExtra("RecommendedReason", bookDetail.getRecommendedReason());
                intent.putExtra("BookImageUrl", bookDetail.getBookImageUrl());
                intent.putExtra("userid", bookDetail.getUserid());
                intent.putExtra("nameheadUrl",bookDetail.getUserheadpath());
                intent.putExtra("bookid",bookDetail.getBookid());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookDetail bookdetail = mBookDetailList.get(position);
        holder.username.setText(bookdetail.getUsername());
        holder.bookName.setText(bookdetail.getBookName());
        holder.author.setText(bookdetail.getAuthor());
        holder.posetime.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(bookdetail.getPosetime())));
        Glide.with(context).load("http://120.24.217.191/Book/img/bookImg/" + bookdetail.getBookImageUrl()).into(holder.bookimg);
        Glide.with(context).load("http://120.24.217.191/Book/img/headImg/" + bookdetail.getUserheadpath()).error(R.drawable.icon).into(holder.nameheadImg);
    }

    @Override
    public int getItemCount() {
        return mBookDetailList.size();
    }

    public void  setIntent(boolean cmd)
    {
        flagIntent = cmd;
    }
}
