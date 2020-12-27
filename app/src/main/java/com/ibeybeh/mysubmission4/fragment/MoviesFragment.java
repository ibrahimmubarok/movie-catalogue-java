package com.ibeybeh.mysubmission4.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibeybeh.mysubmission4.MainViewModel;
import com.ibeybeh.mysubmission4.MoviesViewModel;
import com.ibeybeh.mysubmission4.activity.MoviesDetailActivity;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.adapter.MoviesUpComingAdapter;
import com.ibeybeh.mysubmission4.customclick.ItemClickSupport;
import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_BACKDROP_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_DESC_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_POPULARITY_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_POSTER_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_RATE_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_RELEASE_DATE_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_TITLE_MOVIE;


public class MoviesFragment extends Fragment {

    private ProgressBar progressBar;
    private MoviesUpComingAdapter adapter;
    private ArrayList<MoviesUpComingItems> moviesItems = new ArrayList<>();

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        MainViewModel moviesViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        moviesViewModel.getMoviesUpComing().observe(this, getMoviesUpComing);
        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getLiveDataMoviesItems().observe(this, getMoviesUpComing);

        adapter = new MoviesUpComingAdapter(this.getContext(), moviesItems);
        adapter.notifyDataSetChanged();

        RecyclerView rvMovies = view.findViewById(R.id.recycler_view_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        rvMovies.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_bar_movies);

//        moviesViewModel.setMoviesUpComing();
        moviesViewModel.asyncMovies();
        showLoading(true);

        onClick(rvMovies);
    }

    private void onClick(RecyclerView recyclerView) {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), moviesItems.get(position).getTitle(), Toast.LENGTH_SHORT).show();

//                MoviesUpComingItems movies = new MoviesUpComingItems();

//                MoviesDetailActivity.homeMovies = "moviesFragment";
//                MoviesDetailActivity.id = moviesUpComingItems.get(position).getId();
//                MoviesDetailActivity.titleBar = moviesUpComingItems.get(position).getTitle();
//                MoviesDetailActivity.rating = moviesUpComingItems.get(position).getVoteAverage();

                Intent detailIntent = new Intent(getContext(), MoviesDetailActivity.class);
                detailIntent.putExtra(KEY_TITLE_MOVIE, moviesItems.get(position).getTitle());
                detailIntent.putExtra(KEY_BACKDROP_MOVIE, moviesItems.get(position).getBackdropPath());
                detailIntent.putExtra(KEY_DESC_MOVIE, moviesItems.get(position).getOverview());
                detailIntent.putExtra(KEY_POPULARITY_MOVIE, moviesItems.get(position).getPopularity());
                detailIntent.putExtra(KEY_POSTER_MOVIE, moviesItems.get(position).getPosterPath());
                detailIntent.putExtra(KEY_RELEASE_DATE_MOVIE, moviesItems.get(position).getReleaseDate());
                detailIntent.putExtra(KEY_RATE_MOVIE, moviesItems.get(position).getVoteAverage());
//                detailIntent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movies);
                startActivity(detailIntent);
            }
        });
    }

    private Observer<ArrayList<MoviesUpComingItems>> getMoviesUpComing = new Observer<ArrayList<MoviesUpComingItems>>() {
        @Override
        public void onChanged(ArrayList<MoviesUpComingItems> moviesUpComingItems) {
            if (moviesUpComingItems != null){
                adapter.setData(moviesUpComingItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
