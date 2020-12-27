package com.ibeybeh.mysubmission4.items;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchMoviesModel {
    @SerializedName("results")
    private ArrayList<SearchMoviesItems> searchDataMovies;

    public SearchMoviesModel(){
    }

    public ArrayList<SearchMoviesItems> getSearchDataMovies() {
        return searchDataMovies;
    }

    public void setSearchDataMovies(ArrayList<SearchMoviesItems> searchDataMovies) {
        this.searchDataMovies = searchDataMovies;
    }
}
