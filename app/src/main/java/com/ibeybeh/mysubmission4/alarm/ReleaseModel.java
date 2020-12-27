package com.ibeybeh.mysubmission4.alarm;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReleaseModel{
    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";
    static ArrayList<NotificationReleaseItem> listReleaseItems = new ArrayList<>();

    public void setReleaseMovies(String todayDate){

        AsyncHttpClient client = new AsyncHttpClient();
        //final ArrayList<NotificationReleaseItem> listReleaseItems = new ArrayList<>();
        String url =  "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+ todayDate +"&primary_release_date.lte="+ todayDate;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for(int i=0; i<list.length(); i++){
                        JSONObject movies = list.getJSONObject(i);
                        NotificationReleaseItem releaseItem = new NotificationReleaseItem();
                        releaseItem.setId(movies.getInt("id"));
                        releaseItem.setTitle(movies.getString("title"));
                        listReleaseItems.add(releaseItem);
                        Log.d("Title", releaseItem.getTitle());
                    }
                    Log.d("ReleaseModel baris 45", String.valueOf(listReleaseItems.size()));
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

//    public LiveData<ArrayList<NotificationReleaseItem>> getNotifMovies(){
//        return listReleaseItem;
//    }
}
