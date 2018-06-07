package com.example.sures.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sures.movies.data.MoviesContract.MoviesEntry;

public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    MoviesDBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_POPULAR_MOVIES_TABLE = "CREATE TABLE "  + MoviesEntry.POPULAR_MOVIE_TABLE_NAME + " (" +
                MoviesEntry._ID                + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_TRAILER + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_REVIEW    + " TEXT NOT NULL);";

        final String CREATE_RATING_MOVIES_TABLE = "CREATE TABLE "  + MoviesEntry.RATING_MOVIE_TABLE_NAME + " (" +
                MoviesEntry._ID                + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_TRAILER + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_REVIEW    + " TEXT NOT NULL);";

        final String CREATE_FAVOURITE_MOVIES_TABLE = "CREATE TABLE "  + MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME + " (" +
                MoviesEntry._ID                + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_TRAILER + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_REVIEW    + " TEXT NOT NULL);";

        db.execSQL(CREATE_POPULAR_MOVIES_TABLE);
        db.execSQL(CREATE_RATING_MOVIES_TABLE);
        db.execSQL(CREATE_FAVOURITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.POPULAR_MOVIE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.RATING_MOVIE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME);
        onCreate(db);
    }
}
