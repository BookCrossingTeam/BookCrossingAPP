package com.example.administrator.bookcrossingapp.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.ReviewsEditActivity;
import com.example.administrator.bookcrossingapp.datamodel.ReviewItem;
import com.example.administrator.bookcrossingapp.adapter.ReviewItemAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author Lyh
 */
public class UserdetailReviewsFragment extends Fragment {
    private View view;
    private ReviewItemAdapter adapter;
    private int userid;
    private List<ReviewItem> reviewlist = new ArrayList<>();
    private Button btn_write;

    public UserdetailReviewsFragment() {
        super();
    }

    //创建newInstance方法的方法来向fragment传递参数(注意这个方法是属于类方法）
    public static final UserdetailReviewsFragment newInstance(int userid){
        UserdetailReviewsFragment fragment = new UserdetailReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userid",userid);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid =getArguments().getInt("userid");
        initReviewList();

    }

    private void initReviewList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送请求
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("userid", userid+"").build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/reviewOwn").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    Log.i("testtttttttttttt",response.toString());
                    if(response.isSuccessful()){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "成功连接服务器！", Toast.LENGTH_SHORT).show();
                            }
                        });
                        String responseData = response.body().string();
                        handleResponseData(responseData);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "服务器开小差啦", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        }).start();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reviews, container, false);
        initReviewListRecyclerView();

        return view;
    }

    private void initReviewListRecyclerView() {
        RecyclerView recyclerView = view.findViewById(R.id.reviews_article_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReviewItemAdapter(reviewlist, getContext(), getActivity());  //更改了，传入了context相当于
        recyclerView.setAdapter(adapter);

        //btn_write = view.findViewById(R.id.reviews_write);//可发表
        //btn_write.setVisibility(View.GONE);
//        btn_write.setOnClickListener(new View.OnClickListener() {
//            @Override
//            //点击发表文章
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),ReviewsEditActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    public void handleResponseData(final String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = jsonArray.length()-1; i >= 0; i--) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int articleId = Integer.parseInt(jsonObject.getString("id"));
                String title = jsonObject.getString("title");
                String author = jsonObject.getString("username");
                String coverImgUrl = jsonObject.getString("coverImgUrl");
                int likeAmount = Integer.parseInt(jsonObject.getString("likeAmount"));
                int isLike;
                if( jsonObject.getString("userId").equals("isNotLike")){
                    //表明该item没有被该用户点赞(注意，java的字符串比较是equals函数！)
                    isLike = 0;
                }
                else isLike = 1;

                ReviewItem reviewItem = new ReviewItem();
                reviewItem.setArticleId(articleId);
                reviewItem.setTitle(title);
                reviewItem.setAuthor(author);
                reviewItem.setCoverImgUrl(coverImgUrl);
                reviewItem.setLikeAmount(likeAmount);
                reviewItem.setIsLike(isLike);

                reviewlist.add(reviewItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
