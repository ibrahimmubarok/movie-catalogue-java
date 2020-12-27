package com.ibeybeh.mysubmission4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.TABLE_FAVORITE_MOVIES;

public class FavoriteMoviesHelper {
    private static final String DATABASE_TABLE_MOVIES = TABLE_FAVORITE_MOVIES;
    private static DatabaseHelper databaseHelper;
    private static FavoriteMoviesHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteMoviesHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteMoviesHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteMoviesHelper(context);
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
        return database.query(DATABASE_TABLE_MOVIES,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE_MOVIES,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public long insertProviders(ContentValues values){
        return database.insert(DATABASE_TABLE_MOVIES, null, values);
    }

    public int deleteProviders(String id){
        return database.delete(DATABASE_TABLE_MOVIES, _ID + " = ?", new String[]{id});
    }
}