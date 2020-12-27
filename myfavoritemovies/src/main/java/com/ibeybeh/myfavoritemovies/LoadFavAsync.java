package com.ibeybeh.myfavoritemovies;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;

import java.lang.ref.WeakReference;

import static com.ibeybeh.myfavoritemovies.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;

public class LoadFavAsync extends AsyncTask<Void, Void, Cursor> {

    private final WeakReference<Context> weakContext;
    private final WeakReference<LoadMoviesCallback> weakCallback;

    public LoadFavAsync(Context weakContext, LoadMoviesCallback weakCallback) {
        this.weakContext = new WeakReference<>(weakContext);
        this.weakCallback = new WeakReference<>(weakCallback);
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

    public static class DataObserver extends ContentObserver{
        final Context context;
        public DataObserver(Handler handler, Context context){
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadFavAsync(context, (LoadMoviesCallback) context).execute();
        }
    }
}
