package com.example.sures.movies.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import com.example.sures.movies.data.MovieInfo;
import com.example.sures.movies.network.DataFetch;
import java.util.ArrayList;

public class MovieLoader extends AsyncTaskLoader<ArrayList<MovieInfo>>{
    private final String apiKey = "cdebd4c255c625febb97e2f91c1ed9e5";
    private final String authority = "https://api.themoviedb.org";
    private Context context;

    public MovieLoader(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<MovieInfo> loadInBackground() {
        DataFetch dataFetch = new DataFetch(this.context);
        return dataFetch.makeAPICall("PopularMovies", authority, apiKey);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<MovieInfo> data) {
        super.deliverResult(data);
    }
}