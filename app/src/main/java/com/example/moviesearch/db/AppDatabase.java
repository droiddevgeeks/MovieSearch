package com.example.moviesearch.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.moviesearch.api.model.Result;

@Database(entities = {Result.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "favmovie.db";

    public abstract FavMovieDao favMovieDao();
}
