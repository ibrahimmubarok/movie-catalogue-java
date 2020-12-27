package com.ibeybeh.mysubmission4.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MoviesUpComingItems implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("overview")
    private String overview;
//    private final String URL_POSTER_PATH = "https://image.tmdb.org/t/p/original";
//    private final String size_poster = "original";

    public MoviesUpComingItems(){
    }

    protected MoviesUpComingItems(Parcel in) {
        id = in.readInt();
        voteAverage = in.readString();
        popularity = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
    }

    public static final Creator<MoviesUpComingItems> CREATOR = new Creator<MoviesUpComingItems>() {
        @Override
        public MoviesUpComingItems createFromParcel(Parcel in) {
            return new MoviesUpComingItems(in);
        }

        @Override
        public MoviesUpComingItems[] newArray(int size) {
            return new MoviesUpComingItems[size];
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeString(posterPath);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
    }
}
