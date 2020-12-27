package com.ibeybeh.mysubmission4.items;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesModel {
    @SerializedName("results")
    private ArrayList<MoviesUpComingItems> dataMovies;

    public MoviesModel(){
    }

    public ArrayList<MoviesUpComingItems> getDataMovies() {
        return dataMovies;
    }

    public void setDataMovies(ArrayList<MoviesUpComingItems> dataMovies) {
        this.dataMovies = dataMovies;
    }
}
