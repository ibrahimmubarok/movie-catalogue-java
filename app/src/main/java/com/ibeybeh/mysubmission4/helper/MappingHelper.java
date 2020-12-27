package com.ibeybeh.mysubmission4.helper;

import android.database.Cursor;

import com.ibeybeh.mysubmission4.db.DatabaseContract;
import com.ibeybeh.mysubmission4.entity.FavoriteMoviesItem;
import com.ibeybeh.mysubmission4.entity.FavoriteTvShowItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.*;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.POPULARITY;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.RATING;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.*;

public class MappingHelper {

    public static ArrayList<FavoriteMoviesItem> mapCursorToArrayListMovies(Cursor favMoviesCursor){
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

    public static ArrayList<FavoriteTvShowItem> mapCursorToArrayListTvShow(Cursor favTvShowCursor){
        ArrayList<FavoriteTvShowItem> favTvShowList = new ArrayList<>();

        while (favTvShowCursor.moveToNext()){
            int id = favTvShowCursor.getInt(favTvShowCursor.getColumnIndexOrThrow(_ID));
            String popularity = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.POPULARITY));
            String name = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(NAME));
            String backdropPath = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(BACKDROP_PATH));
            String posterPath = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(POSTER_PATH));
            String firstAirDate = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(FIRST_AIR_DATE));
            String overview = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(OVERVIEW));
            String rating = favTvShowCursor.getString(favTvShowCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteTvShowColumns.RATING));
            favTvShowList.add(new FavoriteTvShowItem(id, popularity, name, backdropPath, posterPath, firstAirDate, overview, rating));
        }
        return favTvShowList;
    }
}
