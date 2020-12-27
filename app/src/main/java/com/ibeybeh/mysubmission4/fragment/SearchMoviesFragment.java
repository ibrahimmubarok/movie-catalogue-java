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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.SearchMoviesViewModel;
import com.ibeybeh.mysubmission4.SearchViewModel;
import com.ibeybeh.mysubmission4.activity.MoviesDetailActivity;
import com.ibeybeh.mysubmission4.adapter.SearchMoviesAdapter;
import com.ibeybeh.mysubmission4.customclick.ItemClickSupport;
import com.ibeybeh.mysubmission4.items.SearchMoviesItems;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_BACKDROP_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_DESC_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_POPULARITY_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_POSTER_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_RATE_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_RELEASE_DATE_MOVIE;
import static com.ibeybeh.mysubmission4.activity.MoviesDetailActivity.KEY_TITLE_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMoviesFragment extends Fragment {

    private ProgressBar progressBar;
    private SearchMoviesAdapter adapter;
    private ArrayList<SearchMoviesItems> searchMoviesItems= new ArrayList<>();
    private String query ;

    public SearchMoviesFragment() {
        // Required empty public constructor
    }

    public SearchMoviesFragment(String query){
        this.query = query;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
//        searchViewModel.getSearchMovies().observe(this, getSearchMovies);
        SearchMoviesViewModel searchMoviesViewModel = ViewModelProviders.of(this).get(SearchMoviesViewModel.class);
        searchMoviesViewModel.getLiveDataSearchMoviesItems().observe(this, getSearchMovies);

        adapter = new SearchMoviesAdapter(this.getContext(), searchMoviesItems);
        adapter.notifyDataSetChanged();

        Log.d("QUERYYYY MOVIES", query);

        RecyclerView rvMovies = view.findViewById(R.id.recycler_view_search_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        rvMovies.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progress_bar_search_movies);
        TextView tvSearchResult = view.findViewById(R.id.tv_search_movies_result);

//        searchViewModel.setSearchMovies();
        searchMoviesViewModel.asyngSearchMovies(query);
        showLoading(true);

        if (query != null){
            tvSearchResult.setText(String.format("%s %s", getResources().getString(R.string.search_result), query));
        }

        onClick(rvMovies);
    }

    private void onClick(RecyclerView recyclerView) {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Toast.makeText(getContext(), searchMoviesItems.get(position).getTitleSearch(), Toast.LENGTH_SHORT).show();

                SearchMoviesItems movies = new SearchMoviesItems();

//                MoviesDetailActivity.homeMovies = "searchMoviesFragment";
//                MoviesDetailActivity.id = searchMoviesItems.get(position).getId();
//                MoviesDetailActivity.titleBar = searchMoviesItems.get(position).getTitleSearch();
//                MoviesDetailActivity.rating = searchMoviesItems.get(position).getVoteAverageSearch();

                Intent detailIntent = new Intent(getContext(), MoviesDetailActivity.class);
//                detailIntent.putExtra(MoviesDetailActivity.EXTRA_SEARCH_MOVIE, movies);
                detailIntent.putExtra(KEY_TITLE_MOVIE, searchMoviesItems.get(position).getTitleSearch());
                detailIntent.putExtra(KEY_BACKDROP_MOVIE, searchMoviesItems.get(position).getBackdropPathSearch());
                detailIntent.putExtra(KEY_DESC_MOVIE, searchMoviesItems.get(position).getOverviewSearch());
                detailIntent.putExtra(KEY_POPULARITY_MOVIE, searchMoviesItems.get(position).getPopularitySearch());
                detailIntent.putExtra(KEY_POSTER_MOVIE, searchMoviesItems.get(position).getPosterPathSearch());
                detailIntent.putExtra(KEY_RELEASE_DATE_MOVIE, searchMoviesItems.get(position).getReleaseDateSearch());
                detailIntent.putExtra(KEY_RATE_MOVIE, searchMoviesItems.get(position).getVoteAverageSearch());
                startActivity(detailIntent);
            }
        });
    }

    private Observer<ArrayList<SearchMoviesItems>> getSearchMovies = new Observer<ArrayList<SearchMoviesItems>>() {
        @Override
        public void onChanged(ArrayList<SearchMoviesItems> searchMoviesItems) {
            if (searchMoviesItems != null){
                adapter.setData(searchMoviesItems);
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
