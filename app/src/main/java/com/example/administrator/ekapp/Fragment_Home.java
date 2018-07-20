package com.example.administrator.ekapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Home extends Fragment{
    Context context;
    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();

        return rootView;
    }
}


//
///**
// * Created by YunJungsu on 2017-07-04.
// */
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class Fragment_Home extends Fragment {
//    String baseurl = null;
//    String baselink = null;
//    ArrayList mainList = new ArrayList();
//    ArrayList dateList = new ArrayList();
//    ArrayList enterList = new ArrayList();
//    ArrayList<mainData> arraymain = new ArrayList<mainData>();      //mainData라는 ArrayList를 담은 arrayList arraymain
//    ArrayList<mainData> arraymain2 = new ArrayList<mainData>();     //mainData라는 ArrayList를 담은 arrayList arraymain2
//    Context context;
//    View rootView;
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        rootView = inflater.inflate(R.layout.fragment_home, container, false);      //rootView 에 fragment_home 화면 넣
//        context = getActivity();
//
//        new Title().execute();
//
//        //tvEnter.setText(arraymain.indexOf(0));
//       // tvDate.setText(jD.date);
//       // tvName.setText(jD.enter);
//       // Glide.with(context.getApplicationContext()).load(jD.logo).into(ivLogo);
//
//        // pull to refresh
//        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                //새로고침 작업 실행...
//                new Title().execute();
//
//                mSwipeRefreshLayout.setRefreshing(false);
//
//            }
//        });
//
//        return rootView;
//    }
//
//
//    private class Title extends AsyncTask<Void, Void, Void> {
//        Elements w_images; // 웹 이미지
//        Elements w_due;     //데이터
//        Elements w_link;    //링크
//        final StringBuffer s_images = new StringBuffer(); // string으로 저장하기 위한 변수
//        final StringBuffer s_link = new StringBuffer(); // string으로 저장하기 위한 변수
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }   // loading, title 등의 message 출력
//
//        @Override
//        protected Void doInBackground(Void... params) {  // take info
//            try {
//                Document doc = Jsoup.connect("https://www.elitekorea.com").get(); // site
//                w_images = doc.select("div.clsindex_royal_bx dl img");     // class name
//                w_due = doc.select("div.clsindex_royal_bx dl");     // class name
//                w_link = doc.select("div.clsindex_royal_bx dt a");
//                String t_link2[] = null;
//                for (Element e : w_images) {  // element 를 string으로 변환
//                    s_images.append(e);
//                }
//                for (Element e : w_due) {  // element 를 string으로 변환
//                    dateList.add(e.select("dd.clsroyal_enddate").text());
//
//                    String temp_str = e.select("dd.clsroyal_content>a").text();
//                    if (temp_str.length() >= 14)
//                        mainList.add(temp_str.substring(0, 14));
//                    else
//                        mainList.add(temp_str);
//
//                    enterList.add(e.select("dd.clsroyal_content span.clsroyal_comname a").text());
//                }
//                for (Element e : w_link) {  // element 를 string으로 변환
//                    s_link.append(e);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) { //text view에 출력
//            String tokens_image = s_images.toString();
//            String tokens_link = s_link.toString();
//            String[] arr_image = tokens_image.split("\"");  // \ 로 잘라서 집어넣어버림
//            String[] arr_link = tokens_link.split("\"");
//
//            String t_logo[] = new String[arr_image.length];
//            String t_link[] = new String[arr_link.length];
//            int a = 0, b = 0;
//
//
//            for (int i = 0; i < arr_image.length; i++) { //로고 분리
//                if (arr_image[i].length() > 4) {
//                    String cmp = arr_image[i].substring(0, 4);          //0~4까지 문자열 자름
//                    if (cmp.equals("http") == true) {
//                        baseurl = (arr_image[i]);
//                        t_logo[a] = arr_image[i];
//                        a++;
//                    }
//                }
//            }
//            for (int i = 0; i < arr_link.length; i++) { //링크분리
//                if (arr_link[i].length() > 4) {
//                    String cmp = arr_link[i].substring(0, 4);
//                    if (cmp.equals("http") == true && arr_link[i].contains("gif") == false) {
//                        baselink = (arr_link[i]);
//                        //         tv.setText(arr_image[i]);
//                        t_link[b] = arr_link[i];
//                        t_link[b] = t_link[b].replace("&amp;", "&");
//                        b++;
//                    }
//                }
//            }
//            for(int i=0; i < 4; i++){
////                Log.d(this.getClass().getName(), enterList.get(i).toString());
////                Log.d(this.getClass().getName(), mainList.get(i).toString());
////                Log.d(this.getClass().getName(), dateList.get(i).toString());
////                Log.d(this.getClass().getName(), t_link[i]);
//                //  arraymain.add(new mainData(t_logo[i], enterList.get(i).toString(), mainList.get(i).toString(), dateList.get(i).toString(), t_link[i]));
//            }
//            for(int i=4; i < mainList.size(); i++)
//                arraymain2.add(new mainData(t_logo[i], enterList.get(i).toString(), mainList.get(i).toString(), dateList.get(i).toString(), t_link[i]));
//
//            final ImageView ivLogo = (ImageView) rootView.findViewById(R.id.iv_Logo);
//            final TextView tvName = (TextView) rootView.findViewById(R.id.tvName);
//            final TextView tvEnter = (TextView) rootView.findViewById(R.id.tvInfo);
//            final TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);
//            final Button btLeft =(Button) rootView.findViewById(R.id.left);
//            final Button btRight =(Button) rootView.findViewById(R.id.right);
//            final TextView tvMainIndex = (TextView) rootView.findViewById(R.id.mainIndex);
//            RelativeLayout mainRL = (RelativeLayout) rootView.findViewById(R.id.mainLayout);
//
////            Glide.with(context.getApplicationContext()).load(arraymain.get(0).logo).into(ivLogo);
////            tvName.setText(arraymain.get(0).enter);
////            tvEnter.setText(arraymain.get(0).name);
////            tvDate.setText(arraymain.get(0).date);
//
//            mainRL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int index = Integer.parseInt(tvMainIndex.getText().toString().substring(0,1));
//
//                    Intent intent = new Intent(context.getApplicationContext(),JobInfoActivity.class);
//                    intent.putExtra("uri",arraymain.get(index-1).link);
//                    startActivity(intent);
//                }
//            });
//
//            btLeft.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int index = Integer.parseInt(tvMainIndex.getText().toString().substring(0,1));
//
//                    if(index==1)
//                        index=4;
//                    else
//                        index--;
//
//                    Glide.with(context.getApplicationContext()).load(arraymain.get(index-1).logo).into(ivLogo);
//                    tvName.setText(arraymain.get(index-1).enter);
//                    tvEnter.setText(arraymain.get(index-1).name);
//                    tvDate.setText(arraymain.get(index-1).date);
//
//                    tvMainIndex.setText(Integer.toString(index)+"/4");
//                }
//            });
//
//            btRight.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int index = Integer.parseInt(tvMainIndex.getText().toString().substring(0,1));
//
//                    if(index==4)
//                        index=1;
//                    else
//                        index++;
//
//                    Glide.with(context.getApplicationContext()).load(arraymain.get(index-1).logo).into(ivLogo);
//                    tvName.setText(arraymain.get(index-1).enter);
//                    tvEnter.setText(arraymain.get(index-1).name);
//                    tvDate.setText(arraymain.get(index-1).date);
//
//                    tvMainIndex.setText(Integer.toString(index)+"/4");
//                }
//            });
///*
//            mainListAdapter adapter = new mainListAdapter(
//                    context.getApplicationContext(), // 현재 화면의 제어권자
//                    R.layout.mainlist_cell,             // 한행을 그려줄 layout
//                    arraymain);
//
//                         ListView lv = (ListView) rootView.findViewById(R.id.mainView1);
//            lv.setAdapter(adapter);
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    mainData jD = arraymain.get(position);
//                    if (jD.link == null) return;
//                    Intent intent = new Intent(context.getApplicationContext(), JobInfoActivity.class);
//                    intent.putExtra("uri", jD.link);
//                    startActivity(intent);
//                }
//            });
//*/
//            mainListAdapter adapter2 = new mainListAdapter(
//                    context.getApplicationContext(), // 현재 화면의 제어권자
//                    R.layout.mainlist_cell,             // 한행을 그려줄 layout
//                    arraymain2);
//
//            ListView lv2 = (ListView)rootView.findViewById(R.id.mainlistView2);
//            lv2.setAdapter(adapter2);
//            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    mainData jD = arraymain2.get(position);
//                    if (jD.link == null) return;
//                    Intent intent = new Intent(context.getApplicationContext(),JobInfoActivity.class);
//                    intent.putExtra("uri",jD.link);
//                    startActivity(intent);
//                }
//            });
//        }
//    } //end of class Title
//
//    class mainData { // 자바 빈 (java Bean)
//        String name = "";
//        String date = "";
//        String enter = "";
//        String logo;
//        String link;
//
//        // 생성자가 있으면 객체 생성시 편리하다
//        public String getProfile_image() {
//            return logo;
//        }
//
//        public mainData(String logo, String enter, String name, String date,  String link) {
//            this.name = name;
//            this.date = date;
//            this.enter = enter;
//            this.logo = logo;
//            this.link = link;
//        }
//
//        public mainData() {
//        }// 기존 코드와 호환을 위해서 생성자 작업시 기본생성자도 추가
//    }
//
//    public class setIndex{
//
//        public int add(int i){
//            return i++;
//        }
//    }
//
//    class mainListAdapter extends BaseAdapter {
//    Context context;     // 현재 화면의 제어권자
//    int layout;              // 한행을 그려줄 layout
//    ArrayList<mainData> arraymain;     // 다량의 데이터
//    LayoutInflater inf; // 화면을 그려줄 때 필요
//
//    public mainListAdapter(Context context, int layout, ArrayList<mainData> arraymain) {
//        this.context = context;
//        this.layout = layout;
//        this.arraymain = arraymain;
//        this.inf = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() { // 총 데이터의 개수를 리턴
//        return arraymain.size();
//    }
//
//    @Override
//    public Object getItem(int position) { // 해당번째의 데이터 값
//        return arraymain.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) { // 해당번째의 고유한 id 값
//        return position;
//    }
//
//    @Override // 해당번째의 행에 내용을 셋팅(데이터와 레이아웃의 연결관계 정의)
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null)
//            convertView = inf.inflate(layout, null);
//        ImageView ivLogo = (ImageView) convertView.findViewById(R.id.tvLocation2);
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName2);
//        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate2);
//        TextView tvEnter = (TextView) convertView.findViewById(R.id.tvEnter2);
//
//        mainData jD = arraymain.get(position);
//
//        tvName.setText(jD.name);
//        tvDate.setText(jD.date);
//        tvEnter.setText(jD.enter);
//
//        Glide.with(context.getApplicationContext()).load(jD.logo).into(ivLogo);
//
//        return convertView;
//    }
//} // mainlist adapter
//
//    class mainAdapter extends BaseAdapter {
//        Context context;     // 현재 화면의 제어권자
//        int layout;              // 한행을 그려줄 layout
//        ArrayList<mainData> arraymain;     // 다량의 데이터
//        LayoutInflater inf; // 화면을 그려줄 때 필요
//
//        public mainAdapter(Context context, int layout, ArrayList<mainData> arraymain) {
//            this.context = context;
//            this.layout = layout;
//            this.arraymain = arraymain;
//            this.inf = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getCount() { // 총 데이터의 개수를 리턴
//            return arraymain.size();
//        }
//
//        @Override
//        public Object getItem(int position) { // 해당번째의 데이터 값
//            return arraymain.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) { // 해당번째의 고유한 id 값
//            return position;
//        }
//
//        @Override // 해당번째의 행에 내용을 셋팅(데이터와 레이아웃의 연결관계 정의)
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null)
//                convertView = inf.inflate(layout, null);
//
//            ImageView ivLogo = (ImageView) convertView.findViewById(R.id.iv_Logo);
//            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//            TextView tvEnter = (TextView) convertView.findViewById(R.id.tvInfo);
//            TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
//
//            mainData jD = arraymain.get(position);
//
//
//            tvDate.setTextColor(Color.parseColor("#3885cb"));
//            tvName.setText(jD.name);
//            tvDate.setText(jD.date);
//            tvEnter.setText(jD.enter);
//
//            if(jD.date.equals("내일마감") || jD.date.equals("오늘마감"))
//                tvDate.setTextColor(Color.parseColor("#cc0000"));
//
//            Glide.with(context.getApplicationContext()).load(jD.logo).into(ivLogo);
//
//            return convertView;
//        }
//    } // mainlist adapter
//}