package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.SearchMoviesItems;
import com.ibeybeh.mysubmission4.items.SearchMoviesModel;
import com.ibeybeh.mysubmission4.rest.client.ApiClient;
import com.ibeybeh.mysubmission4.rest.service.GetSearchMoviesInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMoviesViewModel extends ViewModel {
    private final String TAG = SearchMoviesViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<SearchMoviesItems>> mutableLiveDataSearchMovies = new MutableLiveData<>();

    public void asyngSearchMovies(String movieQuery){
        GetSearchMoviesInterface getSearchMoviesInterface = ApiClient.getClient().create(GetSearchMoviesInterface.class);

        final ArrayList<SearchMoviesItems> listSearchMovies = new ArrayList<>();

        Call<SearchMoviesModel> call = getSearchMoviesInterface.getSearchMovies(movieQuery);
        call.enqueue(new Callback<SearchMoviesModel>() {
            @Override
            public void onResponse(Call<SearchMoviesModel> call, Response<SearchMoviesModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<SearchMoviesItems> moviesItems = response.body().getSearchDataMovies();

                        for (SearchMoviesItems sm : moviesItems) {
                            SearchMoviesItems searchMoviesItems = new SearchMoviesItems();

                            searchMoviesItems.setId(sm.getId());
                            if (sm.getBackdropPathSearch() == null) {
                                searchMoviesItems.setBackdropPathSearch("https://image.tmdb.org/t/p/original" + sm.getPosterPathSearch());
                            } else {
                                searchMoviesItems.setBackdropPathSearch("https://image.tmdb.org/t/p/original" + sm.getBackdropPathSearch());
                            }
                            searchMoviesItems.setOverviewSearch(sm.getOverviewSearch());
                            searchMoviesItems.setPopularitySearch(sm.getPopularitySearch());
                            searchMoviesItems.setPosterPathSearch("https://image.tmdb.org/t/p/original" + sm.getPosterPathSearch());
                            searchMoviesItems.setReleaseDateSearch(sm.getReleaseDateSearch());
                            searchMoviesItems.setTitleSearch(sm.getTitleSearch());
                            searchMoviesItems.setVoteAverageSearch(sm.getVoteAverageSearch());

                            listSearchMovies.add(searchMoviesItems);
                        }
                        mutableLiveDataSearchMovies.postValue(listSearchMovies);
                    }
                }else{
                    return;
                }
            }

            @Override
            public void onFailure(Call<SearchMoviesModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<SearchMoviesItems>> getLiveDataSearchMoviesItems(){
        return mutableLiveDataSearchMovies;
    }
}
