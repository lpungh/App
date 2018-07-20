package com.example.administrator.ekapp;

/**
 * Created by YunJungsu on 2017-07-06.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Fragment link;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: // 홈
                Fragment_Home f_home = new Fragment_Home();
                return f_home;
            case 1: // 채용정보
                Fragment_JobInfo f_jobinfo = new Fragment_JobInfo();
                return f_jobinfo;
            case 2: // 취업뉴스-미완성임
                Fragment_News f_news = new Fragment_News();
                return  f_news;
            case 3: // 엘리트 프로그램-return 형 문제
                Fragment_Program f_program = new Fragment_Program();
                return f_program;
            case 4: // 회사소개
                Fragment_About f_about = new Fragment_About();
                return  f_about;
            case 5: // 주요 사업
                Fragment_Business f_business = new Fragment_Business();
                return f_business;
            case 6: // EK 구인정보-미완성임/DB 사용할것
                Fragment_Consultant f_consultant = new Fragment_Consultant();
                return f_consultant;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}