package com.ibeybeh.mysubmission4.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.ibeybeh.mysubmission4.db.FavoriteTvShowHelper;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.*;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.CONTENT_URI;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.TABLE_FAVORITE_TV_SHOWS;

public class FavoriteTvShowProvider extends ContentProvider {

    private static final int FAV_TV_SHOW = 3;
    private static final int FAV_TV_SHOW_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteTvShowHelper favoriteTvShowHelper;

    static{
        //content://com.ibeybeh.mysubmission4/favorite_tv_shows
        sUriMatcher.addURI(AUTHORITY_TV,TABLE_FAVORITE_TV_SHOWS, FAV_TV_SHOW );

        //content://com.ibeybeh.mysubmission4/favorite_tv_shows/id
        sUriMatcher.addURI(AUTHORITY_TV, TABLE_FAVORITE_TV_SHOWS + "/#", FAV_TV_SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteTvShowHelper = FavoriteTvShowHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteTvShowHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case FAV_TV_SHOW :
                cursor = favoriteTvShowHelper.queryProvider();
                break;

            case FAV_TV_SHOW_ID :
                cursor = favoriteTvShowHelper.queryByIdProviders(uri.getLastPathSegment());
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
        favoriteTvShowHelper.open();
        long added;
        switch (sUriMatcher.match(uri)){
            case FAV_TV_SHOW :
                added = favoriteTvShowHelper.insertProviders(contentValues);
                break;

            default :
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI , null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        favoriteTvShowHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)){
            case FAV_TV_SHOW_ID :
                deleted = favoriteTvShowHelper.deleteProviders(uri.getLastPathSegment());
                break;

            default:
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
