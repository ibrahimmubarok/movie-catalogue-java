package com.ibeybeh.mysubmission4.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.activity.MainActivity;
import com.ibeybeh.mysubmission4.alarm.AlarmReceiver;
import com.ibeybeh.mysubmission4.alarm.AlarmReleaseReceiver;
import com.ibeybeh.mysubmission4.alarm.NotificationReleaseItem;
import com.ibeybeh.mysubmission4.alarm.ReleaseModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    private String DAILY_REMINDER;
    private String RELEASE_REMINDER;

    private SwitchPreference swDaily, swRelease;

    private AlarmReceiver alarmReceiver;

    private ArrayList<NotificationReleaseItem> listReleaseItems = new ArrayList<>();
    private static final String API_KEY = "224f6797a2665e28bce03b9e0655510a";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        DAILY_REMINDER = getResources().getString(R.string.daily_reminder);
        RELEASE_REMINDER = getResources().getString(R.string.release_reminder);

        swDaily = (SwitchPreference) findPreference(DAILY_REMINDER);
        swRelease = (SwitchPreference) findPreference(RELEASE_REMINDER);

        setSummaries();

        alarmReceiver = new AlarmReceiver();

        swDaily.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (swDaily.isChecked()){
                    alarmReceiver.setDailyReminderAlarm(getContext(), AlarmReceiver.TYPE_DAILY, getResources().getString(R.string.miss_you));
                    Toast.makeText(getContext(), getResources().getString(R.string.reminder_on), Toast.LENGTH_SHORT).show();
                }else if(!swDaily.isChecked()){
                    alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.TYPE_DAILY);
                    Toast.makeText(getContext(), getResources().getString(R.string.reminder_off), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        swRelease.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (swRelease.isChecked()){
                    //releaseNotifications();
                    alarmReceiver.setReleaseReminderAlarm(getContext(), AlarmReceiver.TYPE_RELEASE, getResources().getString(R.string.miss_you));
                    Toast.makeText(getContext(), getResources().getString(R.string.release_on), Toast.LENGTH_SHORT).show();
                }else if (!swRelease.isChecked()){
                    alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.TYPE_RELEASE);
                    Toast.makeText(getContext(), getResources().getString(R.string.release_off), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void setSummaries(){
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        swDaily.setChecked(sh.getBoolean(DAILY_REMINDER, false));
        swRelease.setChecked(sh.getBoolean(RELEASE_REMINDER, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(DAILY_REMINDER)){
            swDaily.setChecked(sharedPreferences.getBoolean(DAILY_REMINDER, false));
        }

        if (key.equals(RELEASE_REMINDER)){
            swRelease.setChecked(sharedPreferences.getBoolean(RELEASE_REMINDER, false));
        }
    }

    private void releaseNotifications(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                (int) Calendar.getInstance().getTimeInMillis(), intent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("The Latest Movies");
        for (int i=0; i<listReleaseItems.size(); i++){
            inboxStyle.addLine(listReleaseItems.get(i).getTitle());
        }

        inboxStyle.setSummaryText("Dont want to miss the latest movies ");

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_notifications_24dp
                )
                .setContentTitle("Release Daily")
                .setContentText("New movies available today")
                .setStyle(inboxStyle)
                .addAction(R.drawable.ic_notifications_24dp, "show Activity", pendingIntent)
                .setColor(ContextCompat.getColor(getContext(), android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public void setReleaseMovies(String todayDate){

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
