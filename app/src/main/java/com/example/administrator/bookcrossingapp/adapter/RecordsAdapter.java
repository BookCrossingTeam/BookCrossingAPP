package com.example.administrator.bookcrossingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.Records;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private List<Records> mRecordsList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_pic;
        TextView tv_bookname;
        TextView tv_state;
        TextView tv_exchangeuser;
        TextView tv_time;

        public ViewHolder(View view) {
            super(view);
            img_pic = view.findViewById(R.id.records_pic);
            tv_bookname = view.findViewById(R.id.records_bookname);
            tv_state = view.findViewById(R.id.records_state);
            tv_exchangeuser = view.findViewById(R.id.records_exchange);
            tv_time = view.findViewById(R.id.records_time);
        }
    }

    public RecordsAdapter(List<Records> RecordsList) {
        mRecordsList = RecordsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        //照书上的打会只有一条，改为null，原因可能是版本23以上的问题
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_records_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordsAdapter.ViewHolder holder, int position) {
        Records records = mRecordsList.get(position);

        holder.tv_bookname.setText(records.getRecordsBookname());
        holder.tv_exchangeuser.setText("from: " + records.getRecordsExchangeUser());
        holder.tv_state.setText("已归还");
        String disTime = "From:" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(records.getStartTime())) + " To:" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(records.getRecordsTime()));
        holder.tv_time.setText(disTime);
        Glide.with(context).load("http://120.24.217.191/Book/img/bookImg/" + records.getRecordsBooknameURL()).into(holder.img_pic);

    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }
}
