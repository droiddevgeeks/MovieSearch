package com.example.moviesearch.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.moviesearch.api.model.Result;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavMovieDao {

    @Query("select * from fav_movies")
    LiveData<List<Result>> getFavMovies();

    @Insert(onConflict = REPLACE)
    long addFavMovie(Result movie);

    @Delete
    int removeEntry(Result movie);
}
