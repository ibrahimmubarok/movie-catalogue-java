package com.ibeybeh.myfavoritemovies;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteMoviesItem implements Parcelable {
    private int id;
    private String title;
    private String desc;
    private String imgUrl;
    private String posterUrl;
    private String releaseDate;
    private String popularity;
    private String rating;

    public FavoriteMoviesItem(int id, String title, String desc, String imgUrl, String posterUrl, String releaseDate, String popularity, String rating) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
