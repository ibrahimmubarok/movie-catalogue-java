package com.ibeybeh.myfavoritemovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;
import static com.ibeybeh.myfavoritemovies.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadMoviesCallback {

    private FavoriteMoviesAdapter favoriteMoviesAdapter;
    private MainActivity.DataObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Favorite Movies");

        RecyclerView rvFav = findViewById(R.id.rv_favorite_movies);
        favoriteMoviesAdapter = new FavoriteMoviesAdapter(this);
        rvFav.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvFav.setHasFixedSize(true);
        rvFav.setAdapter(favoriteMoviesAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true,myObserver);
        new getData(this, this).execute();
    }

    private static class getData extends AsyncTask<Void, Void, Cursor>{

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private getData(Context weakContext, LoadMoviesCallback weakCallback) {
            this.weakContext = new WeakReference<>(weakContext);
            this.weakCallback = new WeakReference<>(weakCallback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI,
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

    @Override
    public void postExecute(Cursor favorite) {
        ArrayList<FavoriteMoviesItem> listMovies = mapCursorToArrayList(favorite);
        if (listMovies.size() > 0){
            favoriteMoviesAdapter.setList(listMovies);
        }else {
            Toast.makeText(this, "Tidak Ada Data Saat Ini", Toast.LENGTH_SHORT).show();
            favoriteMoviesAdapter.setList(new ArrayList<FavoriteMoviesItem>());
        }
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public static class DataObserver extends ContentObserver{
        final Context context;
        public DataObserver(Handler handler, Context context){
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}