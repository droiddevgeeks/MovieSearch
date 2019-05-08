package com.example.moviesearch.di.component;

import com.example.moviesearch.MyApplication;
import com.example.moviesearch.di.module.*;
import com.example.moviesearch.di.scope.AppScope;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        ApiModule.class,
        RxModule.class,
        AppDbModule.class})
public interface ApplicationComponent extends AndroidInjector<MyApplication> {

    void inject(MyApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);

        ApplicationComponent build();
    }
}