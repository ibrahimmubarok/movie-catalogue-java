package com.ibeybeh.mysubmission4.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibeybeh.mysubmission4.LoadFavoriteCallback;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.adapter.FavoriteTvShowAdapter;
import com.ibeybeh.mysubmission4.entity.FavoriteTvShowItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.CONTENT_URI;
import static com.ibeybeh.mysubmission4.helper.MappingHelper.mapCursorToArrayListTvShow;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowsFragment extends Fragment implements LoadFavoriteCallback {

    private ProgressBar progressBar;

    private FavoriteTvShowAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteTvShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavTvShows = view.findViewById(R.id.recycler_view_fav_tv_show);
        progressBar = view.findViewById(R.id.progress_fav_tv_show);

        rvFavTvShows.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvFavTvShows.setHasFixedSize(true);

        adapter = new FavoriteTvShowAdapter(getContext());
        rvFavTvShows.setAdapter(adapter);

        if (savedInstanceState == null){
            new LoadFavTvShowAsync(getContext(), this).execute();
        }else{
            ArrayList<FavoriteTvShowItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setListFavTvShows(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFavTvShows());
    }

    @Override
    public void preExecute() {
        setVisibilityProgressBar(true);
    }

    @Override
    public void postExecute(Cursor favTvShow) {
        setVisibilityProgressBar(false);

        ArrayList<FavoriteTvShowItem> listFavTvShow = mapCursorToArrayListTvShow(favTvShow);
        if (listFavTvShow.size() > 0){
            adapter.setListFavTvShows(listFavTvShow);
        }else if (listFavTvShow.size() == 0){
            adapter.setListFavTvShows(new ArrayList<FavoriteTvShowItem>());
            Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadFavTvShowAsync extends AsyncTask<Void, Void, Cursor>{

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadFavTvShowAsync(Context context, LoadFavoriteCallback callback){
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor favTvShow) {
            super.onPostExecute(favTvShow);
            weakCallback.get().postExecute(favTvShow);
        }
    }

    private void setVisibilityProgressBar(boolean statement){
        if (statement){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
