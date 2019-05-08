package com.example.moviesearch.ui.callback;

public interface IItemClick<T> {
    void onItemClick(T item, int position);
    void onFavClick(T item,boolean selected);
}
