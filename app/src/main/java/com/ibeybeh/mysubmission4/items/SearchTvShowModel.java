package com.ibeybeh.mysubmission4.items;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchTvShowModel {
    @SerializedName("results")
    private ArrayList<SearchTvShowsItems> searchTvShowModel;

    public SearchTvShowModel(){
    }

    public ArrayList<SearchTvShowsItems> getSearchTvShowModel() {
        return searchTvShowModel;
    }

    public void setSearchTvShowModel(ArrayList<SearchTvShowsItems> searchTvShowModel) {
        this.searchTvShowModel = searchTvShowModel;
    }
}
