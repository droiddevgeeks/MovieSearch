package com.example.moviesearch.api;

import com.example.moviesearch.api.model.MovieResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    //https://api.themoviedb.org/3/movie/top_rated?
    // api_key=3d70ca0fa7d443b745df2f65a7596208
    // &language=en-US
    // &page="0"
    // &limit="25"
    @GET(ApiConstants.TOP_RATED)
    Single<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page,
            @Query("limit") String limit
    );

}
