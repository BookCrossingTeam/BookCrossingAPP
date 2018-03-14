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
 * Created by yvemuki on 2018/3/13.
 */

public class FriendAdapter extends RecyclerView.Adapter <FriendAdapter.ViewHolder>{
    private List<Friend> mFriendList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView friendPic;
        TextView friendName;
        View friendView; //用来设置整个item的点击事件

        public ViewHolder(View v){
            super(v);
            friendView = v;
            friendPic = (ImageView)v.findViewById(R.id.friend_item_pic);
            friendName = (TextView)v.findViewById(R.id.friend_item_name);
        }
    }
    public FriendAdapter(List<Friend> friendList){
        mFriendList = friendList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_item, parent,false );
        final ViewHolder holder = new ViewHolder(view);
        //为item设置点击事件
        holder.friendView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //暂时先不传递消息
//                int position = holder.getAdapterPosition();
//                Friend friend = mFriendList.get(position);
                Intent intent = new Intent(v.getContext(),FriendChatActivity.class);
                //这里会添加需要传递的消息
                view.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        holder.friendPic.setImageResource(friend.getFriendPic());
        holder.friendName.setText(friend.getFriendName());

    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
