package com.example.moviesearch.api;


import com.example.moviesearch.api.model.MovieResponse;
import io.reactivex.Single;

import javax.inject.Inject;

public class ApiClient {
    private final Api api;

    @Inject
    public ApiClient(Api api) {
        this.api = api;
    }

    public Single<MovieResponse> getTopRatedMovies(String page) {
        return api.getTopRatedMovies(ApiConstants.API_KEY,
                ApiConstants.LANGUAGE,
                page,
                ApiConstants.PER_PAGE_LIMIT);
    }
}
