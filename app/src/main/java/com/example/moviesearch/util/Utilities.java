package com.example.moviesearch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.example.moviesearch.R;
import com.example.moviesearch.api.ApiConstants;

public final class Utilities {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();

    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(ApiConstants.IMAGE_END_POINT_W_500+ url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
