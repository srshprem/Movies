package com.example.sures.movies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.sures.movies.adaptor.MovieAdaptor;
import com.example.sures.movies.data.MovieInfo;
import java.util.ArrayList;
import com.example.sures.movies.network.MovieLoader;

public class MainActivity extends AppCompatActivity implements MovieAdaptor.ItemOnClickListener {

    private final String apiKey = "cdebd4c255c625febb97e2f91c1ed9e5";
    private final String authority = "https://api.themoviedb.org";
    private MovieAdaptor movieAdaptor;
    private int ASYNC_LOADER_ID = 01;

    private android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<MovieInfo>> asynchLoaderCallbacks = new android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<MovieInfo>>() {

        @NonNull
        @Override
        public Loader<ArrayList<MovieInfo>> onCreateLoader(int id, @Nullable Bundle args) {
            return new MovieLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList<MovieInfo>> loader, ArrayList<MovieInfo> data) {
            movieAdaptor.updateData(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList<MovieInfo>> loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userPreference = "PopularMovies";
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        movieAdaptor = new MovieAdaptor(new ArrayList<MovieInfo>(), this);

        getSupportLoaderManager().initLoader(ASYNC_LOADER_ID, null, asynchLoaderCallbacks);
        RecyclerView recyclerView = findViewById(R.id.main_frame);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdaptor);
    }

    @Override
    public void ItemClicked(int positionClicked) {
        Intent childActivityIntent = new Intent(MainActivity.this,
                ChildActivity.class);
        childActivityIntent.putExtra("clicked_item", movieAdaptor.getItem(positionClicked));
        startActivity(childActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String userPreference = null;
        if(item.getItemId() == R.id.sort_popular) {
            userPreference = "PopularMovies";
        } else if (item.getItemId() == R.id.sort_rating) {
            userPreference = "PopularRating";
        }
        return true;
    }
}