package com.example.moviesearch.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.moviesearch.R;
import com.example.moviesearch.api.ApiConstants;
import com.example.moviesearch.api.model.Result;
import com.example.moviesearch.base.BaseActivity;
import com.example.moviesearch.databinding.ActivityDetailBinding;

public class MovieDetailActivity extends BaseActivity<ActivityDetailBinding> {

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            Result movie = bundle.getParcelable(ApiConstants.MOVIE_DATA);
            binding.setMovie(movie);
        }
    }
}
