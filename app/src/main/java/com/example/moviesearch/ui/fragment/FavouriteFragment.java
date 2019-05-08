package com.example.moviesearch.ui.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.moviesearch.R;
import com.example.moviesearch.api.model.Result;
import com.example.moviesearch.base.BaseFragment;
import com.example.moviesearch.databinding.MovieListLayoutBinding;
import com.example.moviesearch.ui.adapter.FavouriteAdapter;
import com.example.moviesearch.ui.viewmodel.CustomViewModelFactory;
import com.example.moviesearch.ui.viewmodel.SearchViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends BaseFragment<MovieListLayoutBinding> {

    private FavouriteAdapter adapter;
    private SearchViewModel viewModel;
    private LinearLayoutManager layoutManager;

    @Inject
    CustomViewModelFactory factory;

    @Override
    public int getLayout() {
        return R.layout.movie_list_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(SearchViewModel.class);
        initFavMovieAdapter();
        observeDataChange();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initFavMovieAdapter() {
        adapter = new FavouriteAdapter(getContext(), new ArrayList<>());
        layoutManager = new LinearLayoutManager(getContext());
        binding.rvMovies.setLayoutManager(layoutManager);
        binding.rvMovies.setAdapter(adapter);

    }

    private void observeDataChange() {
        viewModel.getFavouriteMovies().observe(getViewLifecycleOwner(), this::setFavMovieData);
        viewModel.getLoading().observe(getViewLifecycleOwner(), this::showLoading);
    }

    private void setFavMovieData(List<Result> results) {
        if (results != null && results.size() > 0) {
            binding.tvNoItem.setVisibility(View.GONE);
        } else {
            binding.tvNoItem.setVisibility(View.VISIBLE);
            binding.tvNoItem.setText(R.string.empty_list);
        }
        adapter.swapData(results);
    }

    private void showLoading(boolean isLoading) {
        binding.setShowLoading(isLoading);
    }

}
