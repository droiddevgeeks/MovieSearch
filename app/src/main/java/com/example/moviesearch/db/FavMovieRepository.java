package com.example.moviesearch.db;

import androidx.lifecycle.LiveData;
import com.example.moviesearch.api.model.Result;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.List;

public class FavMovieRepository {

    private final FavMovieDao favMovieDao;

    @Inject
    public FavMovieRepository(FavMovieDao movieDao) {
        this.favMovieDao = movieDao;
    }

    public LiveData<List<Result>> getFavMovies() {
        return favMovieDao.getFavMovies();
    }

    public Single<Long> insertFavMovie(Result movie) {
        return Single.fromCallable(() -> favMovieDao.addFavMovie(movie));
    }

    public Single<Integer> removeFavMovie(Result movie) {
        return Single.fromCallable(() -> favMovieDao.removeEntry(movie));
    }

}
