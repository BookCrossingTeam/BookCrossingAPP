package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SwappingActivity extends AppCompatActivity {

    private List<Swapping> swappinglist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swapping);
        initData();
        RecyclerView recyclerView = findViewById(R.id.swapping_list_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SwappingAdapter adapter = new SwappingAdapter(swappinglist);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Swapping swapping = new Swapping(R.drawable.book_image, "BOOKNAME1", R.drawable.book_image, "BOOKNAME2", 1, true);
            swappinglist.add(swapping);
            Swapping swapping2 = new Swapping(R.drawable.book_image, "Android Studio", R.drawable.book_image, "Visual Studio", 2, false);
            swappinglist.add(swapping2);
            Swapping swapping3 = new Swapping(R.drawable.book_image, "Eclipse", R.drawable.book_image, "Pycharm", 1, false);
            swappinglist.add(swapping3);
            Swapping swapping4 = new Swapping(R.drawable.book_image, "Codeblocks", R.drawable.book_image, "matlab", 2, true);
            swappinglist.add(swapping4);
        }
    }
}
