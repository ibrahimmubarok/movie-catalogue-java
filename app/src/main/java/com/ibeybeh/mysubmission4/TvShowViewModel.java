package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.TvShowModel;
import com.ibeybeh.mysubmission4.items.TvShowUpComingItems;
import com.ibeybeh.mysubmission4.rest.client.ApiClient;
import com.ibeybeh.mysubmission4.rest.service.GetTvShowInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowViewModel extends ViewModel {
    private final String TAG = TvShowViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<TvShowUpComingItems>> mutableLiveDataTvShow = new MutableLiveData<>();

    public void asyncTvShow(){
        GetTvShowInterface getTvShowInterface = ApiClient.getClient().create(GetTvShowInterface.class);

        final ArrayList<TvShowUpComingItems> listTvShow = new ArrayList<>();

        Call<TvShowModel> call = getTvShowInterface.getTvShow();
        call.enqueue(new Callback<TvShowModel>() {
            @Override
            public void onResponse(Call<TvShowModel> call, Response<TvShowModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getTvShowModel() != null){
                        ArrayList<TvShowUpComingItems> tvShowItems = response.body().getTvShowModel();

                        for(TvShowUpComingItems ti : tvShowItems){
                            TvShowUpComingItems tvShowUpComingItems = new TvShowUpComingItems();

                            tvShowUpComingItems.setId(ti.getId());
                            tvShowUpComingItems.setBackdropPath("https://image.tmdb.org/t/p/original"+ti.getBackdropPath());
                            tvShowUpComingItems.setPosterPath("https://image.tmdb.org/t/p/original"+ti.getPosterPath());
                            tvShowUpComingItems.setFirstAirDate(ti.getFirstAirDate());
                            tvShowUpComingItems.setName(ti.getName());
                            tvShowUpComingItems.setOverview(ti.getOverview());
                            tvShowUpComingItems.setPopularity(ti.getPopularity());
                            tvShowUpComingItems.setVoteAverage(ti.getVoteAverage());

                            Log.d("ID", String.valueOf(ti.getId()));
//                            Log.d("Backdrop", ti.getBackdropPath());
                            Log.d("Overview", ti.getOverview());
                            Log.d("Popularity", ti.getPopularity());
                            Log.d("Poster", ti.getPosterPath());
                            Log.d("Release", ti.getFirstAirDate());
                            Log.d("Title", ti.getName());

                            listTvShow.add(tvShowUpComingItems);
                        }
                        mutableLiveDataTvShow.postValue(listTvShow);
                    }
                }else{
                    return;
                }
            }

            @Override
            public void onFailure(Call<TvShowModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<TvShowUpComingItems>> getLiveDataTvShowItems(){
        return mutableLiveDataTvShow;
    }
}
