package com.example.administrator.ekapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by YunJungsu on 2017-07-07.
 */

public class Fragment_Consultant extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView mainListView1;
    private ArrayList<Article> articleList;
    Context context;
    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // rootView = inflater.inflate(R.layout.fragment_about, container, false);
       // context = getActivity();
        rootView = inflater.inflate(R.layout.fragment_consultant, container, false);
        context = getActivity();


        Button buWrite = (Button) rootView.findViewById(R.id.main_button_write);
        Button buRefresh = (Button) rootView.findViewById(R.id.main_button_refresh);

        buWrite.setOnClickListener(this);
        buRefresh.setOnClickListener(this);

        mainListView1 = (ListView)rootView.findViewById(R.id.main_listView1);

        refreshData();

        // pull to refresh
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //새로고침 작업 실행...
                refreshData();

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        return rootView;
    }

    private void refreshData(){
        //db에 json데이터 저장
        ConsultActivity consultActivity = new ConsultActivity(context);
        articleList = consultActivity.getArticleList();
        CustomAdapter customAdapter = new CustomAdapter(context, R.layout.custom_adapter, articleList);
        mainListView1.setAdapter(customAdapter);
        mainListView1.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_button_write:
                Intent intent = new Intent(context.getApplicationContext(), Login.class);
                intent.putExtra("select", 0);
                startActivity(intent);
                break;
            case R.id.main_button_refresh:
                refreshData();
                break;
        }
        refreshData();
    }

    // 리스트 중에 선택해서 상세페이지로 넘어가기
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context.getApplicationContext(), ArticleViewer.class);
        intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber()+""); //번호로 선택
        startActivity(intent);
    }

}
