package com.example.administrator.ekapp;

import android.support.v4.app.FragmentStatePagerAdapter;



public class Fragment_History_Adapter extends FragmentStatePagerAdapter{
    private int tabCount;

    public Fragment_History_Adapter(android.support.v4.app.FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        //Returning the current tabs
        switch (position){
            case 0:
                Fragment_History_2016 fragmentHistory2016 = new Fragment_History_2016();
                return fragmentHistory2016;
            case 1:
                Fragment_History_2015 fragmentHistory2015 = new Fragment_History_2015();
                return fragmentHistory2015;
            case 2:
                Fragment_History_2014 fragmentHistory2014 = new Fragment_History_2014();
                return fragmentHistory2014;
            case 3:
                Fragment_History_2013 fragmentHistory2013 = new Fragment_History_2013();
                return fragmentHistory2013;
            case 4:
                Fragment_History_2012 fragmentHistory2012 = new Fragment_History_2012();
                return fragmentHistory2012;
            case 5:
                Fragment_History_2011 fragmentHistory2011 = new Fragment_History_2011();
                return fragmentHistory2011;
            case 6:
                Fragment_History_2010 fragmentHistory2010 = new Fragment_History_2010();
                return fragmentHistory2010;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
