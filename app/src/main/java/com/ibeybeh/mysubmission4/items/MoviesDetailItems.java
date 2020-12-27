package com.ibeybeh.mysubmission4.items;

public class MoviesDetailItems {

    private String title;
    private int id;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private String popularity;
    private String releaseDate;
    private final String URL_POSTER_PATH = "https://image.tmdb.org/t/p/";
    private final String size_poster = "original";

    public MoviesDetailItems(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return URL_POSTER_PATH+size_poster+posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return URL_POSTER_PATH+size_poster+backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
