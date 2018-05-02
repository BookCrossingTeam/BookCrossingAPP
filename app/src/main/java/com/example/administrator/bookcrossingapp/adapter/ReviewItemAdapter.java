package com.example.administrator.bookcrossingapp.adapter;

import android.app.Activity;
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
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.example.administrator.bookcrossingapp.datamodel.ReviewItem;
import com.example.administrator.bookcrossingapp.activity.ReviewsDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class ReviewItemAdapter  extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder>{
    private List<ReviewItem> mReviewList;
    private static final String TAG = "ReviewItemAdapter";
    private Context context;
    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author;
        ImageView img_cover;
        View reviewView;

        public ViewHolder(View view) {
            super(view);
            reviewView = view;
            tv_title = view.findViewById(R.id.review_item_title);
            tv_author = view.findViewById(R.id.review_item_author);
            img_cover = view.findViewById(R.id.review_item_cover);
        }
    }

    public ReviewItemAdapter(List<ReviewItem> reviewItemList) {
        mReviewList = reviewItemList;
    }

    public ReviewItemAdapter(List<ReviewItem> reviewItemList, Activity activity){
        mActivity = activity;
        mReviewList = reviewItemList;
    }

    @Override
    public ReviewItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext(); //这里传递进来parent
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        final ReviewItemAdapter.ViewHolder holder = new ReviewItemAdapter.ViewHolder(view);

        holder.reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击事件
                int position = holder.getAdapterPosition();
                ReviewItem reviewItem = mReviewList.get(position);
                Intent intent = new Intent(v.getContext(),ReviewsDetailActivity.class);
                intent.putExtra("articleId", reviewItem.getArticleId());
                intent.putExtra("cover",reviewItem.getCoverImgUrl());
                intent.putExtra("title",reviewItem.getTitle());
                intent.putExtra("author",reviewItem.getAuthor());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewItemAdapter.ViewHolder holder, int position) {
        ReviewItem reviewItem = mReviewList.get(position);
        holder.tv_author.setText(reviewItem.getAuthor());
        holder.tv_title.setText(reviewItem.getTitle());
        Glide.with(context).load("http://120.24.217.191/Book/img/bookImg/" + reviewItem.getCoverImgUrl()).into(holder.img_cover);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}