package com.ibeybeh.mysubmission4.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ibeybeh.mysubmission4.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.getColumnInt;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.getColumnString;

public class FavoriteMoviesItem implements Parcelable {
    private int id;
    private String title;
    private String desc;
    private String imgUrl;
    private String posterUrl;
    private String releaseDate;
    private String popularity;
    private String rating;

    public FavoriteMoviesItem(){
    }

    public FavoriteMoviesItem(int id, String title, String desc, String imgUrl, String posterUrl, String releaseDate, String popularity, String rating){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.rating = rating;
    }

    public FavoriteMoviesItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.TITLE);
        this.desc = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.DESCRIPTION);
        this.imgUrl = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.IMG_URL);
        this.posterUrl = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.POSTER_URL);
        this.releaseDate = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.RELEASE_DATE);
        this.popularity = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.POPULARITY);
        this.rating = getColumnString(cursor, DatabaseContract.FavoriteMoviesColumns.RATING);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    protected FavoriteMoviesItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        desc = in.readString();
        imgUrl = in.readString();
        posterUrl = in.readString();
        releaseDate = in.readString();
        popularity = in.readString();
        rating = in.readString();
    }

    public static final Creator<FavoriteMoviesItem> CREATOR = new Creator<FavoriteMoviesItem>() {
        @Override
        public FavoriteMoviesItem createFromParcel(Parcel in) {
            return new FavoriteMoviesItem(in);
        }

        @Override
        public FavoriteMoviesItem[] newArray(int size) {
            return new FavoriteMoviesItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeString(imgUrl);
        parcel.writeString(posterUrl);
        parcel.writeString(releaseDate);
        parcel.writeString(popularity);
        parcel.writeString(rating);
    }
}
