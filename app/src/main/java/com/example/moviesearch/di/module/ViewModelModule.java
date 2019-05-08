package com.example.moviesearch.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.moviesearch.di.scope.ViewModelKey;
import com.example.moviesearch.ui.viewmodel.CustomViewModelFactory;
import com.example.moviesearch.ui.viewmodel.SearchViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindMainActivityViewModel(SearchViewModel searchViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindCustomViewModelFactory(CustomViewModelFactory factory);
}
