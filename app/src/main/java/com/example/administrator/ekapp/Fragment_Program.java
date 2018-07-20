package com.example.administrator.ekapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_Program extends Fragment {
    Context context;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_program, container, false);
        context = getActivity();

        return rootView;
    }
}
/*
Context context;

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
rootView = inflater.inflate(R.layout.fragment_program, container, false);
context = getActivity();

return rootView;
}
}
public class Fragment_Program extends AppCompatActivity{
private ViewPager viewPager;
private TabLayout tab_layout;

public void start(){

}

public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.fragment_program);

tab_layout = (TabLayout) findViewById(R.id.tab_layout);

tab_layout.addTab(tab_layout.newTab().setText("2016"));
tab_layout.addTab(tab_layout.newTab().setText("2015"));
tab_layout.addTab(tab_layout.newTab().setText("2014"));
tab_layout.addTab(tab_layout.newTab().setText("2013"));
tab_layout.addTab(tab_layout.newTab().setText("2012"));
tab_layout.addTab(tab_layout.newTab().setText("2011"));
tab_layout.addTab(tab_layout.newTab().setText("1998~2010"));
tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);

tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);

viewPager = (ViewPager)findViewById(R.id.program_pager);

final Fragment_Program_Adapter adapter = new Fragment_Program_Adapter
(getSupportFragmentManager(), tab_layout.getTabCount());
viewPager.setAdapter(adapter);
viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
@Override
public void onTabSelected(TabLayout.Tab tab) {
viewPager.setCurrentItem(tab.getPosition());
}

@Override
public void onTabUnselected(TabLayout.Tab tab) {

}

@Override
public void onTabReselected(TabLayout.Tab tab) {

}
});
}

@Override
public void onPointerCaptureChanged(boolean hasCapture) {

}
}
public class Fragment_Program extends Fragment {
View rootView;
Context context;

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
rootView = inflater.inflate(R.layout.fragment_program, container, false);
context = getActivity();

return rootView;
}
}
ArrayList<ProgramData> arrayProgram = new ArrayList<ProgramData>();
String num="";
String text="";
String count="";
String date="";
String url="";
int pageNumber=1;
int pageIndex=0;
Context context;
View rootView;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
rootView = inflater.inflate(R.layout.fragment_program, container, false);
context = getActivity();

try {
new Title().execute().get();
} catch (Exception e) {

}
final LinearLayout lm = (LinearLayout) rootView.findViewById(R.id.button_ll);

for (int i = 1; i <= pageIndex + 1; i++) {
Button btn = new Button(context);
btn.setText(Integer.toString(i));
btn.setId(i + 0);
btn.setMinimumWidth(0);
btn.setWidth(50);
btn.setBackgroundColor(Color.TRANSPARENT);
final int position = i;

btn.setOnClickListener(new View.OnClickListener() {
public void onClick(View v) {
Log.d("log", "position :" + position);
try {
pageNumber = position;
Log.d("pageNum2", Integer.toString(pageNumber));
Log.d("pageindex2", Integer.toString(pageIndex));

new Title().execute().get();
} catch (Exception e) {
}
}
});
lm.addView(btn);
}

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

public class Title extends AsyncTask<Void, Void, Void> {
Elements titles; // 소스 저장할 함수

@Override
protected  void  onPreExecute(){
super.onPreExecute();
}   // loading, title 등의 message 출력

protected Void doInBackground(Void... para){  // take info
int a=1;
arrayProgram.clear();
try{
Log.d("pageNum",Integer.toString(pageNumber));
Document doc = Jsoup.connect("https://www.elitekorea.com/cs/notice/index.asp?page="+Integer.toString(pageNumber)).get(); // site
titles = doc.select("div.notice_board_table tbody tr td");

for(Element e : titles){
if(a%4==1)
num = e.select("td.clsnumber").text();
else if(a%4==2) {
url = e.select("a").attr("abs:href").toString();
text = e.select("td.clssubject").text();
}
else if(a%4==3)
count = e.select("td.clsview_count").text();
else if(a%4==0) {
date = e.select("td.clsdate").text();
arrayProgram.add(new ProgramData(num, text, count, date,url));
}
a++;
}
titles = doc.select("div.page03 a");

for(Element e : titles){
pageIndex++;
Log.d("index",Integer.toString(pageIndex));
}
pageNumber = Integer.parseInt(doc.select("div.page03 strong").text());
}
catch(IOException e){
e.printStackTrace();
Log.d("fail","fail");
}
return null;
}

@Override
protected void onProgressUpdate(Void... values) {
super.onProgressUpdate(values);
}

protected  void onPostExecute(Void result){ //text view에 출력

ProgramAdapter adapter = new ProgramAdapter(
context.getApplicationContext(), // 현재 화면의 제어권자
R.layout.program_cell,  arrayProgram           // 한행을 그려줄 layout
);

ListView lv = (ListView)rootView.findViewById(R.id.listview_Program);
lv.setAdapter(adapter);


lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

public void onItemClick(AdapterView<?> parent, View view,
int position, long id) {
ProgramData pD = arrayProgram.get(position);

Intent intent = new Intent(context.getApplicationContext(),JobInfoActivity.class);
intent.putExtra("uri",pD.url);
startActivity(intent);

Log.d("test", "아이템클릭, postion : " + position +
", id : " + id + pD.url);
}
});
}
}

class ProgramData { // 자바 빈 (java Bean)
String num="";
String text="";
String count="";
String date="";
String url="";

// 생성자가 있으면 객체 생성시 편리하다
public ProgramData(String num,  String text, String count, String date,String url) {
this.num = num;
this.text = text;
this.count = count;
this.date = date;
this.url=url;
Log.d("class", num + text + count+ date+url);
}
public ProgramData() {}// 기존 코드와 호환을 위해서 생성자 작업시 기본생성자도 추가
}

class ProgramAdapter extends BaseAdapter {

// ListView 로 카카오톡 만들기
//    1. 다량의 데이터
//    2. Adapter (데이터와 view의 연결 관계를 정의)
//    3. AdapterView (ListView)

Context context;     // 현재 화면의 제어권자
int layout;              // 한행을 그려줄 layout
ArrayList<ProgramData> arrayProgram;     // 다량의 데이터
LayoutInflater inf; // 화면을 그려줄 때 필요
public ProgramAdapter(Context context, int layout, ArrayList<ProgramData> arrayProgram) {
this.context = context;
this.layout = layout;
this.arrayProgram = arrayProgram;
this.inf = (LayoutInflater)context
.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
@Override
public int getCount() { // 총 데이터의 개수를 리턴
return arrayProgram.size();
}
@Override
public Object getItem(int position) { // 해당번째의 데이터 값
return arrayProgram.get(position);
}
@Override
public long getItemId(int position) { // 해당번째의 고유한 id 값
return position;
}
@Override // 해당번째의 행에 내용을 셋팅(데이터와 레이아웃의 연결관계 정의)
public View getView(int position, View convertView, ViewGroup parent) {
if (convertView == null)
convertView = inf.inflate(layout, null);

TextView tvNum=(TextView)convertView.findViewById(R.id.tv_ProNum);
TextView tvSub=(TextView)convertView.findViewById(R.id.tv_ProSub);
TextView tvCount=(TextView)convertView.findViewById(R.id.tv_ProCount);
TextView tvDate = (TextView)convertView.findViewById(R.id.tv_ProDate);

ProgramData pD = arrayProgram.get(position);

tvNum.setText(pD.num);
tvSub.setText(pD.text);
tvCount.setText(pD.count);
tvDate.setText(pD.date);

Log.d("ddd", "num"+pD.num+"text"+pD.text+"count"+pD.count+"date"+pD.date+pD.url);
return convertView;
}
}
}
*/

