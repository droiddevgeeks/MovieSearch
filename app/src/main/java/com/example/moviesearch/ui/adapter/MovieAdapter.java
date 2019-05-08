package com.example.moviesearch.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesearch.api.model.Result;
import com.example.moviesearch.databinding.MovieRowLayoutBinding;
import com.example.moviesearch.ui.callback.IItemClick;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Result> movieList;
    private LayoutInflater layoutInflater;
    private IItemClick<Result> listener;

    public MovieAdapter(Context context, List<Result> movieList) {
        this.movieList = movieList;
        layoutInflater = LayoutInflater.from(context);
    }

    public void swapData(final List<Result> list) {
        if (!this.movieList.isEmpty()) {
            movieList.clear();
        }
        movieList.addAll(list);
        notifyDataSetChanged();
    }

    public void appendData(final List<Result> list) {
        int previousSize = movieList.size();
        movieList.addAll(list);
        notifyItemRangeInserted(previousSize, movieList.size());
    }

    public void filterList(final List<Result> filterList) {
        movieList = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(MovieRowLayoutBinding.inflate(layoutInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Result movie = movieList.get(i);
        viewHolder.binding.setMovie(movie);
        viewHolder.binding.imgPoster.setOnClickListener(v -> listener.onItemClick(movie, viewHolder.getAdapterPosition()));

        viewHolder.binding.tvFav.setOnClickListener(v -> {
            if (movie.isMarkedFav()) {
                movie.setMarkedFav(false);
                listener.onFavClick(movie, false);
            } else {
                movie.setMarkedFav(true);
                listener.onFavClick(movie, true);
            }
            notifyItemChanged(viewHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addListener(IItemClick itemClick) {
        this.listener = itemClick;
    }

    public void removeListener() {
        listener = null;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        private final MovieRowLayoutBinding binding;
        public ViewHolder(MovieRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<Result> getMovieList(){
        return  movieList;
    }
}
