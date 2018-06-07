package com.example.sures.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

    public static final String AUTHORITY = "com.example.suresh.prem.moviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("context://" + AUTHORITY);
    public static final String PATH_POPULAR_MOVIES = "PopularMovies";
    public static final String PATH_RATING_MOVIES = "RatingMovies";
    public static final String PATH_FAVOURITE_MOVIES = "FavouriteMovies";

    public static final class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_POPULAR_MOVIES_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES).build();

        public static final Uri CONTENT_RATING_MOVIES_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATING_MOVIES).build();

        public static final Uri CONTENT_FAVOURITE_MOVIES_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_MOVIES).build();

        public static final String POPULAR_MOVIE_TABLE_NAME = "PopularMovies";
        public static final String RATING_MOVIE_TABLE_NAME = "RatingMovies";
        public static final String FAVOURITE_MOVIE_TABLE_NAME = "FavouriteMovies";


        public static final String COLUMN_MOVIE_ID = "MovieID";
        public static final String COLUMN_ORIGINAL_TITLE = "OriginalTitle";
        public static final String COLUMN_POSTER_PATH = "PosterPath";
        public static final String COLUMN_OVERVIEW = "Overview";
        public static final String COLUMN_VOTE_AVERAGE = "VoteAverage";
        public static final String COLUMN_RELEASE_DATE = "ReleaseDate";
        public static final String COLUMN_REVIEW = "Review";
        public static final String COLUMN_TRAILER = "Trailer";
    }
}
