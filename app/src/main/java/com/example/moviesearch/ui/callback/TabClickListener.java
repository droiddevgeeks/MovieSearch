package com.example.moviesearch.ui.callback;


import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class TabClickListener implements TabLayout.OnTabSelectedListener{

    private final ViewPager pager;

    public TabClickListener(ViewPager pager) {
        this.pager = pager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
