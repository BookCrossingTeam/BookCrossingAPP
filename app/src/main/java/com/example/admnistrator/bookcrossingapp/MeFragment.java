package com.example.admnistrator.bookcrossingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
/**
 * Created by yvemuki on 2018/3/13.
 */

public class MeFragment extends Fragment {
    public static final String ARGUMENT = "MeFragment";
    private String mArgument;
    private View view;
    private Button btnPosing;
    private Button btnSwaping;
    private Button btnReviews;
    private Button btnRecords;
    private Button btnSetting;
    private Button btnWanting;
    private TextView tvUsername;
    private String username;

    //private FriendAdapter adapter;
    //private List<Friend> friendList = new ArrayList<>();
    //private int REQUEST_CODE_SCAN = 111; //扫码

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
        SharedPreferences pref = getActivity().getSharedPreferences("my_user_info", MODE_PRIVATE);
        username = pref.getString("username", "");
        tvUsername.setText(username);
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
        tvUsername = view.findViewById(R.id.me_text_1);

        /*btnPosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                //也可以不传这个参数
                //不传的话  默认都为默认不震动  其他都为true
                ZxingConfig config = new ZxingConfig();
                config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
                config.setPlayBeep(true);//是否播放提示音
                config.setShake(true);//是否震动
                config.setShowAlbum(true);//是否显示相册
                config.setShowFlashLight(true);//是否显示闪光灯
                //如果不传 ZxingConfig的话，两行代码就能搞定了
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });*/
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
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
