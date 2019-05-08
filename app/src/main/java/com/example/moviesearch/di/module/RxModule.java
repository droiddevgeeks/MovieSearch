package com.example.moviesearch.di.module;

import com.example.moviesearch.api.RxSingleSchedulers;
import com.example.moviesearch.di.scope.AppScope;
import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @AppScope
    @Provides
    public RxSingleSchedulers providesScheduler() {
        return RxSingleSchedulers.DEFAULT;
    }
}
