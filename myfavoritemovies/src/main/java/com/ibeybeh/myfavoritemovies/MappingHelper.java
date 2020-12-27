package com.ibeybeh.myfavoritemovies;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.DESCRIPTION;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.IMG_URL;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.POPULARITY;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.POSTER_URL;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.RATING;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.RELEASE_DATE;
import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.TITLE;

public class MappingHelper {

    public static ArrayList<FavoriteMoviesItem> mapCursorToArrayList(Cursor favMoviesCursor){
        ArrayList<FavoriteMoviesItem> favMoviesList = new ArrayList<>();

        while (favMoviesCursor.moveToNext()){
            int id = favMoviesCursor.getInt(favMoviesCursor.getColumnIndexOrThrow(_ID));
            String title = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(TITLE));
            String desc = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(DESCRIPTION));
            String imgUrl = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(IMG_URL));
            String posterUrl = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(POSTER_URL));
            String popularity = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(POPULARITY));
            String releaseDate = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String rating = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(RATING));
            favMoviesList.add(new FavoriteMoviesItem(id, title, desc, imgUrl, posterUrl, popularity, releaseDate, rating));
        }
        return favMoviesList;
    }
}
