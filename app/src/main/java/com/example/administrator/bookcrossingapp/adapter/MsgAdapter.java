package com.example.administrator.bookcrossingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.bookcrossingapp.datamodel.Msg;
import com.example.administrator.bookcrossingapp.R;

import java.util.List;

/**
 * Created by yvemuki on 2018/3/12.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View view){
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //可以理解为虽然在构造函数时候已经获取了view中的组件元素，但还要重新构造一次，利用msg_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_friend_chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if(msg.getType() == Msg.TYPE_RECEIVED){
            //如果是收到的消息，则显示左边的消息布局,将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else if(msg.getType() == Msg.TYPE_SENT){
            //如果是发送的情况
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
