package com.example.moviesearch.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import com.example.moviesearch.R;
import retrofit2.HttpException;

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {

    protected B binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return binding.getRoot();
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected String getErrorMessage(final Throwable error) {
        if (error instanceof HttpException) {
            return getHttpErrorMessage(error);
        } else if (error instanceof Exception) {
            return getString(R.string.some_error);
        }
        return getString(R.string.some_error);
    }


    private String getHttpErrorMessage(Throwable throwable) {
        HttpException exception = (HttpException)throwable;
        return exception.getMessage();
    }

    public abstract int getLayout();

}
