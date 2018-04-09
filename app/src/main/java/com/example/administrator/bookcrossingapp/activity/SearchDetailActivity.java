package com.example.administrator.bookcrossingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.bookcrossingapp.R;

public class SearchDetailActivity extends AppCompatActivity {

    private View viewSearchTitle;
    private ImageButton btnSearch;
    private EditText editContent;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        initSearch();

    }

    private void initSearch() {
        viewSearchTitle = findViewById(R.id.search_title);
        btnSearch = viewSearchTitle.findViewById(R.id.btnSearch);
        editContent = viewSearchTitle.findViewById(R.id.editContent);
        Intent intent = getIntent();
        content = intent.getStringExtra("searchContent");
        editContent.setText(content);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = editContent.getText().toString();
                if (content.equals("") ) {
                    Toast.makeText(SearchDetailActivity.this, "搜索内容不能为空白", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SearchDetailActivity.this, SearchDetailActivity.class);
                intent.putExtra("searchContent", content);
                startActivityForResult(intent, 1);
                finish();
            }
        });
    }

    //回传搜索值
    @Override
    public void onBackPressed() {
        content = editContent.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("searchContent",content);
        setResult(RESULT_OK,intent);
        finish();
    }
}
