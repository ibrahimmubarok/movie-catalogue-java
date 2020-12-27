package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;
import com.ibeybeh.mysubmission4.items.TvShowUpComingItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private AsyncHttpClient client;
    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";
    private MutableLiveData<ArrayList<MoviesUpComingItems>> listMoviesUpComing = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShowUpComingItems>> listTvShowUpComing = new MutableLiveData<>();

    public void setMoviesUpComing(){
        client = new AsyncHttpClient();
        final ArrayList<MoviesUpComingItems> listMoviesItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i=0; i<list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        MoviesUpComingItems moviesUpComingItems = new MoviesUpComingItems();
                        moviesUpComingItems.setId(movies.getInt("id"));
                        moviesUpComingItems.setTitle(movies.getString("title"));
                        moviesUpComingItems.setOverview(movies.getString("overview"));
                        moviesUpComingItems.setReleaseDate(movies.getString("release_date"));
                        moviesUpComingItems.setPopularity(String.valueOf(movies.getDouble("popularity")));
                        moviesUpComingItems.setVoteAverage(String.valueOf(movies.getDouble("vote_average")));
                        moviesUpComingItems.setPosterPath(movies.getString("poster_path"));
                        moviesUpComingItems.setBackdropPath(movies.getString("backdrop_path"));
                        listMoviesItems.add(moviesUpComingItems);
                    }
                    listMoviesUpComing.postValue(listMoviesItems);
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void setTvShowUpComing(){
        client = new AsyncHttpClient();
        final ArrayList<TvShowUpComingItems> listTvShowItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/on_the_air?api_key="+API_KEY+"&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i=0; i<list.length(); i++){
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShowUpComingItems tvShowUpComingItems = new TvShowUpComingItems();
                        tvShowUpComingItems.setId(tvShow.getInt("id"));
                        tvShowUpComingItems.setVoteAverage(String.valueOf(tvShow.getDouble("vote_average")));
                        tvShowUpComingItems.setPopularity(String.valueOf(tvShow.getDouble("popularity")));
                        tvShowUpComingItems.setName(tvShow.getString("name"));
                        tvShowUpComingItems.setBackdropPath(tvShow.getString("backdrop_path"));
                        tvShowUpComingItems.setPosterPath(tvShow.getString("poster_path"));
                        tvShowUpComingItems.setFirstAirDate(tvShow.getString("first_air_date"));
                        tvShowUpComingItems.setOverview(tvShow.getString("overview"));
                        listTvShowItems.add(tvShowUpComingItems);
                    }
                    listTvShowUpComing.postValue(listTvShowItems);
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception : ", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MoviesUpComingItems>> getMoviesUpComing(){
        return listMoviesUpComing;
    }

    public LiveData<ArrayList<TvShowUpComingItems>> getTvShowUpComing(){
        return listTvShowUpComing;
    }
}