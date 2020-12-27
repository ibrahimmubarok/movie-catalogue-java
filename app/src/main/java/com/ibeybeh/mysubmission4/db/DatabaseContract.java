package com.ibeybeh.mysubmission4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.ibeybeh.mysubmission4";
    public static final String AUTHORITY_TV = "com.ibeybeh.mysubmission4.tvshow";
    private static final String SCHEME = "content";

    public static final String TABLE_FAVORITE_MOVIES = "favorite_movies";
    public static final String TABLE_FAVORITE_TV_SHOWS = "favorite_tv_shows";

    public static final class FavoriteMoviesColumns implements BaseColumns{

        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String IMG_URL = "img_url";
        public static final String POSTER_URL = "poster_url";
        public static final String RELEASE_DATE = "release_date";
        public static final String POPULARITY = "popularity";
        public static final String RATING = "rating";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_MOVIES)
                .build();

        public static String getColumnString(Cursor cursor, String columnName){
            return cursor.getString(cursor.getColumnIndex(columnName));
        }

        public static int getColumnInt(Cursor cursor, String columnName){
            return cursor.getInt(cursor.getColumnIndex(columnName));
        }
    }

    public static final class FavoriteTvShowColumns implements BaseColumns{
        public static final String NAME = "name";
        public static final String POPULARITY = "popularity";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String POSTER_PATH = "poster_path";
        public static final String FIRST_AIR_DATE = "first_air_date";
        public static final String OVERVIEW = "overview";
        public static final String RATING = "rating";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_FAVORITE_TV_SHOWS)
                .build();

        public static String getColumnTvShowString(Cursor cursor, String columnName){
            return cursor.getString(cursor.getColumnIndex(columnName));
        }

        public static int getColumnTvShowInt(Cursor cursor, String columnName){
            return cursor.getInt(cursor.getColumnIndex(columnName));
        }
    }
}
