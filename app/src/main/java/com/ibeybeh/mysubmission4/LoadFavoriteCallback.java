package com.ibeybeh.mysubmission4;

import android.database.Cursor;

public interface LoadFavoriteCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
