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
import com.example.administrator.bookcrossingapp.activity.FriendChatActivity;
import com.example.administrator.bookcrossingapp.datamodel.Friend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> mFriendList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView friendPic;
        TextView friendName;
        TextView friendRead;
        TextView friendTime;
        View friendView; //用来设置整个item的点击事件

        public ViewHolder(View v) {
            super(v);
            friendView = v;
            friendPic = (ImageView) v.findViewById(R.id.friend_item_pic);
            friendName = (TextView) v.findViewById(R.id.friend_item_name);
            friendRead = (TextView) v.findViewById(R.id.friendRead);
            friendTime = (TextView) v.findViewById(R.id.friendTime);
        }
    }

    public FriendAdapter(List<Friend> friendList) {
        mFriendList = friendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //为item设置点击事件
        holder.friendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂时先不传递消息
               int position = holder.getAdapterPosition();
                Friend friend = mFriendList.get(position);
                Intent intent = new Intent(v.getContext(), FriendChatActivity.class);
                intent.putExtra("userid",friend.getUserid());
                //这里会添加需要传递的消息
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        if(friend.getIsread()==1)
            holder.friendRead.setVisibility(View.GONE);
        else
            holder.friendRead.setVisibility(View.VISIBLE);
        //holder.friendPic.setImageResource(R.drawable.friend_list_icon);
        holder.friendName.setText(friend.getFriendName());
        holder.friendTime.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(friend.getTime())));
        Glide.with(context).load("http://120.24.217.191/Book/img/headImg/" + friend.getFriendheadImgURL()).error(R.drawable.icon).into(holder.friendPic);
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
