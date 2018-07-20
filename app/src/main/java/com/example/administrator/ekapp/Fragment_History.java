package com.example.administrator.ekapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Fragment_History extends FragmentActivity{
    private ViewPager viewPager;
    private TabLayout tab_layout;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);

        tab_layout=(TabLayout) findViewById(R.id.tab_layout);

        tab_layout.addTab(tab_layout.newTab().setText("2016"));
        tab_layout.addTab(tab_layout.newTab().setText("2015"));
        tab_layout.addTab(tab_layout.newTab().setText("2014"));
        tab_layout.addTab(tab_layout.newTab().setText("2013"));
        tab_layout.addTab(tab_layout.newTab().setText("2012"));
        tab_layout.addTab(tab_layout.newTab().setText("2011"));
        tab_layout.addTab(tab_layout.newTab().setText("~2010"));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPager=(ViewPager)findViewById(R.id.history_pager);

        final Fragment_History_Adapter adapter= new Fragment_History_Adapter(getSupportFragmentManager(), tab_layout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnAdapterChangeListener((ViewPager.OnAdapterChangeListener)new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
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

}
