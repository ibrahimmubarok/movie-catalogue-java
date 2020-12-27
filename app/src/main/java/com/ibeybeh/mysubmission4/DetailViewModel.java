package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.MoviesDetailItems;
import com.ibeybeh.mysubmission4.items.SearchMoviesItems;
import com.ibeybeh.mysubmission4.items.SearchTvShowsItems;
import com.ibeybeh.mysubmission4.items.TvShowDetailItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.ibeybeh.mysubmission4.SearchViewModel.getQuery;

public class DetailViewModel extends ViewModel {

    private AsyncHttpClient client;
    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";
    private MutableLiveData<ArrayList<MoviesDetailItems>> listMoviesDetail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShowDetailItems>> listTvShowDetail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<SearchMoviesItems>> listSearchMoviesDetail = new MutableLiveData<>();
    private MutableLiveData<ArrayList<SearchTvShowsItems>> listSearchTvShowsDetail = new MutableLiveData<>();

    public void setMoviesDetailData(){
        client = new AsyncHttpClient();
        final ArrayList<MoviesDetailItems> listMoviesItems = new ArrayList<>();

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
                        MoviesDetailItems items = new MoviesDetailItems();
                        items.setId(movies.getInt("id"));
                        items.setTitle(movies.getString("title"));
                        items.setOverview(movies.getString("overview"));
                        items.setReleaseDate(movies.getString("release_date"));
                        items.setPopularity(String.valueOf(movies.getDouble("popularity")));
                        items.setPosterPath(movies.getString("poster_path"));
                        items.setBackdropPath(movies.getString("backdrop_path"));
                        listMoviesItems.add(items);

                        Log.d("DetailMovies", String.valueOf(items.getId()));
                    }
                    listMoviesDetail.postValue(listMoviesItems);
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

    public void setSearchMoviesDetail(){
        client = new AsyncHttpClient();
        final ArrayList<SearchMoviesItems> listMoviesItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + getQuery();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i=0; i<list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        SearchMoviesItems items = new SearchMoviesItems();
                        items.setId(movies.getInt("id"));
                        items.setTitleSearch(movies.getString("title"));
                        items.setOverviewSearch(movies.getString("overview"));
                        items.setReleaseDateSearch(movies.getString("release_date"));
                        items.setPopularitySearch(String.valueOf(movies.getDouble("popularity")));
                        items.setPosterPathSearch(movies.getString("poster_path"));
                        items.setBackdropPathSearch(movies.getString("backdrop_path"));
                        listMoviesItems.add(items);

                        Log.d("DetailMovies", String.valueOf(items.getId()));
                    }
                    listSearchMoviesDetail.postValue(listMoviesItems);
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

    public void setTvShowDetailData(){
        client = new AsyncHttpClient();
        final ArrayList<TvShowDetailItems> listTvShowItems = new ArrayList<>();
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
                        TvShowDetailItems items = new TvShowDetailItems();
                        items.setId(tvShow.getInt("id"));
                        items.setVoteAverage(String.valueOf(tvShow.getDouble("vote_average")));
                        items.setPopularity(String.valueOf(tvShow.getDouble("popularity")));
                        items.setName(tvShow.getString("name"));
                        items.setBackdropPath(tvShow.getString("backdrop_path"));
                        items.setPosterPath(tvShow.getString("poster_path"));
                        items.setFirstAirDate(tvShow.getString("first_air_date"));
                        items.setOverview(tvShow.getString("overview"));
                        listTvShowItems.add(items);
                    }
                    listTvShowDetail.postValue(listTvShowItems);
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

    public void setSearchTvShowDetail(){
        client = new AsyncHttpClient();
        final ArrayList<SearchTvShowsItems> listSearchTvShowItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+ API_KEY +"&language=en-US&query="+getQuery();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i=0; i<list.length(); i++){
                        JSONObject tvShow = list.getJSONObject(i);
                        SearchTvShowsItems items = new SearchTvShowsItems();
                        items.setId(tvShow.getInt("id"));
                        items.setVoteAverageSearch(String.valueOf(tvShow.getDouble("vote_average")));
                        items.setPopularitySearch(String.valueOf(tvShow.getDouble("popularity")));
                        items.setTitleSearch(tvShow.getString("name"));
                        items.setBackdropPathSearch(tvShow.getString("backdrop_path"));
                        items.setPosterPathSearch(tvShow.getString("poster_path"));
                        items.setReleaseDateSearch(tvShow.getString("first_air_date"));
                        items.setOverviewSearch(tvShow.getString("overview"));
                        listSearchTvShowItems.add(items);
                    }
                    listSearchTvShowsDetail.postValue(listSearchTvShowItems);
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

    public LiveData<ArrayList<MoviesDetailItems>> getMoviesDetail(){
        return listMoviesDetail;
    }

    public LiveData<ArrayList<TvShowDetailItems>> getTvShowDetail() {return listTvShowDetail; }

    public LiveData<ArrayList<SearchMoviesItems>> getSearchMoviesDetail() {
        return listSearchMoviesDetail;
    }

    public LiveData<ArrayList<SearchTvShowsItems>> getSearchTvShowsDetail(){
        return listSearchTvShowsDetail;
    }
}
