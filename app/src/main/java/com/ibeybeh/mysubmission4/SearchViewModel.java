package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.SearchMoviesItems;
import com.ibeybeh.mysubmission4.items.SearchTvShowsItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchViewModel extends ViewModel {
    private AsyncHttpClient client;
    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";
    private MutableLiveData<ArrayList<SearchMoviesItems>> listSearchMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<SearchTvShowsItems>> listSearchTvShows = new MutableLiveData<>();
    public static String query;

    public void setSearchMovies(){
        client = new AsyncHttpClient();
        final ArrayList<SearchMoviesItems> listMoviesItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + getQuery(); //Tambahin Searchnya

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject searchMovies = list.getJSONObject(i);
                        SearchMoviesItems searchMoviesItems = new SearchMoviesItems();
                        searchMoviesItems.setId(searchMovies.getInt("id"));
                        searchMoviesItems.setTitleSearch(searchMovies.getString("title"));
                        searchMoviesItems.setOverviewSearch(searchMovies.getString("overview"));
                        searchMoviesItems.setReleaseDateSearch(searchMovies.getString("release_date"));
                        searchMoviesItems.setPopularitySearch(String.valueOf(searchMovies.getDouble("popularity")));
                        searchMoviesItems.setVoteAverageSearch(String.valueOf(searchMovies.getDouble("vote_average")));
                        searchMoviesItems.setPosterPathSearch(searchMovies.getString("poster_path"));
                        searchMoviesItems.setBackdropPathSearch(searchMovies.getString("backdrop_path"));
                        listMoviesItems.add(searchMoviesItems);
                    }
                    listSearchMovies.postValue(listMoviesItems);
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

    public static String getQuery() {
        return query;
    }

    public static void setQuery(String query) {
        SearchViewModel.query = query;
    }

    public void setSearchTvShows(){
        client = new AsyncHttpClient();
        final ArrayList<SearchTvShowsItems> listTvShowItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+getQuery();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject searchTvShows = list.getJSONObject(i);
                        SearchTvShowsItems searchTvShowsItems= new SearchTvShowsItems();
                        searchTvShowsItems.setId(searchTvShows.getInt("id"));
                        searchTvShowsItems.setTitleSearch(searchTvShows.getString("name"));
                        searchTvShowsItems.setOverviewSearch(searchTvShows.getString("overview"));
                        searchTvShowsItems.setReleaseDateSearch(searchTvShows.getString("first_air_date"));
                        searchTvShowsItems.setPopularitySearch(String.valueOf(searchTvShows.getDouble("popularity")));
                        searchTvShowsItems.setVoteAverageSearch(String.valueOf(searchTvShows.getDouble("vote_average")));
                        searchTvShowsItems.setPosterPathSearch(searchTvShows.getString("poster_path"));
                        searchTvShowsItems.setBackdropPathSearch(searchTvShows.getString("backdrop_path"));
                        listTvShowItems.add(searchTvShowsItems);
                    }
                    listSearchTvShows.postValue(listTvShowItems);
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

    public LiveData<ArrayList<SearchMoviesItems>> getSearchMovies(){
        return listSearchMovies;
    }

    public LiveData<ArrayList<SearchTvShowsItems>> getSearchTvShows(){
        return listSearchTvShows;
    }
}
