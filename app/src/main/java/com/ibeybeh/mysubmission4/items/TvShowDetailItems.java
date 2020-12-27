package com.ibeybeh.mysubmission4.items;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowDetailItems implements Parcelable {
    private int id;
    private String voteAverage;
    private String popularity;
    private String name;
    private String backdropPath;
    private String posterPath;
    private String firstAirDate;
    private String overview;
    private final String URL_POSTER_PATH = "https://image.tmdb.org/t/p/";
    private final String POSTER_SIZE = "original";

    public TvShowDetailItems(){

    }

    protected TvShowDetailItems(Parcel in) {
        id = in.readInt();
        voteAverage = in.readString();
        popularity = in.readString();
        name = in.readString();
        backdropPath = in.readString();
        posterPath = in.readString();
        firstAirDate = in.readString();
        overview = in.readString();
    }

    public static final Creator<TvShowDetailItems> CREATOR = new Creator<TvShowDetailItems>() {
        @Override
        public TvShowDetailItems createFromParcel(Parcel in) {
            return new TvShowDetailItems(in);
        }

        @Override
        public TvShowDetailItems[] newArray(int size) {
            return new TvShowDetailItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
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
        return URL_POSTER_PATH+POSTER_SIZE+backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return URL_POSTER_PATH+POSTER_SIZE+posterPath;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(voteAverage);
        parcel.writeString(popularity);
        parcel.writeString(name);
        parcel.writeString(backdropPath);
        parcel.writeString(posterPath);
        parcel.writeString(firstAirDate);
        parcel.writeString(overview);
    }
}
