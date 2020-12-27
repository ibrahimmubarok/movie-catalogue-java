package com.ibeybeh.mysubmission4.items;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowModel {
    @SerializedName("results")
    private ArrayList<TvShowUpComingItems> tvShowModel;

    public TvShowModel() {
    }

    public ArrayList<TvShowUpComingItems> getTvShowModel() {
        return tvShowModel;
    }

    public void setTvShowModel(ArrayList<TvShowUpComingItems> tvShowModel) {
        this.tvShowModel = tvShowModel;
    }
}
