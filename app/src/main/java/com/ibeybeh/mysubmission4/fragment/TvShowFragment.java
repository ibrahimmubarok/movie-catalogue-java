package com.ibeybeh.mysubmission4.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibeybeh.mysubmission4.MainViewModel;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.TvShowViewModel;
import com.ibeybeh.mysubmission4.activity.MoviesDetailActivity;
import com.ibeybeh.mysubmission4.activity.TvShowDetailActivity;
import com.ibeybeh.mysubmission4.adapter.TvShowUpComingAdapter;
import com.ibeybeh.mysubmission4.customclick.ItemClickSupport;
import com.ibeybeh.mysubmission4.items.TvShowUpComingItems;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_BACKDROP_TV_SHOW;
import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_DESC_TV_SHOW;
import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_NAME_TV_SHOW;
import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_ON_AIR_DATE_TV_SHOW;
import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_POPULARITY_TV_SHOW;
import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_POSTER_TV_SHOW;
import static com.ibeybeh.mysubmission4.activity.TvShowDetailActivity.KEY_RATE_TV_SHOW;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TvShowUpComingAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<TvShowUpComingItems> tvShowItems = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        MainViewModel tvShowViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        tvShowViewModel.getTvShowUpComing().observe(this, getTvShowUpComing);
        TvShowViewModel tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getLiveDataTvShowItems().observe(this, getTvShowUpComing);

        adapter = new TvShowUpComingAdapter(this.getContext(), tvShowItems);
        adapter.notifyDataSetChanged();

        RecyclerView rvTvShow = view.findViewById(R.id.recycler_view_tv_show);
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        rvTvShow.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_bar_tv_show);

        tvShowViewModel.asyncTvShow();
        showLoading(true);

        onClick(rvTvShow);
    }

    private void onClick(RecyclerView recyclerView) {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), tvShowItems.get(position).getName(), Toast.LENGTH_SHORT).show();

//                TvShowDetailActivity.rating = tvShowUpComingItems.get(position).getVoteAverage();
//                TvShowDetailActivity.homeTvShows = "tvShowsFragment";
//                TvShowDetailActivity.id = tvShowUpComingItems.get(position).getId();
//                TvShowDetailActivity.titleBar = tvShowUpComingItems.get(position).getName();

                Intent detailIntent = new Intent(getContext(), TvShowDetailActivity.class);
                detailIntent.putExtra(KEY_NAME_TV_SHOW, tvShowItems.get(position).getName());
                detailIntent.putExtra(KEY_BACKDROP_TV_SHOW, tvShowItems.get(position).getBackdropPath());
                detailIntent.putExtra(KEY_ON_AIR_DATE_TV_SHOW, tvShowItems.get(position).getFirstAirDate());
                detailIntent.putExtra(KEY_DESC_TV_SHOW, tvShowItems.get(position).getOverview());
                detailIntent.putExtra(KEY_POPULARITY_TV_SHOW, tvShowItems.get(position).getPopularity());
                detailIntent.putExtra(KEY_POSTER_TV_SHOW, tvShowItems.get(position).getPosterPath());
                detailIntent.putExtra(KEY_RATE_TV_SHOW, tvShowItems.get(position).getVoteAverage());
                startActivity(detailIntent);
            }
        });
    }

    private Observer<ArrayList<TvShowUpComingItems>> getTvShowUpComing = new Observer<ArrayList<TvShowUpComingItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowUpComingItems> tvShowUpComingItems) {
            if(tvShowUpComingItems != null){
                adapter.setData(tvShowUpComingItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
