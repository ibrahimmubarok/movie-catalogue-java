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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.SearchTvShowViewModel;
import com.ibeybeh.mysubmission4.SearchViewModel;
import com.ibeybeh.mysubmission4.activity.TvShowDetailActivity;
import com.ibeybeh.mysubmission4.adapter.SearchTvShowsAdapter;
import com.ibeybeh.mysubmission4.customclick.ItemClickSupport;
import com.ibeybeh.mysubmission4.items.SearchTvShowsItems;

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
public class SearchTvShowsFragment extends Fragment {

    private ProgressBar progressBar;
    private SearchTvShowsAdapter adapter;
    private ArrayList<SearchTvShowsItems> searchTvShowsItems= new ArrayList<>();
    private String query ;

    public SearchTvShowsFragment() {
        // Required empty public constructor
    }

    public SearchTvShowsFragment(String query){
        this.query = query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
//        searchViewModel.getSearchTvShows().observe(this, getSearchTvShows);
        SearchTvShowViewModel searchTvShowViewModel = ViewModelProviders.of(this).get(SearchTvShowViewModel.class);
        searchTvShowViewModel.getLiveDataSearchTvShowItems().observe(this, getSearchTvShows);

        adapter = new SearchTvShowsAdapter(this.getContext(), searchTvShowsItems);
        adapter.notifyDataSetChanged();

        RecyclerView rvMovies = view.findViewById(R.id.recycler_view_search_tv_shows);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        rvMovies.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_bar_search_tv_shows);
        TextView tvSearchResult = view.findViewById(R.id.tv_search_tv_shows_result);

        if (query != null){
            tvSearchResult.setText(String.format("%s %s", getResources().getString(R.string.search_result), query));
        }

        Log.d("QUERYYYY TV SHOW", query);

//        searchViewModel.setSearchTvShows();
        searchTvShowViewModel.asyncTvShow(query);
        showLoading(true);

        onClick(rvMovies);
    }

    private void onClick(RecyclerView recyclerView) {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), searchTvShowsItems.get(position).getTitleSearch(), Toast.LENGTH_SHORT).show();

//                SearchTvShowsItems tvShows = new SearchTvShowsItems();

//                TvShowDetailActivity.homeTvShows = "tvShowsSearchFragment";
//                TvShowDetailActivity.id = searchTvShowsItems.get(position).getId();
//                TvShowDetailActivity.titleBar = searchTvShowsItems.get(position).getTitleSearch();
//                TvShowDetailActivity.rating = searchTvShowsItems.get(position).getVoteAverageSearch();

                Intent detailIntent = new Intent(getContext(), TvShowDetailActivity.class);
                detailIntent.putExtra(KEY_NAME_TV_SHOW, searchTvShowsItems.get(position).getTitleSearch());
                detailIntent.putExtra(KEY_BACKDROP_TV_SHOW, searchTvShowsItems.get(position).getBackdropPathSearch());
                detailIntent.putExtra(KEY_ON_AIR_DATE_TV_SHOW, searchTvShowsItems.get(position).getReleaseDateSearch());
                detailIntent.putExtra(KEY_DESC_TV_SHOW, searchTvShowsItems.get(position).getOverviewSearch());
                detailIntent.putExtra(KEY_POPULARITY_TV_SHOW, searchTvShowsItems.get(position).getPopularitySearch());
                detailIntent.putExtra(KEY_POSTER_TV_SHOW, searchTvShowsItems.get(position).getPosterPathSearch());
                detailIntent.putExtra(KEY_RATE_TV_SHOW, searchTvShowsItems.get(position).getVoteAverageSearch());
//                detailIntent.putExtra(TvShowDetailActivity.EXTRA_SEARCH_TV_SHOW, tvShows);
                startActivity(detailIntent);
            }
        });
    }

    private Observer<ArrayList<SearchTvShowsItems>> getSearchTvShows = new Observer<ArrayList<SearchTvShowsItems>>() {
        @Override
        public void onChanged(ArrayList<SearchTvShowsItems> searchTvShowsItems) {
            if (searchTvShowsItems != null){
                adapter.setData(searchTvShowsItems);
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
