package com.example.admnistrator.bookcrossingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity {

    private List<Records> recordslist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        initData();
        RecyclerView recyclerView = findViewById(R.id.records_list_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecordsAdapter adapter = new RecordsAdapter(recordslist);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Records records = new Records(R.drawable.book_image,"Android Studio",3,"exchangeuser:user1","from 2017.5.8 to 2017.12.31");
            recordslist.add(records);
            Records records2 = new Records(R.drawable.book_image,"matlab",3,"exchangeuser:user2","from 2017.12.11 to 2017.12.31");
            recordslist.add(records2);
            Records records3 = new Records(R.drawable.book_image,"BOOKNAME1",4,"exchangeuser:user3","from 2018.1.1 to 2018.2.1");
            recordslist.add(records3);

        }
    }
}
