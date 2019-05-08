package com.example.moviesearch.di.module;

import androidx.room.Room;
import com.example.moviesearch.MyApplication;
import com.example.moviesearch.db.AppDatabase;
import com.example.moviesearch.db.FavMovieDao;
import com.example.moviesearch.di.scope.AppScope;
import dagger.Module;
import dagger.Provides;

@Module
public class AppDbModule {

    @AppScope
    @Provides
    public AppDatabase provideAppDatabase(MyApplication application){
        return Room.databaseBuilder(application,
                AppDatabase.class, AppDatabase.DATABASE_NAME)
                .build();
    }

    @AppScope
    @Provides
    public FavMovieDao provideFavMovieDao(AppDatabase db){
        return db.favMovieDao();
    }
}
