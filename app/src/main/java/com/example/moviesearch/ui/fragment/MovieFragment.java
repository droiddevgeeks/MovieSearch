package com.example.moviesearch.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesearch.R;
import com.example.moviesearch.api.ApiConstants;
import com.example.moviesearch.api.model.MovieResponse;
import com.example.moviesearch.api.model.Result;
import com.example.moviesearch.base.BaseFragment;
import com.example.moviesearch.databinding.MovieListLayoutBinding;
import com.example.moviesearch.ui.activity.MovieDetailActivity;
import com.example.moviesearch.ui.adapter.MovieAdapter;
import com.example.moviesearch.ui.callback.IItemClick;
import com.example.moviesearch.ui.viewmodel.CustomViewModelFactory;
import com.example.moviesearch.ui.viewmodel.SearchViewModel;
import com.example.moviesearch.util.EndlessScrollListener;
import com.example.moviesearch.util.Utilities;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class MovieFragment extends BaseFragment<MovieListLayoutBinding> implements IItemClick<Result> {

    private MovieAdapter adapter;
    private SearchViewModel viewModel;
    private GridLayoutManager layoutManager;
    private EndlessScrollListener scrollListener;
    private boolean isLocalSearchPerforming = false;
    private List<Result> originalList = new ArrayList<>();

    @Inject
    CustomViewModelFactory factory;

    @Override
    public int getLayout() {
        return R.layout.movie_list_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(SearchViewModel.class);
        initMovieAdapter();
        loadMovieList();
        observeDataChange();
        observeEndlessScrolling();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        handleSearchFunctionality(searchItem);
    }

    private void handleSearchFunctionality(MenuItem searchItem) {
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                if (!text.isEmpty()) {
                    isLocalSearchPerforming = true;
                    originalList.addAll(adapter.getMovieList());
                    originalList = new ArrayList<>(new LinkedHashSet<>(originalList));
                    adapter.filterList(viewModel.filterData(originalList, text));
                }
                return false;
            }
        });
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(v -> {
            isLocalSearchPerforming = false;
            searchView.clearFocus();
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
            }
            searchItem.collapseActionView();
            adapter.filterList(viewModel.filterData(originalList, ""));
        });

    }

    private void initMovieAdapter() {
        adapter = new MovieAdapter(getContext(), new ArrayList<>());
        layoutManager = new GridLayoutManager(getContext(), 3);
        binding.rvMovies.setLayoutManager(layoutManager);
        binding.rvMovies.setAdapter(adapter);
        adapter.addListener(this);
    }

    private void loadMovieList() {
        if (Utilities.isNetworkConnected(getContext())) {
            viewModel.getTopRatedMovies();
        } else {
            showToast(getContext(), getString(R.string.internet_error));
        }
    }

    private void observeDataChange() {
        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> binding.setShowLoading(isLoading));
        viewModel.getApiError().observe(this, throwable -> showToast(getContext(), getErrorMessage(throwable)));
        viewModel.getMovieData().observe(getViewLifecycleOwner(), data -> adapter.swapData(data.getResults()));
        viewModel.getMovieMoreData().observe(getViewLifecycleOwner(), data -> adapter.appendData(data.getResults()));
        viewModel.getItemInserted().observe(getViewLifecycleOwner(), insert -> showToast(getContext(), getContext().getString(R.string.movie_fav_marked)));
        viewModel.getItemDeleted().observe(getViewLifecycleOwner(), delete -> showToast(getContext(), getContext().getString(R.string.movie_unfav_marked)));
    }

    private void observeEndlessScrolling() {
        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (Utilities.isNetworkConnected(getContext())) {
                    viewModel.getMoreTopRatedMovies(String.valueOf(page));
                } else {
                    showToast(getContext(), getString(R.string.internet_error));
                }
            }

            @Override
            public boolean isLocalSearch() {
                return isLocalSearchPerforming;
            }
        };
        binding.rvMovies.addOnScrollListener(scrollListener);
    }

    @Override
    public void onItemClick(Result item, int position) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ApiConstants.MOVIE_DATA, item);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFavClick(Result item, boolean selected) {
        if (selected) {
            viewModel.addFavMovie(item);
        } else {
            viewModel.removeFavMovie(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.removeListener();
            adapter = null;
            scrollListener = null;
        }
    }
}
