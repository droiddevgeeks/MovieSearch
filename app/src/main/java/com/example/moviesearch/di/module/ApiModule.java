package com.example.moviesearch.di.module;

import android.util.Log;
import com.example.moviesearch.MyApplication;
import com.example.moviesearch.api.Api;
import com.example.moviesearch.api.ApiConstants;
import com.example.moviesearch.di.scope.AppScope;
import com.example.moviesearch.util.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.example.moviesearch.api.ApiConstants.HEADER_CACHE_CONTROL;
import static com.example.moviesearch.api.ApiConstants.HEADER_PRAGMA;

@Module
public class ApiModule {

    @AppScope
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder().baseUrl(ApiConstants.MOVIES_DB_API_END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @AppScope
    @Provides
    Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @AppScope
    @Provides
    OkHttpClient provideOkHttpClient(MyApplication context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheInterceptor(context))
                .cache(provideCache(context));
        return httpClient.build();
    }

    @AppScope
    @Provides
    Cache provideCache(MyApplication context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"), 20 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("Cache", "Could not create Cache!");
        }

        return cache;
    }

    @AppScope
    @Provides
    Interceptor provideCacheInterceptor(MyApplication context) {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl;
            if (Utilities.isNetworkConnected(context)) {
                cacheControl = new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    @AppScope
    @Provides
    Interceptor provideOfflineCacheInterceptor(MyApplication context) {
        return chain -> {
            Request request = chain.request();

            if (!Utilities.isNetworkConnected(context)) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }


    @AppScope
    @Provides
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
