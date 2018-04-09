package com.example.administrator.bookcrossingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.Records;

import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ViewHolder> {

    private List<Records> mRecordsList;


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
    public RecordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //照书上的打会只有一条，改为null，原因可能是版本23以上的问题
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_records_item, null);
        RecordsAdapter.ViewHolder holder = new RecordsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordsAdapter.ViewHolder holder, int position) {
        Records records = mRecordsList.get(position);
        holder.img_pic.setImageResource(records.getRecordsPic());
        holder.tv_bookname.setText(records.getRecordsBookname());
        holder.tv_exchangeuser.setText(records.getRecordsExchangeUser());
        int state = records.getRecordState();
        if (state == 3)
            holder.tv_state.setText("已归还");
        else if (state == 4)
            holder.tv_state.setText("已超期失效");
        holder.tv_time.setText(records.getRecordsTime());


    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }
}
