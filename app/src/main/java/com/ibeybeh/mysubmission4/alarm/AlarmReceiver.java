package com.ibeybeh.mysubmission4.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.activity.MainActivity;
import com.ibeybeh.mysubmission4.items.MoviesModel;
import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;
import com.ibeybeh.mysubmission4.rest.client.ApiClient;
import com.ibeybeh.mysubmission4.rest.service.GetReleaseMovieInterface;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TYPE_RELEASE = "Release Reminder";
    public static final String TYPE_DAILY = "Daily Reminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";

    private final static int ID_RELEASE = 100;
    private final static int ID_DAILY = 101;

    private static ArrayList<NotificationReleaseItem> listReleaseItems = new ArrayList<>();

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = type.equalsIgnoreCase(TYPE_RELEASE) ? TYPE_RELEASE : TYPE_DAILY;
        int notifId = type.equalsIgnoreCase(TYPE_RELEASE) ? ID_RELEASE : ID_DAILY ;

        if (title.equals(TYPE_DAILY)){
            showAlarmNotification(context, title, message, notifId);
        }else {
            showReleaseNotifications(context, title, message);
        }
    }

    private void showReleaseNotifications(Context context, String title, String message){

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                (int) Calendar.getInstance().getTimeInMillis(), intent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(title);

        //getDataReleaseMovies();

        Log.d(TAG, "showReleaseNotifications: "+listReleaseItems.get(0).getTitle());

        for (int i=0; i<5; i++){
            inboxStyle.addLine(listReleaseItems.get(i).getTitle());
        }

        inboxStyle.setSummaryText(message);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(inboxStyle)
                .addAction(R.drawable.ic_notifications_24dp, "show Activity", pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManagerCompat.notify(1, builder.build());

        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "ReleaseReminder Manager";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(1, notification);
        }
    }

    public void setReleaseReminderAlarm(Context context, String type, String message){

        getDataReleaseMovies();

        Log.d("listReleaseItems", String.valueOf(listReleaseItems.size()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Log.d("ReleaseReminder", "Diaktifkan");
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    public void setDailyReminderAlarm(Context context, String type, String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Log.d("AlarmReminder", "Diaktifkan");
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_RELEASE) ? ID_RELEASE : ID_DAILY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Log.d("AlarmReminder", "Di non Aktifkan");
    }

    public void getDataReleaseMovies(){
        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormat.format(date);

//        AsyncHttpClient client = new AsyncHttpClient();
//        String url =  "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+ todayDate+"&primary_release_date.lte="+ todayDate;
//
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
//                try {
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    JSONArray list = responseObject.getJSONArray("results");
//
//                    for(int i=0; i<list.length(); i++){
//                        JSONObject movies = list.getJSONObject(i);
//                        NotificationReleaseItem releaseItem = new NotificationReleaseItem();
//                        releaseItem.setId(movies.getInt("id"));
//                        releaseItem.setTitle(movies.getString("title"));
//                        listReleaseItems.add(releaseItem);
//                        Log.d("Title", releaseItem.getTitle());
//                    }
//                }catch (Exception e){
//                    Log.d("Exception", e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("onFailure", error.getMessage());
//            }
//        });
        GetReleaseMovieInterface getReleaseMovieInterface = ApiClient.getClient().create(GetReleaseMovieInterface.class);

        Call<MoviesModel> call = getReleaseMovieInterface.getReleaseMovies(todayDate);
        call.enqueue(new Callback<MoviesModel>() {
            @Override
            public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                if (response.isSuccessful()){
                    if ((response.body().getDataMovies() != null)){
                        ArrayList<MoviesUpComingItems> moviesItems = response.body().getDataMovies();

                        for (MoviesUpComingItems mi : moviesItems){
                            NotificationReleaseItem notificationReleaseItem = new NotificationReleaseItem();

                            notificationReleaseItem.setId(mi.getId());
                            notificationReleaseItem.setTitle(mi.getTitle());

                            listReleaseItems.add(notificationReleaseItem);
                        }
                    }
                }else{
                    return;
                }
            }

            @Override
            public void onFailure(Call<MoviesModel> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}
