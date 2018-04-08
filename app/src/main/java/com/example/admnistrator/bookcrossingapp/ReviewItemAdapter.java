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
 * Created by Administrator on 2018/4/8.
 */

public class ReviewItemAdapter  extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder>{
    private List<ReviewItem> mReviewList;
    private static final String TAG = "ReviewItemAdapter";

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

    @Override
    public ReviewItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        final ReviewItemAdapter.ViewHolder holder = new ReviewItemAdapter.ViewHolder(view);
        holder.reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ReviewItem reviewItem = mReviewList.get(position);
                Intent intent = new Intent(v.getContext(),AgreementActivity.class);
                /*intent.putExtra("Username",bookDetail.getUsername());
                intent.putExtra("BookName",bookDetail.getBookName());
                intent.putExtra("Author",bookDetail.getAuthor());
                intent.putExtra("Press",bookDetail.getPress());
                intent.putExtra("RecommendedReason",bookDetail.getRecommendedReason());*/
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
        holder.img_cover.setImageResource(reviewItem.getCover());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}