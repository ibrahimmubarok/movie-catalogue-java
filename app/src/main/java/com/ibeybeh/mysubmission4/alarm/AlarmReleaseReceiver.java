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
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.ibeybeh.mysubmission4.alarm.AlarmReceiver.TYPE_RELEASE;

public class AlarmReleaseReceiver extends BroadcastReceiver {

    public static final String RELEASE_DAILY = "Release Reminder";
    public static final String TYPE_DAILY = "Daily Reminder";

    public static final String EXTRA_MESSAGE_RELEASE = "message_release";
    public static final String EXTRA_TYPE_RELEASE = "type_release";

    private final static int ID_RELEASE = 100;
    private final static int ID_DAILY = 101;

    private ArrayList<NotificationReleaseItem> listReleaseItems = new ArrayList<>();
    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";

    public AlarmReleaseReceiver(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showReleaseNotifications(context);
    }

    public void setReleaseReminderAlarm(Context context, String type, String message) {

        setDataReleaseMovies();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_RELEASE, message);
        intent.putExtra(EXTRA_TYPE_RELEASE, type);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Log.d("ReleaseReminder", "Diaktifkan");
    }

    private void showReleaseNotifications(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                (int) Calendar.getInstance().getTimeInMillis(), intent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        Log.d("listReleaseItems", String.valueOf(listReleaseItems.size()));

        inboxStyle.setBigContentTitle("The Latest Movies");
        for (int i=0; i<7; i++){
            inboxStyle.addLine(listReleaseItems.get(i).getTitle());
        }

        inboxStyle.setSummaryText("Dont want to miss the latest movies ");

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_24dp
                )
                .setContentTitle(RELEASE_DAILY)
                .setContentText("New movies available today")
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

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Log.d("ReleaseReminder", "Di non Aktifkan");
    }

    public void setDataReleaseMovies(){
        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormat.format(date);

        AsyncHttpClient client = new AsyncHttpClient();
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
}
