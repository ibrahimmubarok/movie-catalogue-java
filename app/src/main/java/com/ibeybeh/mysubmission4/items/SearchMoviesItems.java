package com.ibeybeh.mysubmission4.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchMoviesItems implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private String voteAverageSearch;
    @SerializedName("popularity")
    private String popularitySearch;
    @SerializedName("title")
    private String titleSearch;
    @SerializedName("backdrop_path")
    private String backdropPathSearch;
    @SerializedName("poster_path")
    private String posterPathSearch;
    @SerializedName("release_date")
    private String releaseDateSearch;
    @SerializedName("overview")
    private String overviewSearch;

    public SearchMoviesItems() {
    }

    protected SearchMoviesItems(Parcel in) {
        id = in.readInt();
        voteAverageSearch = in.readString();
        popularitySearch = in.readString();
        titleSearch = in.readString();
        backdropPathSearch = in.readString();
        posterPathSearch = in.readString();
        releaseDateSearch = in.readString();
        overviewSearch = in.readString();
    }


    public static final Creator<SearchMoviesItems> CREATOR = new Creator<SearchMoviesItems>() {
        @Override
        public SearchMoviesItems createFromParcel(Parcel in) {
            return new SearchMoviesItems(in);
        }

        @Override
        public SearchMoviesItems[] newArray(int size) {
            return new SearchMoviesItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteAverageSearch() {
        return voteAverageSearch;
    }

    public void setVoteAverageSearch(String voteAverageSearch) {
        this.voteAverageSearch = voteAverageSearch;
    }

    public String getPopularitySearch() {
        return popularitySearch;
    }

    public void setPopularitySearch(String popularitySearch) {
        this.popularitySearch = popularitySearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getBackdropPathSearch() {
        return backdropPathSearch;
    }

    public void setBackdropPathSearch(String backdropPathSearch) {
        this.backdropPathSearch = backdropPathSearch;
    }

    public String getPosterPathSearch() {
        return posterPathSearch;
    }

    public void setPosterPathSearch(String posterPathSearch) {
        this.posterPathSearch = posterPathSearch;
    }

    public String getReleaseDateSearch() {
        return releaseDateSearch;
    }

    public void setReleaseDateSearch(String releaseDateSearch) {
        this.releaseDateSearch = releaseDateSearch;
    }

    public String getOverviewSearch() {
        return overviewSearch;
    }

    public void setOverviewSearch(String overviewSearch) {
        this.overviewSearch = overviewSearch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(voteAverageSearch);
        parcel.writeString(popularitySearch);
        parcel.writeString(titleSearch);
        parcel.writeString(backdropPathSearch);
        parcel.writeString(posterPathSearch);
        parcel.writeString(releaseDateSearch);
        parcel.writeString(overviewSearch);
    }
}
