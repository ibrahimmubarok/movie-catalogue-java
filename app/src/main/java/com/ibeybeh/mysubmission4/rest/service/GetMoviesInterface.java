package com.ibeybeh.mysubmission4.rest.service;

import com.ibeybeh.mysubmission4.items.MoviesModel;
import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import static com.ibeybeh.mysubmission4.rest.client.ApiClient.API_KEY;

public interface GetMoviesInterface {
    @GET("3/movie/upcoming?api_key="+API_KEY+"&language=en-US&page=1")
    Call<MoviesModel> getMovies(
    );
}