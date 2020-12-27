package com.ibeybeh.mysubmission4.rest.service;

import com.ibeybeh.mysubmission4.items.SearchMoviesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.ibeybeh.mysubmission4.rest.client.ApiClient.API_KEY;

public interface GetSearchMoviesInterface {

    @GET("3/search/movie?api_key="+API_KEY)
    Call<SearchMoviesModel> getSearchMovies(
            @Query("query") String moviesQuery
    );
}
