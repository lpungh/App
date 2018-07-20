package com.example.administrator.ekapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-07-12.
 */

public class Login extends AppCompatActivity implements Button.OnClickListener {
    Context context;
    int select; // Login 이전 페이지 알아내기
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login_btn = (Button)findViewById(R.id.login_button);
        login_btn.setOnClickListener(this);

        intent = getIntent();
        select = intent.getExtras().getInt("select");
    }

    @Override
    public void onClick(View v) {
        EditText login_id = (EditText)findViewById(R.id.login_id);
        EditText login_pw = (EditText)findViewById(R.id.login_pw);
        TextView login_text = (TextView)findViewById(R.id.login_text);

// EliteKorea  025612133
        if( login_id.getText().toString().equals("EliteKorea") && login_pw.getText().toString().equals("025612133") ) // 로그인 성공
        {
            switch (select)
            {
                case 0: // 작성
                    intent = new Intent(Login.this, ArticleWrite.class);
                    startActivity(intent);
                    finish();
                    break;

                case 1: // 수정
                    String num = intent.getExtras().getString("num");

                    try{
                        intent = new Intent(Login.this, ArticleModify.class);
                        intent.putExtra("num", num);
                        startActivity(intent);
                        finish();
                    } catch (Exception e)
                    {
                        Log.e("Login", "Login - MODIFY JSON ERROR! - " + e);
                        e.printStackTrace();
                    }
                    break;

                case 2: // 삭제
                    finish();
                    break;

                default:
                    break;
            }
        }
        else{ // 로그인 실패
            login_text.setText("   로그인에 실패하셨습니다. \n 새로운 계정발급을 원하시면 \n  관련부서에 문의바랍니다.");
        }
    }

}
