package com.example.admnistrator.bookcrossingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        return holder;
        /*
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_swapping_item, parent, false);
        //ViewHolder holder = new ViewHolder(view);
        final SwappingAdapter.ViewHolder holder = new SwappingAdapter.ViewHolder(view);
        holder.swappingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Swapping Swapping = mSwappingList.get(position);
                Intent intent = new Intent(v.getContext(),ExchangeBookDetailsActivity.class);
                /*
                intent.putExtra("Username",Swapping.getUsername());
                intent.putExtra("BookName",Swapping.getBookName());
                intent.putExtra("Author",Swapping.getAuthor());
                intent.putExtra("Press",Swapping.getPress());
                intent.putExtra("RecommendedReason",Swapping.getRecommendedReason());

                view.getContext().startActivity(intent);
            }
        });
        return holder;*/
    }

    @Override
    public void onBindViewHolder(SwappingAdapter.ViewHolder holder, int position) {
        Swapping Swapping = mSwappingList.get(position);
        holder.img_pic1.setImageResource(Swapping.getSwappingPic1());
        holder.img_pic2.setImageResource(Swapping.getSwappingPic2());
        holder.tv_bookname1.setText(Swapping.getSwappingBookname1());
        holder.tv_bookname2.setText(Swapping.getSwappingBookname2());
        int now_state = Swapping.getNow_state();
        if (now_state == 1) {
            holder.tv_now_state.setText("state：线上确认完成");
            holder.btn_next_state.setText("线下确认");
        } else if (now_state == 2) {
            holder.tv_now_state.setText("state：线下确认完成");
            holder.btn_next_state.setText("归还");
        }

        boolean confirm_state = Swapping.isConfirm();
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
