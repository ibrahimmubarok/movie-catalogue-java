package com.ibeybeh.mysubmission4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.ibeybeh.mysubmission4.db.FavoriteMoviesHelper;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.AUTHORITY;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.TABLE_FAVORITE_MOVIES;

public class FavoriteMoviesProvider extends ContentProvider {

    private static final int FAV_MOVIES = 1;
    private static final int FAV_MOVIES_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteMoviesHelper favoriteMoviesHelper;

    static{
        //content://com.ibeybeh.mysubmission4/favorite_movies
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIES, FAV_MOVIES);

        //content://com.ibeybeh.mysubmission4/favorite_movies/id
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_MOVIES + "/#", FAV_MOVIES_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteMoviesHelper = FavoriteMoviesHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteMoviesHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case FAV_MOVIES :
                cursor = favoriteMoviesHelper.queryProvider();
                break;

            case FAV_MOVIES_ID :
                cursor = favoriteMoviesHelper.queryByIdProviders(uri.getLastPathSegment());
                break;

            default :
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        favoriteMoviesHelper.open();
        long added;
        switch (sUriMatcher.match(uri)){
            case FAV_MOVIES :
                added = favoriteMoviesHelper.insertProviders(contentValues);
                break;

            default :
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        favoriteMoviesHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)){
            case FAV_MOVIES_ID :
                deleted = favoriteMoviesHelper.deleteProviders(uri.getLastPathSegment());
                break;

            default :
                deleted = 0;
                break;
        }

        if (deleted > 0){
            getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        }
        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
