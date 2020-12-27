package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.SearchTvShowModel;
import com.ibeybeh.mysubmission4.items.SearchTvShowsItems;
import com.ibeybeh.mysubmission4.rest.client.ApiClient;
import com.ibeybeh.mysubmission4.rest.service.GetSearchTvShowInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowViewModel extends ViewModel {
    private final String TAG = SearchTvShowModel.class.getSimpleName();

    private MutableLiveData<ArrayList<SearchTvShowsItems>> mutableLiveDataSearchTvShow = new MutableLiveData<>();

    public void asyncTvShow(String tvShowQuery){
        GetSearchTvShowInterface getSearchTvShowInterface = ApiClient.getClient().create(GetSearchTvShowInterface.class);

        final ArrayList<SearchTvShowsItems> listSearchTvShow = new ArrayList<>();

        Call<SearchTvShowModel> call = getSearchTvShowInterface.getSearchTvShow(tvShowQuery);
        call.enqueue(new Callback<SearchTvShowModel>() {
            @Override
            public void onResponse(Call<SearchTvShowModel> call, Response<SearchTvShowModel> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        ArrayList<SearchTvShowsItems> tvShowsItems = response.body().getSearchTvShowModel();

                        for (SearchTvShowsItems tvs : tvShowsItems) {
                            SearchTvShowsItems searchTvShowsItems = new SearchTvShowsItems();

                            searchTvShowsItems.setId(tvs.getId());
                            if (tvs.getBackdropPathSearch() == null) {
                                searchTvShowsItems.setBackdropPathSearch("https://image.tmdb.org/t/p/original" + tvs.getPosterPathSearch());
                            } else {
                                searchTvShowsItems.setBackdropPathSearch("https://image.tmdb.org/t/p/original" + tvs.getBackdropPathSearch());
                            }
                            searchTvShowsItems.setOverviewSearch(tvs.getOverviewSearch());
                            searchTvShowsItems.setPopularitySearch(tvs.getPopularitySearch());
                            searchTvShowsItems.setPosterPathSearch("https://image.tmdb.org/t/p/original" + tvs.getPosterPathSearch());
                            searchTvShowsItems.setReleaseDateSearch(tvs.getReleaseDateSearch());
                            searchTvShowsItems.setTitleSearch(tvs.getTitleSearch());
                            searchTvShowsItems.setVoteAverageSearch(tvs.getVoteAverageSearch());

                            listSearchTvShow.add(searchTvShowsItems);
                        }
                        mutableLiveDataSearchTvShow.postValue(listSearchTvShow);
                    }
                }else{
                    return;
                }
            }

            @Override
            public void onFailure(Call<SearchTvShowModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<SearchTvShowsItems>> getLiveDataSearchTvShowItems(){
        return mutableLiveDataSearchTvShow;
    }
}
