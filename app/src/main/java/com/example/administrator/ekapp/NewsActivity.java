package com.example.administrator.ekapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.text.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    ArrayList newsList = new ArrayList();
    ArrayList<NewsData> arrayNews = new ArrayList<NewsData>();
    Context context;
    View rootView;
    String uri="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_viewer);
        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");
        Log.d("site", uri.toString());
        new Title().execute();

        Button closebtn = (Button)findViewById(R.id.news_button_close);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               // finishActivity(-1);
            }
        });
    }

    private class Title extends AsyncTask<Void, Void, Void> {
        Elements titles; // 소스 저장할 함수
        Elements contents;

        TextView newsViewTitle=(TextView)findViewById(R.id.news_viewer_title);
        TextView newsViewDate=(TextView)findViewById(R.id.news_viewer_date);
        TextView newsViewContent=(TextView)findViewById(R.id.news_viewer_content);

        @Override
        protected  void  onPreExecute(){
            super.onPreExecute();
        }   // loading, title 등의 message 출력

        @Override
        protected Void doInBackground(Void... params){  // take info
            String data = "";
            String content="";
            int a =0;

            try{
                Document doc = Jsoup.connect(uri).get(); // site
                titles = doc.select("table.table_list tbody tr");
                for(Element e : titles){
                    int b=a%4;

                    switch (b){
                        case 0:
                            data=(e.select("th").text()).toString();
                            newsViewTitle.setText(data);
                            break;
                        case 1:
                            newsViewDate.setText(e.select("td").text().toString());
                            break;
                        case 2:
                            data=e.toString();
                            newsViewContent.setText(Html.fromHtml(data, Html.FROM_HTML_MODE_LEGACY));

//                            if (android.os.Build.VERSION.SDK_INT < 24) {
//                                newsViewContent.setText(Html.fromHtml(data));
//                            }


                           // newsViewContent.setText(Html.fromHtml(data));
                            break;
                        default:
                            break;
                    }
                    a++;
                }
            }
            catch(IOException e){
                e.printStackTrace();
                Log.d("fail","fail");
            }

            return null;
        }

        @Override
        protected  void onPostExecute(Void result){ //text view에 출력
        }
    }

    class NewsData { // 자바 빈 (java Bean)
        String title="";
        String date = "";
        String content = "";
        String list="";

        // 생성자가 있으면 객체 생성시 편리하다
        public NewsData(String title, String date, String content, String list) {
            this.title = title;
            this.date = date;
            this.content=content;
            this.list=list;

            Log.d("title", this.title);
            Log.d("date", this.date);
            Log.d("content", this.content);
        }

        public NewsData() {}// 기존 코드와 호환을 위해서 생성자 작업시 기본생성자도 추가
    }

    class NewsListAdapter extends BaseAdapter {
        Context context;     // 현재 화면의 제어권자
        int layout;              // 한행을 그려줄 layout
        ArrayList<NewsData> arrayNews;     // 다량의 데이터

        LayoutInflater inf; // 화면을 그려줄 때 필요
        public NewsListAdapter(Context context, int layout, ArrayList<NewsData> arrayNews) {
            this.context = context;
            this.layout = layout;
            this.arrayNews = arrayNews;
            this.inf = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() { // 총 데이터의 개수를 리턴
            return arrayNews.size();
        }
        @Override
        public Object getItem(int position) { // 해당번째의 데이터 값
            return arrayNews.get(position);
        }
        @Override
        public long getItemId(int position) { // 해당번째의 고유한 id 값
            return position;
        }
        @Override // 해당번째의 행에 내용을 셋팅(데이터와 레이아웃의 연결관계 정의)
        public View getView(int position, View convertView, ViewGroup parent) {
            return convertView;
        }
    }

}
