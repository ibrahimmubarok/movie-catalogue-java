package com.ibeybeh.mysubmission4.rest.service;

import com.ibeybeh.mysubmission4.items.SearchTvShowModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.ibeybeh.mysubmission4.rest.client.ApiClient.API_KEY;

public interface GetSearchTvShowInterface {

    @GET("3/search/tv?api_key="+API_KEY)
    Call<SearchTvShowModel> getSearchTvShow(
            @Query("query") String tvShowQuery
    );
}
