package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class FriendChatActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);
        initMsgs(); //初始化消息数据
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        //为send注册点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    //如果输入框内容不为空
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg); //相当于将新添加的内容加到数据list中
                    //当有新的数据插入是时，会刷新RecyclerView显示
                    adapter.notifyItemInserted(msgList.size() - 1); //重新获取最新数据list位置
                    //将RecyclerView定位到最后一行(相当于保证聊天界面会定位到最新的地方)
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText(""); //清空输入框内容

                }
            }
        });
    }

    private void initMsgs(){
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is That?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice to meet you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
