package com.example.administrator.bookcrossingapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.bookcrossingapp.R;
import com.example.administrator.bookcrossingapp.activity.RecordsActivity;
import com.example.administrator.bookcrossingapp.activity.ReviewsActivity;
import com.example.administrator.bookcrossingapp.activity.SettingActivity;
import com.example.administrator.bookcrossingapp.activity.ShareListActivity;
import com.example.administrator.bookcrossingapp.activity.SwappingActivity;
import com.example.administrator.bookcrossingapp.activity.WantListActivity;

import static android.content.Context.MODE_PRIVATE;
/**
 * Created by yvemuki on 2018/3/13.
 */

public class MeFragment extends Fragment {
    public static final String ARGUMENT = "MeFragment";
    private View view;
    private Button btnPosing;
    private Button btnSwaping;
    private Button btnReviews;
    private Button btnRecords;
    private Button btnSetting;
    private Button btnWanting;
    private TextView tvUsername;
    private String username;
    private ImageView headImg;
    private String headImgUrl;

    public MeFragment(){
        super();
        //initMe();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_me, container, false);
        //initMeRecyclerView();
        initButton();
        initData();
        return view;
    }

    //初始化用户名
    private void initData() {
        //显示用户名
        SharedPreferences pref = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        username = pref.getString("username", "");
        headImgUrl = pref.getString("headImgPath","");
        tvUsername.setText(username);
        Glide.with(getActivity()).load("http://120.24.217.191/Book/img/headImg/"+headImgUrl).error(R.drawable.me_icon_person).into(headImg);
        /////时间、头像、书籍待完善
    }

    //初始化控件
    private void initButton() {
        btnPosing = view.findViewById(R.id.me_button_posing);
        btnSwaping = view.findViewById(R.id.me_button_swaping);
        btnReviews =  view.findViewById(R.id.me_button_reviews);
        btnRecords = view.findViewById(R.id.me_button_records);
        btnSetting = view.findViewById(R.id.me_button_settings);
        btnWanting = view.findViewById(R.id.me_button_wanting);
        headImg = view.findViewById(R.id.me_icon_personIcon);
        tvUsername = view.findViewById(R.id.me_text_1);

        btnPosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShareListActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        btnWanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WantListActivity.class);
                startActivity(intent);
            }
        });
        btnSwaping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SwappingActivity.class);
                startActivity(intent);
            }
        });
        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReviewsActivity.class);
                startActivity(intent);
            }
        });
        btnRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecordsActivity.class);
                startActivity(intent);
            }
        });
    }

    public static MeFragment newInstance(String from) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, from);
        MeFragment meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        return meFragment;
    }

    /*扫码
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == -1) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getActivity(), "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
            }
        }
    }*/



//    private void initMe(){
//        for (int i = 0; i < 3; i++){
//            Friend friend1 = new Friend(R.drawable.friend_list_icon, "Yvettemuki");
//            friendList.add(friend1);
//            Friend friend2 = new Friend(R.drawable.friend_list_icon,"Angelababy");
//        }
//    }

//    public void initFriendsRecyclerView(){
//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.friend_list_recyclerView);
//        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(LayoutManager);
//        adapter = new FriendAdapter(friendList);
//        recyclerView.setAdapter(adapter);
//    }
}
