package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.MessageManagement;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.MsgAdapter;
import com.example.administrator.bookcrossingapp.datamodel.Msg;
import com.example.administrator.bookcrossingapp.service.PollingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yvemuki on 2018/3/13.
 */

public class FriendChatActivity extends AppCompatActivity {
    private static final String TAG = "FriendChatActivity";
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button exchangeBtn;
    private Button send;
    private ImageView book_right;
    private ImageView book_left;
    private int book_right_id;
    private int book_left_id;
    private ImageView exchange_pose;
    private String book_right_url;
    private String book_left_url;
    private RecyclerView msgRecyclerView;
    private LinearLayoutManager recyclerViewlayoutManager;
    private MsgAdapter adapter;
    //点击事件时候需要变化布局
    private LinearLayout layout_exchange;
    private LinearLayout layout_exchange_change;

    private Handler handler;
    private Runnable runnable;

    private int userid;
    private int layout_exchange_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);

        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", 0);
        book_left_id = intent.getIntExtra("bookid", 0);
        book_left_url = intent.getStringExtra("bookimg");

        initMsgs(); //初始化消息数据

        //根据id获取控件布局实例
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        exchangeBtn = (Button) findViewById(R.id.button_exchange);
        book_right = (ImageView) findViewById(R.id.exchange_book_right);
        book_left = (ImageView) findViewById(R.id.exchange_book_left);
        layout_exchange = (LinearLayout) findViewById(R.id.layout_chat_exchange);
        layout_exchange_change = (LinearLayout) findViewById(R.id.layout_chat_exchange_change);
        exchange_pose = (ImageView) findViewById(R.id.exchange_pose);

        recyclerViewlayoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(recyclerViewlayoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        msgRecyclerView.scrollToPosition(msgList.size() - 1);


        if (book_left_id != 0) {
            Glide.with(FriendChatActivity.this).load("http://120.24.217.191/Book/img/bookImg/" + book_left_url).into(book_left);
        }

        //为send注册点击事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send.setEnabled(false);
                final String content = inputText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!"".equals(content)) {
                                //如果输入框内容不为空
                                if (MessageManagement.getInstance(FriendChatActivity.this).sendMsg(userid, content)) {
                                    refreshMsg();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
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
                        } catch (Exception e) {
                            e.getStackTrace();
                        } finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    send.setEnabled(true);
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        //为点击exchange按钮设置点击事件
        layout_exchange_height = ((LinearLayout.LayoutParams) layout_exchange.getLayoutParams()).height;
        exchangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) layout_exchange.getLayoutParams();
                if (layout_exchange_change.getVisibility() == View.GONE) {
                    linearParams.height = 500;
                    layout_exchange_change.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "exchange your book", Toast.LENGTH_SHORT).show();
                } else {
                    linearParams.height = layout_exchange_height;
                    layout_exchange_change.setVisibility(View.GONE);
                }
            }
        });

        book_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendChatActivity.this, ShareListActivity.class);
                intent.putExtra("userid", userid);
                intent.putExtra("flag", 1);
                startActivityForResult(intent, 1);
            }
        });

        //为添加按钮添加点击事件
        book_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendChatActivity.this, ShareListActivity.class);
                intent.putExtra("userid", MessageManagement.getInstance(FriendChatActivity.this).getMyuserid());
                intent.putExtra("flag", 1);
                startActivityForResult(intent, 2);
            }
        });

        exchange_pose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchange_pose.setEnabled(false);

                if (book_left_id == 0 || book_right_id == 0) {
                    Toast.makeText(FriendChatActivity.this, "请选择双方的书", Toast.LENGTH_SHORT).show();
                    exchange_pose.setEnabled(true);
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取数据
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder().add("userAId", MessageManagement.getInstance(FriendChatActivity.this).getMyuserid() + "")
                                    .add("userBId", userid + "")
                                    .add("bookAId", book_right_id + "")
                                    .add("bookBId", book_left_id + "")
                                    .build();
                            Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/exchange").post(requestBody).build();
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                final String responseData = response.body().string();
                                if (responseData.equals("OK")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            layout_exchange_change.setVisibility(View.GONE);
                                            book_left_id = 0;
                                            book_right_id = 0;
                                            Glide.with(FriendChatActivity.this).load(R.drawable.friend_chat_exchange_add).into(book_left);
                                            Glide.with(FriendChatActivity.this).load(R.drawable.friend_chat_exchange_add).into(book_right);
                                            exchange_pose.setEnabled(false);
                                            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) layout_exchange.getLayoutParams();
                                            linearParams.height = layout_exchange_height;

                                            refreshMsg();
                                        }
                                    });
                                } else if(!responseData.equals("")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(FriendChatActivity.this, responseData, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                Objects.requireNonNull(FriendChatActivity.this).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(FriendChatActivity.this, "服务器开小差啦", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    exchange_pose.setEnabled(true);
                                }
                            });
                        }
                    }
                }).start();
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
                handler.postDelayed(runnable, 5*1000);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            book_left_url = data.getStringExtra("BookImageUrl");
            book_left_id = data.getIntExtra("bookid", 0);
            Glide.with(FriendChatActivity.this).load("http://120.24.217.191/Book/img/bookImg/" + book_left_url).into(book_left);
        }
        if (requestCode == 2 && resultCode == 1) {
            book_right_url = data.getStringExtra("BookImageUrl");
            book_right_id = data.getIntExtra("bookid", 0);
            Glide.with(FriendChatActivity.this).load("http://120.24.217.191/Book/img/bookImg/" + book_right_url).into(book_right);
        }
    }

    private void initMsgs() {
        MessageManagement.getInstance(FriendChatActivity.this).initMsglist(userid, msgList);
        Log.i(TAG, "initMsgs: " + msgList.size());
    }

    public void refreshMsg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageManagement.getInstance(FriendChatActivity.this).getMsgFromRemote();
                if (PollingService.getNewNum() > -1)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "refreshMsg: ");
                            int oldlistsize = msgList.size();
                            MessageManagement.getInstance(FriendChatActivity.this).initMsglist(userid, msgList);
                            //当有新的数据插入是时，会刷新RecyclerView显示
                            adapter.notifyDataSetChanged(); //重新获取最新数据list位置
                            if (recyclerViewlayoutManager.findLastVisibleItemPosition() == oldlistsize - 1)
                                //将RecyclerView定位到最后一行(相当于保证聊天界面会定位到最新的地方)
                                msgRecyclerView.smoothScrollToPosition(msgList.size() - 1);
                        }
                    });
                PollingService.setNewNum(0);
            }
        }).start();

    }
}
