package com.ibeybeh.myfavoritemovies;

import android.database.Cursor;

public interface LoadMoviesCallback {
    void postExecute(Cursor favorite);
    void preExecute();
}
