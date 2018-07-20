package com.example.administrator.ekapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017-07-04.
 */

public class ArticleViewer extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_viewer);

        TextView tvTitle = (TextView)findViewById(R.id.article_viewer_title);
        TextView tvDeadLine = (TextView)findViewById(R.id.article_viewer_deadline);
        TextView tvName = (TextView)findViewById(R.id.article_viewer_name);
        TextView tvDate = (TextView)findViewById(R.id.article_viewer_date);
        TextView tvContent = (TextView)findViewById(R.id.article_viewer_content);

 //       ImageView ivImage = (ImageView)findViewById(R.id.article_viewer_image);

        Button bnCancle = (Button)findViewById(R.id.article_button_cancel);
        Button bnModify = (Button)findViewById(R.id.article_button_modify);
        Button bnDelete = (Button)findViewById(R.id.article_button_delete);

        final String articleNumber = getIntent().getExtras().getString("ArticleNumber");

        // db로부터 값 가져오기
        final ConsultActivity consultActivity = new ConsultActivity(getApplicationContext());
        final Article article = consultActivity.getArticleByArticleNumber(Integer.parseInt(articleNumber));
        tvTitle.setText(article.getTitle());
        tvDeadLine.setText(article.getId());
        tvName.setText(article.getWriter());
        tvDate.setText(article.getWriteDate());
        tvContent.setText(article.getContent());
        try{
            InputStream ims = getApplicationContext().getAssets().open(article.getImgName());
            Drawable d = Drawable.createFromStream(ims, null);
  //          ivImage.setImageDrawable(d);
        } catch (IOException e) {
            Log.e("ERROR", "ERROR," + e);
        }

        //삭제
        bnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleViewer.this, Login.class);
                intent.putExtra("select", 2);
                startActivity(intent);

                try {
                    consultActivity.removeData(article.getArticleNumber());
                    finish();
                }catch(Exception e){
                    Log.e("test", "REMOVE JSON ERROR! - " + e);
                    e.printStackTrace();
                }
            }
        });

        //수정
        bnModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ArticleViewer.this, Login.class);
                    intent.putExtra("select", 1);
                    intent.putExtra("num", articleNumber);
                    startActivity(intent);
                    finish();
                }catch(Exception e){
                    Log.e("test", "MODIFY JSON ERROR! - " + e);
                    e.printStackTrace();
                }
            }
        });

        // 닫기
        bnCancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    } //end of oncreate
}
