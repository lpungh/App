package com.example.administrator.ekapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-14.
 */
public class ArticleModify extends AppCompatActivity  {
    private EditText etTitle;
    private EditText etId;
    private TextView tvWriter;
    private EditText etContent;
    private Button bnComplete;
    private Button bnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_modify);

        etTitle = (EditText) findViewById(R.id.article_modify_title);
        etId = (EditText) findViewById(R.id.article_modify_id);
        tvWriter = (TextView) findViewById(R.id.article_modify_writer);
        etContent = (EditText) findViewById(R.id.article_modify_content);

        bnComplete = (Button) findViewById(R.id.article_button_complete);
        bnCancel = (Button) findViewById(R.id.article_button_cancel);
        Intent intent = getIntent();
        final String articleNumber = intent.getStringExtra("num");
        final ConsultActivity consultActivity = new ConsultActivity(getApplicationContext());
        final Article article = consultActivity.getArticleByArticleNumber(Integer.parseInt(articleNumber));

        etTitle.setText(article.getTitle().toString());
        etId.setText(article.getId().toString());
        tvWriter.setText(article.getWriter().toString());
        etContent.setText(article.getContent().toString());

        bnComplete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    // db로부터 값 가져오기
                   consultActivity.modifyData(etTitle.getText().toString(), etId.getText().toString(), tvWriter.getText().toString(), etContent.getText().toString(), article.getArticleNumber());
                    finish();
                }catch(Exception e){
                    Log.e("test", "MODIFY JSON ERROR! - " + e);
                    e.printStackTrace();
                }
            }
        });

        bnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    } //end of onCreate
}
