package com.ibeybeh.mysubmission4.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchTvShowsItems implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private String voteAverageSearch;
    @SerializedName("popularity")
    private String popularitySearch;
    @SerializedName("name")
    private String titleSearch;
    @SerializedName("backdrop_path")
    private String backdropPathSearch;
    @SerializedName("poster_path")
    private String posterPathSearch;
    @SerializedName("first_air_date")
    private String releaseDateSearch;
    @SerializedName("overview")
    private String overviewSearch;

    public SearchTvShowsItems() {
    }

    public static final Creator<SearchTvShowsItems> CREATOR = new Creator<SearchTvShowsItems>() {
        @Override
        public SearchTvShowsItems createFromParcel(Parcel in) {
            return new SearchTvShowsItems(in);
        }

        @Override
        public SearchTvShowsItems[] newArray(int size) {
            return new SearchTvShowsItems[size];
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

    public String getOverviewSearch() {
        return overviewSearch;
    }

    public void setOverviewSearch(String overviewSearch) {
        this.overviewSearch = overviewSearch;
    }

    public void setReleaseDateSearch(String releaseDateSearch) {
        this.releaseDateSearch = releaseDateSearch;
    }

    protected SearchTvShowsItems(Parcel in) {
        id = in.readInt();
        voteAverageSearch = in.readString();
        popularitySearch = in.readString();
        titleSearch = in.readString();
        backdropPathSearch = in.readString();
        posterPathSearch = in.readString();
        releaseDateSearch = in.readString();
        overviewSearch = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(voteAverageSearch);
        dest.writeString(popularitySearch);
        dest.writeString(titleSearch);
        dest.writeString(backdropPathSearch);
        dest.writeString(posterPathSearch);
        dest.writeString(releaseDateSearch);
        dest.writeString(overviewSearch);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
