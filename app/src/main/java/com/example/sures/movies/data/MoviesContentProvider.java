package com.example.sures.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.sures.movies.data.MoviesContract.MoviesEntry;

public class MoviesContentProvider extends ContentProvider {
    private MoviesDBHelper moviesDBHelper;
    private UriMatcher uriMatcher = buildUriMatcher();

    public static final int POPULAR_MOVIES = 100;
    public static final int POPULAR_MOVIES_WITH_ID = 101;
    public static final int RATING_MOVIES = 200;
    public static final int RATING_MOVIES_WITH_ID = 201;
    public static final int FAVOURITE_MOVIES = 300;
    public static final int FAVOURITE_MOVIES_WITH_ID = 301;

    public static UriMatcher buildUriMatcher () {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_POPULAR_MOVIES, POPULAR_MOVIES);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_POPULAR_MOVIES + "/#", POPULAR_MOVIES_WITH_ID);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_RATING_MOVIES,RATING_MOVIES);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_RATING_MOVIES + "/#", RATING_MOVIES_WITH_ID);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_POPULAR_MOVIES,FAVOURITE_MOVIES);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAVOURITE_MOVIES + "/#", FAVOURITE_MOVIES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        this.moviesDBHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase database = this.moviesDBHelper.getReadableDatabase();
        int matchUri = this.uriMatcher.match(uri);
        Cursor retCursor;

        switch (matchUri) {
            case POPULAR_MOVIES:
                retCursor = database.query(MoviesEntry.POPULAR_MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RATING_MOVIES:
                retCursor = database.query(MoviesEntry.RATING_MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVOURITE_MOVIES:
                retCursor = database.query(MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case POPULAR_MOVIES_WITH_ID:
                retCursor = database.query(MoviesEntry.POPULAR_MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RATING_MOVIES_WITH_ID:
                retCursor = database.query(MoviesEntry.RATING_MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVOURITE_MOVIES_WITH_ID:
                retCursor = database.query(MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase database = this.moviesDBHelper.getWritableDatabase();
        int matchUri = this.uriMatcher.match(uri);
        Uri returnUri;
        long result;
        switch (matchUri) {
            case POPULAR_MOVIES:
               result =  database.insert(MoviesEntry.POPULAR_MOVIE_TABLE_NAME,null,values);
                if ( result > 0 ) {
                    returnUri = ContentUris.withAppendedId(MoviesEntry.CONTENT_POPULAR_MOVIES_URI, result);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
               break;
            case RATING_MOVIES:
                result =  database.insert(MoviesEntry.RATING_MOVIE_TABLE_NAME,null,values);
                if ( result > 0 ) {
                    returnUri = ContentUris.withAppendedId(MoviesEntry.CONTENT_RATING_MOVIES_URI, result);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case FAVOURITE_MOVIES:
                result =  database.insert(MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME,null,values);
                if ( result > 0 ) {
                    returnUri = ContentUris.withAppendedId(MoviesEntry.CONTENT_FAVOURITE_MOVIES_URI, result);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = this.moviesDBHelper.getWritableDatabase();
        int matchUri = this.uriMatcher.match(uri);
        int returnValue;
        switch (matchUri) {
            case POPULAR_MOVIES:
                returnValue = database.delete(MoviesEntry.POPULAR_MOVIE_TABLE_NAME,selection,selectionArgs);
                break;
            case RATING_MOVIES:
                returnValue = database.delete(MoviesEntry.RATING_MOVIE_TABLE_NAME,selection,selectionArgs);
                break;
            case FAVOURITE_MOVIES:
                returnValue = database.delete(MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (returnValue != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnValue;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = this.moviesDBHelper.getWritableDatabase();
        int matchUri = this.uriMatcher.match(uri);
        int returnValue;

        switch (matchUri) {
            case POPULAR_MOVIES_WITH_ID:
                returnValue = database.update(MoviesEntry.POPULAR_MOVIE_TABLE_NAME, values, selection, selectionArgs);
                break;
            case RATING_MOVIES_WITH_ID:
                returnValue = database.update(MoviesEntry.RATING_MOVIE_TABLE_NAME, values, selection, selectionArgs);
                break;
            case FAVOURITE_MOVIES_WITH_ID:
                returnValue = database.update(MoviesEntry.FAVOURITE_MOVIE_TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return returnValue;
    }
}
