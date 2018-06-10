package com.example.sures.movies.network;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.sures.movies.data.MovieInfo;
import com.example.sures.movies.data.MoviesContentProvider;
import com.example.sures.movies.data.MoviesContract;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class DataFetch {
    private Context context;
    /*Empty Constructor*/
    public DataFetch (Context context) {
        this.context = context;
    }

    public ArrayList<MovieInfo> makeAPICall(String userPreference, String authority, String apiKey) {
        String url = this.buildURL(userPreference, authority, apiKey);
        String response = this.getResponse(url);
        return this.parseResponse(response);
    }

    /*Build URL*/
    private String buildURL(String userPreference, String authority, String apiKey) {
        Uri uri = null;
        if(userPreference.equalsIgnoreCase("PopularMovies")) {
            uri = Uri.parse(authority).buildUpon().appendPath("3").appendPath("movie").appendPath("popular").appendQueryParameter("api_key",apiKey).build();
        } else if (userPreference.equalsIgnoreCase("PopularRating")) {
            uri = Uri.parse(authority).buildUpon().appendPath("3").appendPath("movie").appendPath("top_rated").appendQueryParameter("api_key",apiKey).build();
        }
        return uri.toString();
    }

    /*Get Response*/
    private String getResponse(String url) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL urlWeb = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlWeb.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while(line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /*Parse Response*/
    private ArrayList parseResponse (String response) {
        ArrayList movieCollection = new ArrayList <MovieInfo>();
        try {
            JSONObject root = new JSONObject(response);
            JSONArray results = root.getJSONArray("results");
            int counter = 0;
            ContentValues contentValues = new ContentValues();
            while (counter < results.length()) {
                JSONObject jsonObject = results.getJSONObject(counter);
                movieCollection.add(new MovieInfo(jsonObject.getString("original_title"),
                                                    "http://image.tmdb.org/t/p/w185/" + jsonObject.getString("poster_path"),
                                                    jsonObject.getString("overview"),
                                                    jsonObject.getString("vote_average"),
                                                    jsonObject.getString("release_date")));
                contentValues.clear();
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, jsonObject.getString("original_title"));
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, "http://image.tmdb.org/t/p/w185/" + jsonObject.getString("poster_path"));
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, jsonObject.getString("overview"));
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, jsonObject.getString("vote_average"));
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, jsonObject.getString("release_date"));
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID, jsonObject.getInt("id"));
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_TRAILER, " ");
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_REVIEW," ");

                this.context.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_POPULAR_MOVIES_URI, contentValues);
                counter++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return movieCollection;
    }
}
