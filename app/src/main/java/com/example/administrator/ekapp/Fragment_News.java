package com.example.administrator.ekapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_News extends Fragment{
    ArrayList newsList = new ArrayList();
    ArrayList<NewsData> arrayNews = new ArrayList<NewsData>();
    Context context;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        context = getActivity();

        new Title().execute();

        // pull to refresh
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Title().execute();

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        return rootView;
    }

    private class Title extends AsyncTask<Void, Void, Void> {
        Elements titles; // 소스 저장할 함수

        @Override
        protected  void  onPreExecute(){
            super.onPreExecute();
        }   // loading, title 등의 message 출력

        @Override
        protected Void doInBackground(Void... params){  // take info
            String data = "";
            int a =0;

            try{
                Document doc = Jsoup.connect("http://www.elitekorea.com/info/bbs/recruit_news/").get(); // site
                titles = doc.select("table.table_list tbody tr td");

                for(Element e : titles){
                    if(a%2==0) {
                        data = ((e.select("td.left").text()).toString()).substring(0, (e.select("td.left").text().length()));
                        if (data.length() >= 20)
                            newsList.add(data.substring(0, 20));
                        else
                            newsList.add(data);
                        newsList.add(e.select("a").attr("abs:href"));
                    }
                    else
                        newsList.add(e.select("td").text());

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

            //집어넣기
            for(int i=0; i<newsList.size();i=i+3){
                arrayNews.add(new NewsData(newsList.get(i).toString(), newsList.get(i+1).toString(), newsList.get(i+2).toString()));
            }

            NewsListAdapter adapter = new NewsListAdapter(
                    context.getApplicationContext(), // 현재 화면의 제어권자
                    R.layout.news_cell,             // 한행을 그려줄 layout
                    arrayNews);

            ListView lv = (ListView)rootView.findViewById(R.id.listview1);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    NewsData nD = arrayNews.get(position);
                    Intent intent = new Intent(context.getApplicationContext(),NewsActivity.class);
                    intent.putExtra("uri", nD.info);
                    startActivity(intent);
                }
            });
        }
    }

    class NewsData { // 자바 빈 (java Bean)
        String title="";
        String info = "";
        String date = "";

        // 생성자가 있으면 객체 생성시 편리하다
        public NewsData(String title, String info, String date) {
            this.title = title;
            this.info = info;
            this.date = date;
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
            if (convertView == null)
                convertView = inf.inflate(layout, null);

            TextView newsTitle=(TextView)convertView.findViewById(R.id.newsTitle);
            TextView newsDate=(TextView)convertView.findViewById(R.id.newsDate);

            NewsData nD = arrayNews.get(position);
            newsDate.setTextColor(Color.parseColor("#666666"));
            newsTitle.setText(nD.title);
            newsDate.setText(nD.date);

            Log.d("date",nD.date);
//            if(jD.date.equals("내일마감") || jD.date.equals("오늘마감"))
//                tvDate.setTextColor(Color.RED);
            return convertView;
        }
    }
}