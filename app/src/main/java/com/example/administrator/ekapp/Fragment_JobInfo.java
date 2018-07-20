package com.example.administrator.ekapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class Fragment_JobInfo extends Fragment {
    ArrayList jobList = new ArrayList();
    ArrayList<JobData> arrayJob = new ArrayList<JobData>();
    Context context;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_jobinfo, container, false);
        context = getActivity();

        new Title().execute();

        // pull to refresh
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //새로고침 작업 실행...
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
            String uri="";
            int a =0;
            // http://www.elitekorea.com/jobs/all/Default.asp?page=3
            try{
                for(int i=1;i<=5;i++) {
                    uri="http://www.elitekorea.com/jobs/all/Default.asp?page="+String.valueOf(i);
                    Document doc = Jsoup.connect(uri).get(); // site
                    titles = doc.select("table.table_list_B tbody tr td");

                    for (Element e : titles) {
                        if (a % 7 == 3) {
                            data = ((e.select("td.left").text()).toString()).substring(0, (e.select("td.left").text().length()));
                            if (data.length() >= 18)
                                jobList.add(data.substring(0, 18));
                            else
                                jobList.add(data);
                            jobList.add(e.select("a").attr("abs:href"));
                        } else
                            jobList.add(e.select("td").text());

                        a++;
                    }
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
            for(int i=0; i<jobList.size();i=i+8){
                arrayJob.add(new JobData(jobList.get(i).toString(), jobList.get(i+1).toString(), jobList.get(i+2).toString(),jobList.get(i+3).toString(),jobList.get(i+4).toString(),jobList.get(i+5).toString(), jobList.get(i+6).toString(), jobList.get(i+7).toString()));
            }

            JobListAdapter adapter = new JobListAdapter(
                    context.getApplicationContext(), // 현재 화면의 제어권자
                    R.layout.job_cell,             // 한행을 그려줄 layout
                    arrayJob);

            ListView lv = (ListView)rootView.findViewById(R.id.listview1);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    JobData jD = arrayJob.get(position);

                    Intent intent = new Intent(context.getApplicationContext(),JobInfoActivity.class);
                    intent.putExtra("uri",jD.info);
                    startActivity(intent);

                    Log.d("test", "아이템클릭, postion : " + position +
                            ", id : " + id + jD.info);
                }
            });
        }
    }

    class JobData { // 자바 빈 (java Bean)
        String enroll_Date="";
        String name="";
        String brand = "";
        String data = "";
        String qualification = "";
        String edu = "";
        String date = "";
        String info = "";

        // 생성자가 있으면 객체 생성시 편리하다
        public JobData(String enroll_Date, String name, String brand, String data, String info, String qualification, String edu, String date) {
            this.enroll_Date = enroll_Date;
            this.name = name;
            this.brand = brand;
            this.data = data;
            this.qualification = qualification;
            this.edu = edu;
            this.date = date;
            this.info = info;
        }

        public JobData() {}// 기존 코드와 호환을 위해서 생성자 작업시 기본생성자도 추가
    }

    class JobListAdapter extends BaseAdapter {

        // ListView 로 카카오톡 만들기
        //    1. 다량의 데이터
        //    2. Adapter (데이터와 view의 연결 관계를 정의)
        //    3. AdapterView (ListView)

        Context context;     // 현재 화면의 제어권자
        int layout;              // 한행을 그려줄 layout
        ArrayList<JobData> arrayJob;     // 다량의 데이터

        LayoutInflater inf; // 화면을 그려줄 때 필요
        public JobListAdapter(Context context, int layout, ArrayList<JobData> arrayJob) {
            this.context = context;
            this.layout = layout;
            this.arrayJob = arrayJob;
            this.inf = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() { // 총 데이터의 개수를 리턴
            return arrayJob.size();
        }
        @Override
        public Object getItem(int position) { // 해당번째의 데이터 값
            return arrayJob.get(position);
        }
        @Override
        public long getItemId(int position) { // 해당번째의 고유한 id 값
            return position;
        }
        @Override // 해당번째의 행에 내용을 셋팅(데이터와 레이아웃의 연결관계 정의)
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inf.inflate(layout, null);

            TextView tvName=(TextView)convertView.findViewById(R.id.tvName);
            TextView tvData=(TextView)convertView.findViewById(R.id.tvData);
            TextView tvQualification=(TextView)convertView.findViewById(R.id.tvQualification);
            TextView tvEdu = (TextView)convertView.findViewById(R.id.tvEdu);
            TextView tvDate =(TextView)convertView.findViewById(R.id.tvDate);

            JobData jD = arrayJob.get(position);
            tvDate.setTextColor(Color.parseColor("#666666"));
            tvName.setText(jD.name);
            tvData.setText(jD.data);
            tvQualification.setText(jD.qualification);
            tvEdu.setText(jD.edu);
            tvDate.setText(jD.date);

           Log.d("date",jD.date);
//            if(jD.date.equals("내일마감") || jD.date.equals("오늘마감"))
//                tvDate.setTextColor(Color.RED);

            return convertView;
        }
    }
}
