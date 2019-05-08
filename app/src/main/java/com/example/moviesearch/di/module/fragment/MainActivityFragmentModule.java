package com.example.moviesearch.di.module.fragment;

import com.example.moviesearch.ui.fragment.FavouriteFragment;
import com.example.moviesearch.ui.fragment.MovieFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityFragmentModule {

    @ContributesAndroidInjector
    abstract MovieFragment provideMovieFragment();

    @ContributesAndroidInjector
    abstract FavouriteFragment provideFavouriteFragment();
}
