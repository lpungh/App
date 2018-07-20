package com.example.administrator.ekapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("홈"));
        tabLayout.addTab(tabLayout.newTab().setText("채용정보"));
        tabLayout.addTab(tabLayout.newTab().setText("취업 뉴스"));
        tabLayout.addTab(tabLayout.newTab().setText("ELITE 프로그램"));
        tabLayout.addTab(tabLayout.newTab().setText("회사소개"));
        tabLayout.addTab(tabLayout.newTab().setText("주요 사업"));
        //tabLayout.addTab(tabLayout.newTab().setText("EK 구인정보"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        initNavigationDrawer();

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    } //end of class Title


    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){

                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.EliteJob:
                        viewPager.setCurrentItem(1);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.JobNews:
                        viewPager.setCurrentItem(2);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.EliteProgram:
                        viewPager.setCurrentItem(3);
                        drawerLayout.closeDrawers();
                        break;
                    case  R.id.EliteAbout:
                        viewPager.setCurrentItem(4);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.EliteBusiness:
                        viewPager.setCurrentItem(5);
                        drawerLayout.closeDrawers();
                        break;
                    case  R.id.EliteGetJob:
                        viewPager.setCurrentItem(6);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.EliteWeb:
                        drawerLayout.closeDrawers();
                        Intent intent_EW = new Intent(MainActivity.this,JobInfoActivity.class);
                        intent_EW.putExtra("uri","http://www.elitekorea.com/");
                        startActivity(intent_EW);
                        break;
                    case R.id.Jobyoung:
                        drawerLayout.closeDrawers();
                        Intent intent_JY = new Intent(MainActivity.this,JobInfoActivity.class);
                        intent_JY.putExtra("uri","http://www.work.go.kr/jobyoung/main.do");
                        startActivity(intent_JY);
                        break;
                    case R.id.HRD:
                        drawerLayout.closeDrawers();
                        Intent intent_HRD = new Intent(MainActivity.this,JobInfoActivity.class);
                        intent_HRD.putExtra("uri","http://www.hrd.go.kr/hrdp/ma/pmmao/index.do");
                        startActivity(intent_HRD);
                        break;
                    case R.id.careerNet:
                        drawerLayout.closeDrawers();
                        Intent intent_careerNet = new Intent(MainActivity.this,JobInfoActivity.class);
                        intent_careerNet.putExtra("uri","http://www.career.go.kr/cnet/front/main/main.do");
                        startActivity(intent_careerNet);
                        break;
                    case R.id.eliteFacebook:
                        drawerLayout.closeDrawers();
                        Intent intent_fc = new Intent(MainActivity.this,JobInfoActivity.class);
                        intent_fc.putExtra("uri","https://www.facebook.com/elite2133/");
                        startActivity(intent_fc);
                        break;
                    case R.id.eliteInstagram:
                        drawerLayout.closeDrawers();
                        Intent intent_el = new Intent(MainActivity.this,JobInfoActivity.class);
                        intent_el.putExtra("uri","http://instagram.com/elitekorea_");
                        startActivity(intent_el);
                        break;
                }
                return true;
            }
        });
        final View header = navigationView.getHeaderView(0);

//        Button callBtn = (Button) header.findViewById(R.id.call);
//        callBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                String tel = "tel:02-561-2133";
//                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
//            }
//        });

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

} //end of main activity
