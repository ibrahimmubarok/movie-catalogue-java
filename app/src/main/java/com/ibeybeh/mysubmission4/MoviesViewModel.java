package com.ibeybeh.mysubmission4;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ibeybeh.mysubmission4.items.MoviesModel;
import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;
import com.ibeybeh.mysubmission4.rest.client.ApiClient;
import com.ibeybeh.mysubmission4.rest.service.GetMoviesInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {
    private final String TAG = MoviesViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<MoviesUpComingItems>> mutableLiveDataMovies = new MutableLiveData<>();

    public void asyncMovies(){
        GetMoviesInterface getMoviesInterface = ApiClient.getClient().create(GetMoviesInterface.class);

        final ArrayList<MoviesUpComingItems> listMovies = new ArrayList<>();

        Call<MoviesModel> call = getMoviesInterface.getMovies();
        call.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                if (response.isSuccessful()){
                    if ((response.body().getDataMovies() != null)){
                        ArrayList<MoviesUpComingItems> moviesItems = response.body().getDataMovies();

                        for (MoviesUpComingItems mi : moviesItems){
                            MoviesUpComingItems moviesUpComingItems = new MoviesUpComingItems();

                            moviesUpComingItems.setId(mi.getId());
                            moviesUpComingItems.setBackdropPath("https://image.tmdb.org/t/p/original"+mi.getBackdropPath());
                            moviesUpComingItems.setOverview(mi.getOverview());
                            moviesUpComingItems.setPopularity(mi.getPopularity());
                            moviesUpComingItems.setPosterPath("https://image.tmdb.org/t/p/original"+mi.getPosterPath());
                            moviesUpComingItems.setReleaseDate(mi.getReleaseDate());
                            moviesUpComingItems.setTitle(mi.getTitle());
                            moviesUpComingItems.setVoteAverage(mi.getVoteAverage());

                            Log.d("ID", String.valueOf(mi.getId()));
                            Log.d("Backdrop", mi.getBackdropPath());
                            Log.d("Overview", mi.getOverview());
                            Log.d("Popularity", mi.getPopularity());
                            Log.d("Poster", mi.getPosterPath());
                            Log.d("Release", mi.getReleaseDate());
                            Log.d("Title", mi.getTitle());

                            listMovies.add(moviesUpComingItems);
                        }
                        mutableLiveDataMovies.postValue(listMovies);
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

    public LiveData<ArrayList<MoviesUpComingItems>> getLiveDataMoviesItems(){
        return mutableLiveDataMovies;
    }
}
