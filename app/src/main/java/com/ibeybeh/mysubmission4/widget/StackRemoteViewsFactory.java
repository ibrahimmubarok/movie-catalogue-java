package com.ibeybeh.mysubmission4.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ibeybeh.mysubmission4.BuildConfig;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.entity.FavoriteMoviesItem;

import java.util.concurrent.ExecutionException;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private final Context mContext;

    public StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null){
            cursor.close();
        }

        final long identity = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identity);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        FavoriteMoviesItem favoriteMoviesItem = getItem(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(favoriteMoviesItem.getPosterUrl())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    private FavoriteMoviesItem getItem(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new FavoriteMoviesItem(cursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
