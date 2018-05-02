package com.example.administrator.bookcrossingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.MessageManagement;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.UserDetailActivity;
import com.example.administrator.bookcrossingapp.datamodel.Msg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yvemuki on 2018/3/12.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        TextView leftMsgTime;
        TextView rightMsgTime;
        ImageView leftImg;
        ImageView rightImg;

        public ViewHolder(View view){
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            leftMsgTime = (TextView) view.findViewById(R.id.left_msg_time);
            rightMsgTime = (TextView) view.findViewById(R.id.right_msg_time);
            leftImg = (ImageView) view.findViewById(R.id.friend_item_people1);
            rightImg = (ImageView)view.findViewById(R.id.friend_item_people2);
        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        //可以理解为虽然在构造函数时候已经获取了view中的组件元素，但还要重新构造一次，利用msg_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_chat_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Msg msg = mMsgList.get(position);
                Intent intent = new Intent(view.getContext(), UserDetailActivity.class);
                intent.putExtra("username", msg.getUsername());
                intent.putExtra("userid", msg.getUserid());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if(msg.getType() == Msg.TYPE_RECEIVED){
            //如果是收到的消息，则显示左边的消息布局,将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            holder.leftMsgTime.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(msg.getTime())));
            Glide.with(context).load("http://120.24.217.191/Book/img/headImg/" + msg.getUserheadImgPath()).error(R.drawable.icon).into(holder.leftImg);
        }else if(msg.getType() == Msg.TYPE_SENT){
            //如果是发送的情况
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
            holder.rightMsgTime.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(msg.getTime())));
            Glide.with(context).load("http://120.24.217.191/Book/img/headImg/" + MessageManagement.getInstance(context).getMyheadImgPath()).error(R.drawable.icon).into(holder.rightImg);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
