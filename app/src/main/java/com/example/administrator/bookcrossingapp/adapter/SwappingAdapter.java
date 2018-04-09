package com.example.administrator.bookcrossingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.datamodel.Swapping;

import java.util.List;

/**
 * Created by Administrator on 2018/4/7.
 */

public class SwappingAdapter extends RecyclerView.Adapter<SwappingAdapter.ViewHolder> {

    private List<Swapping> mSwappingList;


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_pic1;
        TextView tv_bookname1;
        ImageView img_pic2;
        TextView tv_bookname2;
        TextView tv_now_state;
        Button btn_next_state;
        TextView tv_confirm;

        public ViewHolder(View view) {
            super(view);
            img_pic1 = view.findViewById(R.id.swapping_pic1);
            tv_bookname1 = view.findViewById(R.id.swapping_bookname1);
            img_pic2 = view.findViewById(R.id.swapping_pic2);
            tv_bookname2 = view.findViewById(R.id.swapping_bookname2);
            tv_now_state = view.findViewById(R.id.swapping_now_state);
            btn_next_state = view.findViewById(R.id.swapping_btn_next_state);
            tv_confirm = view.findViewById(R.id.swapping_others_confirm);
        }
    }

    public SwappingAdapter(List<Swapping> SwappingList) {
        mSwappingList = SwappingList;
    }


    @Override
    public SwappingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //照书上的打会只有一条，改为null，原因可能是版本23以上的问题
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_swapping_item, null);
        ViewHolder holder = new ViewHolder(view);
        holder.btn_next_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击事件待实现", Toast.LENGTH_SHORT).show();

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
        int now_state = swapping.getNow_state();
        if (now_state == 1) {
            holder.tv_now_state.setText("state：线上确认完成");
            holder.btn_next_state.setText("线下确认");
        } else if (now_state == 2) {
            holder.tv_now_state.setText("state：线下确认完成");
            holder.btn_next_state.setText("归还");
        }

        boolean confirm_state = swapping.isConfirm();
        if (confirm_state)
            holder.tv_confirm.setText("对方已确认");
        else
            holder.tv_confirm.setText("对方未确认");
    }

    @Override
    public int getItemCount() {
        return mSwappingList.size();
    }
}
