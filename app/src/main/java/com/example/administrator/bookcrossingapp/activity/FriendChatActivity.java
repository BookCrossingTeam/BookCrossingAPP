package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.MessageManagement;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.MsgAdapter;
import com.example.administrator.bookcrossingapp.datamodel.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class FriendChatActivity extends AppCompatActivity {
    private static final String TAG = "FriendChatActivity";
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button exchangeBtn;
    private Button send;
    private Button addBtn;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    //点击事件时候需要变化布局
    private LinearLayout layout_exchange;
    private LinearLayout layout_exchange_change;

    private Handler handler;
    private Runnable runnable;

    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);
        initMsgs(); //初始化消息数据

        //根据id获取控件布局实例
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        exchangeBtn = (Button) findViewById(R.id.button_exchange);
        addBtn = (Button) findViewById(R.id.exchange_book_right);
        layout_exchange = (LinearLayout) findViewById(R.id.layout_chat_exchange);
        layout_exchange_change = (LinearLayout) findViewById(R.id.layout_chat_exchange_change);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        msgRecyclerView.scrollToPosition(msgList.size() - 1);

        //为send注册点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = inputText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!"".equals(content)) {
                            //如果输入框内容不为空
                            if (MessageManagement.getInstance(FriendChatActivity.this).sendMsg(userid, content)) {
                                Msg msg = new Msg(content, Msg.TYPE_SENT);
                                msg.setTime(System.currentTimeMillis());
                                msgList.add(msg); //相当于将新添加的内容加到数据list中
                                //当有新的数据插入是时，会刷新RecyclerView显示
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyItemInserted(msgList.size() - 1); //重新获取最新数据list位置
                                        //将RecyclerView定位到最后一行(相当于保证聊天界面会定位到最新的地方)
                                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                                        inputText.setText(""); //清空输入框内容}
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "网络开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });

        //为点击exchange按钮设置点击事件
        exchangeBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) layout_exchange.getLayoutParams();
                linearParams.height = 400;
                layout_exchange_change.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "exchange your book", Toast.LENGTH_SHORT).show();

            }
        });

        //为添加按钮添加点击事件
        addBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendChatActivity.this, ShareListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                refreshMsg();
                handler.postDelayed(runnable, 10000);
            }
        };
        handler.postDelayed(runnable, 10000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void initMsgs() {
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        MessageManagement.getInstance(FriendChatActivity.this).initMsglist(userid, msgList);
        Log.i(TAG, "initMsgs: " + msgList.size());
    }

    public void refreshMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageManagement.getInstance(FriendChatActivity.this).getMsgFromRemote();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "refreshMsg: ");
                        MessageManagement.getInstance(FriendChatActivity.this).initMsglist(userid, msgList);

                        //当有新的数据插入是时，会刷新RecyclerView显示
                        adapter.notifyDataSetChanged(); //重新获取最新数据list位置
                        //将RecyclerView定位到最后一行(相当于保证聊天界面会定位到最新的地方)
                        msgRecyclerView.smoothScrollToPosition(msgList.size() - 1);
                    }
                });
            }
        }).start();

    }
}
