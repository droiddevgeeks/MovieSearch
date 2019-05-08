package com.example.moviesearch.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.moviesearch.api.ApiClient;
import com.example.moviesearch.api.ApiConstants;
import com.example.moviesearch.api.RxSingleSchedulers;
import com.example.moviesearch.api.model.MovieResponse;
import com.example.moviesearch.api.model.Result;
import com.example.moviesearch.db.FavMovieRepository;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private CompositeDisposable disposable;
    private final ApiClient apiClient;
    private final RxSingleSchedulers rxSingleSchedulers;
    private final FavMovieRepository movieRepository;

    private final MutableLiveData<MovieResponse> movieData = new MutableLiveData<>();
    private final MutableLiveData<MovieResponse> movieMoreData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> apiError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MutableLiveData<Long> itemInserted = new MutableLiveData<>();
    private final MutableLiveData<Integer> itemDeleted = new MutableLiveData<>();


    @Inject
    public SearchViewModel(ApiClient apiClient, RxSingleSchedulers rxSingleSchedulers, FavMovieRepository favMovieRepository) {
        this.apiClient = apiClient;
        this.rxSingleSchedulers = rxSingleSchedulers;
        this.movieRepository = favMovieRepository;
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<MovieResponse> getMovieData() {
        return movieData;
    }

    public MutableLiveData<MovieResponse> getMovieMoreData() {
        return movieMoreData;
    }

    public MutableLiveData<Throwable> getApiError() {
        return apiError;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<Result>> getFavouriteMovies() {
        return movieRepository.getFavMovies();
    }

    public MutableLiveData<Long> getItemInserted() {
        return itemInserted;
    }

    public MutableLiveData<Integer> getItemDeleted() {
        return itemDeleted;
    }


    public void getTopRatedMovies() {
        loading.postValue(true);
        disposable.add(apiClient
                .getTopRatedMovies(ApiConstants.STARTING_OFFSET)
                .doOnEvent((list, throwable) -> loading.postValue(false))
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(movieData::postValue, apiError::postValue)
        );
    }

    public void getMoreTopRatedMovies(String page) {
        loading.postValue(true);
        disposable.add(apiClient
                .getTopRatedMovies(page)
                .doOnEvent((list, throwable) -> loading.postValue(false))
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(movieMoreData::postValue, apiError::postValue)
        );
    }

    public void addFavMovie(Result movie) {
        loading.postValue(true);
        disposable.add(movieRepository.insertFavMovie(movie)
                .doOnEvent((data, throwable) -> loading.postValue(false))
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(itemInserted::postValue, apiError::postValue)
        );
    }

    public void removeFavMovie(Result movie) {
        loading.postValue(true);
        disposable.add(movieRepository.removeFavMovie(movie)
                .doOnEvent((data, throwable) -> loading.postValue(false))
                .compose(rxSingleSchedulers.applySchedulers())
                .subscribe(itemDeleted::postValue, apiError::postValue)
        );
    }

    public List<Result> filterData(List<Result> list, String query) {
        List<Result> filterList = new ArrayList<>();
        for (Result movie : list) {
            if (movie.getTitle().toLowerCase().contains(query)) {
                filterList.add(movie);
            }
        }
        return filterList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
