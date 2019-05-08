package com.example.moviesearch.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesearch.api.model.Result;
import com.example.moviesearch.databinding.MovieFavRowBinding;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private List<Result> movieList;
    private LayoutInflater layoutInflater;

    public FavouriteAdapter(Context context, List<Result> movieList) {
        this.movieList = movieList;
        layoutInflater = LayoutInflater.from(context);
    }

    public void swapData(final List<Result> list) {
        if (!this.movieList.isEmpty()) {
            this.movieList.clear();
        }
        this.movieList.addAll(list);
        notifyDataSetChanged();
    }

    public void appendData(final List<Result> list) {
        int previousSize = movieList.size();
        movieList.addAll(list);
        notifyItemRangeInserted(previousSize, movieList.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(MovieFavRowBinding.inflate(layoutInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Result movie = movieList.get(i);
        viewHolder.binding.setMovie(movie);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        private final MovieFavRowBinding binding;

        public ViewHolder(MovieFavRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
