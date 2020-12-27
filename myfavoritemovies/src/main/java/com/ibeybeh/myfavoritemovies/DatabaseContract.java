package com.ibeybeh.myfavoritemovies;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.ibeybeh.mysubmission4";
    private static final String SCHEME = "content";

    public static final String TABLE_FAVORITE_MOVIES = "favorite_movies";

    public static final class FavoriteMoviesColumns implements BaseColumns {

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
}
