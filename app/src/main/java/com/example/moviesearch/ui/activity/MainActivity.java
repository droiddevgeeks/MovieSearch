package com.example.moviesearch.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import com.example.moviesearch.R;
import com.example.moviesearch.base.BaseActivity;
import com.example.moviesearch.databinding.ActivityMainBinding;
import com.example.moviesearch.ui.adapter.PagerAdapter;
import com.example.moviesearch.ui.callback.TabClickListener;
import com.example.moviesearch.ui.viewmodel.CustomViewModelFactory;
import com.example.moviesearch.ui.viewmodel.SearchViewModel;
import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Inject
    CustomViewModelFactory factory;
    private SearchViewModel vm;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        setUpViewPager();
    }

    private void setUpViewPager() {
        PagerAdapter pageAdapter = new PagerAdapter(getSupportFragmentManager(), binding.tlMain.getTabCount());
        binding.vpMain.setAdapter(pageAdapter);
        binding.vpMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tlMain));
        binding.tlMain.addOnTabSelectedListener(new TabClickListener(binding.vpMain));
        binding.tlMain.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return false;
    }

}
