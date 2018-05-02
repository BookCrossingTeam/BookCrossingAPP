package com.example.administrator.bookcrossingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.SwappingActivity;
import com.example.administrator.bookcrossingapp.datamodel.Swapping;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/7.
 */

public class SwappingAdapter extends RecyclerView.Adapter<SwappingAdapter.ViewHolder> {

    private List<Swapping> mSwappingList;
    private Context context;
    private SwappingActivity mActivity;


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_pic1;
        TextView tv_bookname1;
        ImageView img_pic2;
        TextView tv_bookname2;
        TextView tv_now_state;
        Button btn_next_state;
        TextView tv_confirm;
        View swappingview;

        public ViewHolder(View view) {
            super(view);
            swappingview = view;
            img_pic1 = view.findViewById(R.id.swapping_pic1);
            tv_bookname1 = view.findViewById(R.id.swapping_bookname1);
            img_pic2 = view.findViewById(R.id.swapping_pic2);
            tv_bookname2 = view.findViewById(R.id.swapping_bookname2);
            tv_now_state = view.findViewById(R.id.swapping_now_state);
            btn_next_state = view.findViewById(R.id.swapping_btn_next_state);
            tv_confirm = view.findViewById(R.id.swapping_others_confirm);
        }
    }

    public SwappingAdapter(List<Swapping> SwappingList, Activity activity) {
        mActivity = (SwappingActivity)activity;
        mSwappingList = SwappingList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_swapping_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_next_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final Swapping swapping = mSwappingList.get(position);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取数据
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("id", swapping.getExchangeid() + "")
                                    .add("myuserid", swapping.getMyuserid() + "")
                                    .add("touserid", swapping.getTouserid() + "")
                                    .add("bookAId",swapping.getBookAid()+"")
                                    .add("bookBId",swapping.getBookBid()+"")
                                    .build();
                            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/exchange_update").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {

                                String responseData = response.body().string();

                                if (responseData.equals("OK"))
                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mActivity.initData();
                                        }
                                    });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SwappingAdapter.ViewHolder holder, int position) {
        Swapping swapping = mSwappingList.get(position);
        holder.img_pic1.setImageResource(swapping.getSwappingPic1());
        holder.img_pic2.setImageResource(swapping.getSwappingPic2());
        holder.tv_bookname1.setText(swapping.getSwappingBookname1());
        holder.tv_bookname2.setText(swapping.getSwappingBookname2());

        Glide.with(context).load("http://120.24.217.191/Book/img/bookImg/" + swapping.getSwappingBookURL1()).into(holder.img_pic1);
        Glide.with(context).load("http://120.24.217.191/Book/img/bookImg/" + swapping.getSwappingBookURL2()).into(holder.img_pic2);

        int now_state = swapping.getNow_state();
        if (now_state == 1) {
            holder.tv_now_state.setText("state：线上未确认");
            holder.btn_next_state.setText("等待对方确认");
            holder.btn_next_state.setEnabled(false);
            holder.tv_confirm.setText("对方线上未确认");
        }
        if (now_state == 2) {
            holder.tv_now_state.setText("state：线上确认完成");
            holder.btn_next_state.setText("线下确认");
            holder.tv_confirm.setText("对方线上已确认");
        }
        if (now_state == 3) {
            holder.tv_now_state.setText("state：线下未确认");
            holder.btn_next_state.setText("等待对方确认");
            holder.btn_next_state.setEnabled(false);
            holder.tv_confirm.setText("对方线下未确认");
        }
        if (now_state == 4) {
            holder.tv_now_state.setText("state：线下确认完成");
            holder.btn_next_state.setText("归还");
            holder.tv_confirm.setText("对方线下确认完成");
        }
        if (now_state == 5) {
            holder.tv_now_state.setText("state：归还未确认");
            holder.btn_next_state.setText("等待对方确认");
            holder.btn_next_state.setEnabled(false);
            holder.tv_confirm.setText("对方归还未确认");
        }
        if (now_state == 6) {
            holder.tv_now_state.setText("state：归还完成");
            holder.btn_next_state.setText("已完成");
            holder.btn_next_state.setEnabled(false);
        }
        if (now_state == 7) {
            holder.tv_now_state.setText("state：线上未确认");
            holder.btn_next_state.setText("线上确认");
            holder.tv_confirm.setText("对方请求交换");
        }
        if (now_state == 8) {
            holder.tv_now_state.setText("state：线上确认完成");
            holder.btn_next_state.setText("等待对方线下确认");
            holder.btn_next_state.setEnabled(false);
            holder.tv_confirm.setText("对方线下未确认");
        }
        if (now_state == 9) {
            holder.tv_now_state.setText("state：线下未确认");
            holder.btn_next_state.setText("线下确认");
            holder.tv_confirm.setText("对方线下确认完成");
        }
        if (now_state == 10) {
            holder.tv_now_state.setText("state：线下确认完成");
            holder.btn_next_state.setText("等待对方归还");
            holder.btn_next_state.setEnabled(false);
            holder.tv_confirm.setText("对方线下确认完成");
        }
        if (now_state == 11) {
            holder.tv_now_state.setText("state：归还未确认");
            holder.btn_next_state.setText("归还确认");
            holder.tv_confirm.setText("对方归还已确认");
        }
        if (now_state == 12) {
            holder.tv_now_state.setText("state：归还完成");
            holder.btn_next_state.setText("已完成");
            holder.btn_next_state.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mSwappingList.size();
    }
}
