package com.ibeybeh.mysubmission4.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShowUpComingItems implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("name")
    private String name;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("overview")
    private String overview;

    public TvShowUpComingItems(){
    }

    protected TvShowUpComingItems(Parcel in) {
        id = in.readInt();
        voteAverage = in.readString();
        popularity = in.readString();
        name = in.readString();
        backdropPath = in.readString();
        posterPath = in.readString();
        firstAirDate = in.readString();
        overview = in.readString();
    }

    public static final Creator<TvShowUpComingItems> CREATOR = new Creator<TvShowUpComingItems>() {
        @Override
        public TvShowUpComingItems createFromParcel(Parcel in) {
            return new TvShowUpComingItems(in);
        }

        @Override
        public TvShowUpComingItems[] newArray(int size) {
            return new TvShowUpComingItems[size];
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
