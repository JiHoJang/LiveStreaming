package com.example.jang.se;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.lecture,
            R.drawable.calendar,
            R.drawable.setting
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListViewFragment(), "홈");
        adapter.addFragment(new Tab2Fragment(), "내 강의");
        adapter.addFragment(new CalandarFragment(), "일정");
        adapter.addFragment(new Tab2Fragment(), "설정");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt (0).setIcon(tabIcons[0]);
        tabLayout.getTabAt (1).setIcon(tabIcons[1]);
        tabLayout.getTabAt (2).setIcon(tabIcons[2]);
        tabLayout.getTabAt (3).setIcon(tabIcons[3]);
    }

}
