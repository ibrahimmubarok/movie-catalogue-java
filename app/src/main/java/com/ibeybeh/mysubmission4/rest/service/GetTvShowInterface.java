package com.ibeybeh.mysubmission4.rest.service;

import com.ibeybeh.mysubmission4.items.TvShowModel;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.ibeybeh.mysubmission4.rest.client.ApiClient.API_KEY;

public interface GetTvShowInterface {
    @GET("3/tv/on_the_air?api_key="+API_KEY+"&language=en-US&page=1")
    Call<TvShowModel> getTvShow();
}
