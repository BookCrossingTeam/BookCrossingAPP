package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.adapter.BookDetailAdapter;
import com.example.administrator.bookcrossingapp.datamodel.BookDetail;
import com.example.administrator.bookcrossingapp.datamodel.DoubanSearch;
import com.example.administrator.bookcrossingapp.datamodel.Search;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchDetailActivity extends AppCompatActivity {

    private static final String TAG = "SearchDetailActivity";

    private View viewSearchTitle;
    private ImageButton btnSearch;
    private EditText editContent;
    private String content;
    private int type;
    private ProgressBar progressBar;
    private TextView empty_msg;

    private BookDetailAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private List<BookDetail> search_result_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", Search.REMOTE);
        content = intent.getStringExtra("searchContent");

        RecyclerView recyclerView = findViewById(R.id.search_result_recyclerView);
        empty_msg = findViewById(R.id.search_result_empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookDetailAdapter(search_result_list,SearchDetailActivity.this);
        if (type == Search.DOUBAN)
            adapter.setIntent(3);
        recyclerView.setAdapter(adapter);
        progressBar = findViewById(R.id.searching);

        initSearch();

        if (type == Search.DOUBAN)
            initDataFromDouban();

        if (type == Search.REMOTE)
            initDataFromRemote();
    }

    private void initSearch() {
        viewSearchTitle = findViewById(R.id.search_title);
        btnSearch = viewSearchTitle.findViewById(R.id.btnSearch);
        editContent = viewSearchTitle.findViewById(R.id.editContent);
        editContent.setText(content);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = editContent.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(SearchDetailActivity.this, "搜索内容不能为空白", Toast.LENGTH_SHORT).show();
                    return;
                }
                search_result_list.clear();
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);
                if (type == Search.DOUBAN)
                    initDataFromDouban();

                if (type == Search.REMOTE)
                    initDataFromRemote();
            }
        });
    }

    //回传搜索值
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        content = editContent.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("searchContent", content);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void initDataFromDouban() {
        if (content.equals("")) {
            Toast.makeText(SearchDetailActivity.this, "搜索内容不能为空白", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ;// do somethings
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("https://api.douban.com/v2/book/search?count=100&q=" + content).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "run: " + responseData);
                    final DoubanSearch resultJson = new Gson().fromJson(responseData, DoubanSearch.class);
                    List<DoubanSearch.Book> bookList = resultJson.getBooks();
                    for (DoubanSearch.Book book : bookList) {
                        BookDetail bookDetail = new BookDetail();
                        bookDetail.setBookName(book.getTitle());
                        if (!book.getAuthor().isEmpty())
                            bookDetail.setAuthor(book.getAuthor().get(0));
                        bookDetail.setBookImageUrl(book.getImage());
                        bookDetail.setPress(book.getPublisher());
                        search_result_list.add(bookDetail);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initDataFromRemote() {
        if (content.equals("")) {
            Toast.makeText(SearchDetailActivity.this, "搜索内容不能为空白", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ;// do somethings
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("content", content).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/bookSearch").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "run: " + responseData);
                    final JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String bookName = jsonObject.getString("bookName");
                        String author = jsonObject.getString("author");
                        String press = jsonObject.getString("publish");
                        String imgUrl = jsonObject.getString("imgUrl");
                        long posetime = Long.parseLong(jsonObject.getString("poseTime"));
                        int userId = Integer.parseInt(jsonObject.getString("userId"));
                        String username = jsonObject.getString("username");
                        int bookId = Integer.parseInt(jsonObject.getString("id"));
                        String userheadpath = jsonObject.getString("headImgPath");
                        int bookType = Integer.parseInt(jsonObject.getString("bookType"));
                        String recommendedReason = jsonObject.getString("reason");
                        int exchangeState = Integer.parseInt(jsonObject.getString("exchangeState"));

                        BookDetail book = new BookDetail();
                        book.setBookid(bookId);
                        book.setUsername(username);
                        book.setBookName(bookName);
                        book.setAuthor(author);
                        book.setPress(press);
                        book.setRecommendedReason("");
                        book.setBookImageUrl(imgUrl);
                        book.setPosetime(posetime);
                        book.setUserid(userId);
                        book.setUserheadpath(userheadpath);
                        book.setBookType(bookType);
                        book.setRecommendedReason(recommendedReason);
                        book.setExchangeState(exchangeState);
                        search_result_list.add(book);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            if(jsonArray.length()==0)
                                empty_msg.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).

                start();
    }
}
