package com.ibeybeh.mysubmission4.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.fragment.MainFavoriteFragment;
import com.ibeybeh.mysubmission4.fragment.MoviesFragment;
import com.ibeybeh.mysubmission4.fragment.SearchMoviesFragment;
import com.ibeybeh.mysubmission4.fragment.SearchTvShowsFragment;
import com.ibeybeh.mysubmission4.fragment.TvShowFragment;

import static com.ibeybeh.mysubmission4.SearchViewModel.setQuery;

public class MainActivity extends AppCompatActivity{

    private String fragmentName = "";
    private final String FRAGMENT_TAG = "fragment_tag";

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_RELEASE = "channel_release";
    private static CharSequence CHANNEL_NAME = "ibeybeh channel";

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("My Submission 4");
        }

        if (savedInstanceState != null){
            fragmentName = savedInstanceState.getString(FRAGMENT_TAG);
            Log.d(TAG, fragmentName);
        }else{
            loadFragment(new MoviesFragment());
            Log.d(TAG, "GAGAL");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.movies :
                        loadFragment(new MoviesFragment());
                        fragmentName = "movies";
                        return true;

                    case R.id.tv_show :
                        loadFragment(new TvShowFragment());
                        fragmentName = "tvShow";
                        return true;

                    case R.id.fav_menu :
                        loadFragment(new MainFavoriteFragment());
                        fragmentName = "favorite";
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(FRAGMENT_TAG, fragmentName);
        super.onSaveInstanceState(outState);
    }

    public void sendNotification(View view){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_RELEASE)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp))
                .setContentTitle(getResources().getString(R.string.daily_reminder))
                .setContentText(getResources().getString(R.string.daily_reminder))
                .setSubText(getResources().getString(R.string.reminder_daily_return_to_app))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_RELEASE, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_RELEASE);
            if (mNotificationManager != null){
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void loadFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(searchManager != null){
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (fragmentName == null || fragmentName.equals("movies")){
//                        setQuery(query);
                        loadFragment(new SearchMoviesFragment(query));
                        Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    }else if (fragmentName.equals("tvShow")){
//                        setQuery(query);
                        loadFragment(new SearchTvShowsFragment(query));
                        Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_language) {
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
        }else if (item.getItemId() == R.id.item_setting){
            Intent settingIntent = new Intent(this, SettingActivity.class);
            startActivity(settingIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
