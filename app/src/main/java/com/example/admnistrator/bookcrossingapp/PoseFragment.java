package com.example.admnistrator.bookcrossingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 57010 on 2017/5/2.
 */

public class PoseFragment extends Fragment {
    private String mArgument;
    private View view;

    private EditText bookName;
    private EditText author;
    private EditText press;
    private EditText recommendedReason;
    private Spinner classify;
    private ImageView pose_btn;
    private String bookNameValue, authorValue, pressValue, recommendedReasonValue, classifyValue;
    private String username;

    public static final String ARGUMENT = "argument";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.pose_layout, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("my_user_info", MODE_PRIVATE);
        username = pref.getString("username", "");
        initViewPager();
        return view;
    }

    public static PoseFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        PoseFragment poseFragment = new PoseFragment();
        poseFragment.setArguments(bundle);
        return poseFragment;
    }

    public void initViewPager() {
        bookName = (EditText) view.findViewById(R.id.edit_posing_shared_name);
        author = (EditText) view.findViewById(R.id.edit_posing_shared_author);
        press = (EditText) view.findViewById(R.id.edit_posing_shared_press);
        recommendedReason = (EditText) view.findViewById(R.id.edit_posing_shared_recommend);
        pose_btn = (ImageView) view.findViewById(R.id.btn_posing_shared);
        classify = view.findViewById(R.id.spinner_posing_shared);

        pose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookNameValue = bookName.getText().toString();
                authorValue = author.getText().toString();
                pressValue = press.getText().toString();
                recommendedReasonValue = recommendedReason.getText().toString();
                classifyValue = classify.getSelectedItem().toString();
                if (bookNameValue.equals("") || authorValue.equals("") || pressValue.equals("") || recommendedReasonValue.equals("")) {
                    Toast.makeText(getActivity(), "请填写完整", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (classifyValue.equals("请选择分类")) {
                    Toast.makeText(getActivity(), "请选择分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPose();
            }
        });


    }

    public void sendPose() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username", username).add("bookName", bookNameValue).add("author", authorValue).add("press", pressValue).add("recommendedReason", recommendedReasonValue).build();
                    Request request = new Request.Builder().url("http://120.24.217.191/Book/APP/sendPose").post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (responseData.equals("true")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "发表成功", Toast.LENGTH_SHORT).show();
                                bookName.setText("");
                                author.setText("");
                                press.setText("");
                                recommendedReason.setText("");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
