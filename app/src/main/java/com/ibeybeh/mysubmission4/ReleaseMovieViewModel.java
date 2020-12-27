package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.alarm.NotificationReleaseItem;
import com.ibeybeh.mysubmission4.items.MoviesModel;
import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;
import com.ibeybeh.mysubmission4.rest.client.ApiClient;
import com.ibeybeh.mysubmission4.rest.service.GetReleaseMovieInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseMovieViewModel extends ViewModel {
    private final String TAG = ReleaseMovieViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<NotificationReleaseItem>> mutableLiveDataReleaseMovies = new MutableLiveData<>();

    public void asyncRelaseMovies(String time){
        GetReleaseMovieInterface getReleaseMovieInterface = ApiClient.getClient().create(GetReleaseMovieInterface.class);

        final ArrayList<NotificationReleaseItem> listMovies = new ArrayList<>();

        Call<MoviesModel> call = getReleaseMovieInterface.getReleaseMovies(time);
        call.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                if (response.isSuccessful()){
                    if ((response.body().getDataMovies() != null)){
                        ArrayList<MoviesUpComingItems> moviesItems = response.body().getDataMovies();

                        for (MoviesUpComingItems mi : moviesItems){
                            NotificationReleaseItem notificationReleaseItem = new NotificationReleaseItem();

                            notificationReleaseItem.setId(mi.getId());
                            notificationReleaseItem.setTitle(mi.getTitle());

                            listMovies.add(notificationReleaseItem);
                        }
                        mutableLiveDataReleaseMovies.postValue(listMovies);
                    }
                }else{
                    return;
                }
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }

    public LiveData<ArrayList<NotificationReleaseItem>> getLiveDataReleaseMoviesItems(){
        return mutableLiveDataReleaseMovies;
    }
}
