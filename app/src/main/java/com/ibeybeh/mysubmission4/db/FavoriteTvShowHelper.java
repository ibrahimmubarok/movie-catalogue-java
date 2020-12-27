package com.ibeybeh.mysubmission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.TABLE_FAVORITE_TV_SHOWS;

public class FavoriteTvShowHelper {
    private static final String DATABASE_TABLE_TV_SHOWS = TABLE_FAVORITE_TV_SHOWS;
    private static DatabaseHelper databaseHelper;
    private static FavoriteTvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavoriteTvShowHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteTvShowHelper getInstance (Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteTvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryByIdProviders(String id){
        return database.query(DATABASE_TABLE_TV_SHOWS,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE_TV_SHOWS,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public long insertProviders(ContentValues values){
        return database.insert(DATABASE_TABLE_TV_SHOWS, null, values);
    }

    public int deleteProviders(String id){
        return database.delete(DATABASE_TABLE_TV_SHOWS, _ID + " = ?", new String[]{id});
    }
}
