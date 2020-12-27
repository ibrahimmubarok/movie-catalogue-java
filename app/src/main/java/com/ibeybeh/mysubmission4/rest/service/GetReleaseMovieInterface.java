package com.ibeybeh.mysubmission4.rest.service;

import com.ibeybeh.mysubmission4.items.MoviesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.ibeybeh.mysubmission4.rest.client.ApiClient.API_KEY;

public interface GetReleaseMovieInterface {
    @GET("3/discover/movie?api_key="+API_KEY)
    Call<MoviesModel> getReleaseMovies(
            @Query("primary_release_date.gte") String time
    );
}
