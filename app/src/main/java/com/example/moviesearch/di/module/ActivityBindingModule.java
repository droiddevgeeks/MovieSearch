package com.example.moviesearch.di.module;

import com.example.moviesearch.di.module.fragment.MainActivityFragmentModule;
import com.example.moviesearch.ui.activity.MainActivity;
import com.example.moviesearch.ui.activity.MovieDetailActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainActivityFragmentModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract MovieDetailActivity bindDetailActivity();
}
