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
import com.ibeybeh.mysubmission4.adapter.FavoriteMoviesAdapter;
import com.ibeybeh.mysubmission4.entity.FavoriteMoviesItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;
import static com.ibeybeh.mysubmission4.helper.MappingHelper.mapCursorToArrayListMovies;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment implements LoadFavoriteCallback {

    private ProgressBar progressBar;

    private FavoriteMoviesAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavMovies = view.findViewById(R.id.recycler_view_fav_movies);
        progressBar = view.findViewById(R.id.progress_fav_movies);

        rvFavMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvFavMovies.setHasFixedSize(true);

        adapter = new FavoriteMoviesAdapter(getContext());
        rvFavMovies.setAdapter(adapter);

        if (savedInstanceState == null){
            new LoadFavMoviesAsync(getContext(), this).execute();
        }else{
            ArrayList<FavoriteMoviesItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapter.setListFavMovies(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFavMovies());
    }

    @Override
    public void preExecute() {
        setVisibilityProgressBar(true);
    }

    @Override
    public void postExecute(Cursor favMovies) {
        setVisibilityProgressBar(false);

        ArrayList<FavoriteMoviesItem> listFavMovies = mapCursorToArrayListMovies(favMovies);
        if (listFavMovies.size() > 0){
            adapter.setListFavMovies(listFavMovies);
        }else{
            adapter.setListFavMovies(new ArrayList<FavoriteMoviesItem>());
            Toast.makeText(getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadFavMoviesAsync extends AsyncTask<Void, Void, Cursor>{

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadFavMoviesAsync(Context context, LoadFavoriteCallback callback){
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
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
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
