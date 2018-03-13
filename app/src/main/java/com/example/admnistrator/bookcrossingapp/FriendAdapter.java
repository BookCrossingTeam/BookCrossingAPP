package com.example.admnistrator.bookcrossingapp;

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

        public ViewHolder(View v){
            super(v);
            friendPic = (ImageView)v.findViewById(R.id.friend_item_pic);
            friendName = (TextView)v.findViewById(R.id.friend_item_name);
        }
    }
    public FriendAdapter(List<Friend> friendList){
        mFriendList = friendList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_item, parent,false );
        ViewHolder holder = new ViewHolder(view);
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
