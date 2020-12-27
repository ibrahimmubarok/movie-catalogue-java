package com.ibeybeh.mysubmission4.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ibeybeh.mysubmission4.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.getColumnTvShowInt;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.getColumnTvShowString;

public class FavoriteTvShowItem implements Parcelable {
    private int id;
    private String popularity;
    private String name;
    private String backdropPath;
    private String posterPath;
    private String firstAirDate;
    private String overview;
    private String rating;

    public FavoriteTvShowItem(){
    }

    public FavoriteTvShowItem(int id, String popularity, String name, String backdropPath, String posterPath, String firstAirDate, String overview, String rating){
        this.id = id;
        this.popularity = popularity;
        this.name = name;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.firstAirDate = firstAirDate;
        this.overview = overview;
        this.rating = rating;
    }

    public FavoriteTvShowItem(Cursor cursor){
        this.id = getColumnTvShowInt(cursor, _ID);
        this.name = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.NAME);
        this.overview = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.OVERVIEW);
        this.posterPath = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.POSTER_PATH);
        this.backdropPath = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.BACKDROP_PATH);
        this.firstAirDate = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.FIRST_AIR_DATE);
        this.popularity = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.POPULARITY);
        this.rating = getColumnTvShowString(cursor, DatabaseContract.FavoriteTvShowColumns.RATING);
    }

    protected FavoriteTvShowItem(Parcel in) {
        id = in.readInt();
        popularity = in.readString();
        name = in.readString();
        backdropPath = in.readString();
        posterPath = in.readString();
        firstAirDate = in.readString();
        overview = in.readString();
        rating = in.readString();
    }

    public static final Creator<FavoriteTvShowItem> CREATOR = new Creator<FavoriteTvShowItem>() {
        @Override
        public FavoriteTvShowItem createFromParcel(Parcel in) {
            return new FavoriteTvShowItem(in);
        }

        @Override
        public FavoriteTvShowItem[] newArray(int size) {
            return new FavoriteTvShowItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(popularity);
        parcel.writeString(name);
        parcel.writeString(backdropPath);
        parcel.writeString(posterPath);
        parcel.writeString(firstAirDate);
        parcel.writeString(overview);
        parcel.writeString(rating);
    }
}
