package com.ibeybeh.mysubmission4.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ibeybeh.mysubmission4.DetailViewModel;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.entity.FavoriteTvShowItem;
import com.ibeybeh.mysubmission4.items.SearchTvShowsItems;
import com.ibeybeh.mysubmission4.items.TvShowDetailItems;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.*;

public class TvShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPopularity, tvTitle, tvDescription, tvFirstOnAir;
    private ImageView imgPhoto, imgPoster;
    private TextView mReleaseDate, mPopularity;
    private CardView cardView;
    private ProgressBar progressBar;
    private ImageButton btnFavTvShow;

    public static String titleBar;
    public static String rating;
    public static int id;
    public static String homeTvShows = "";
    private boolean favIcon = false;

    public static String KEY_NAME_TV_SHOW = "key_name_tv_show";
    public static String KEY_DESC_TV_SHOW = "key_desc_tv_show";
    public static String KEY_POPULARITY_TV_SHOW= "key_popularity_tv_show";
    public static String KEY_ON_AIR_DATE_TV_SHOW = "key_on_air_tv_show";
    public static String KEY_POSTER_TV_SHOW = "key_poster_tv_show";
    public static String KEY_BACKDROP_TV_SHOW = "key_backdrop_tv_show";
    public static String KEY_RATE_TV_SHOW = "key_rate_tv_show";

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    public static final String EXTRA_SEARCH_TV_SHOW = "extra_search_tv_show";

    public static final String EXTRA_FAVORITE_TV_SHOW = "extra_favorite_tv_show";
    public static final String EXTRA_POSITION_TV_SHOW = "extra_position_tv_show";

    private final String TAG = MoviesDetailActivity.class.getSimpleName();

    private FavoriteTvShowItem favTvShows;

    private int position;

    private String name, overview, popularity, firstOnAir, posterPath, backdropPath, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString(KEY_NAME_TV_SHOW);
        overview = bundle.getString(KEY_DESC_TV_SHOW);
        popularity = bundle.getString(KEY_POPULARITY_TV_SHOW);
        firstOnAir = bundle.getString(KEY_ON_AIR_DATE_TV_SHOW);
        posterPath = bundle.getString(KEY_POSTER_TV_SHOW);
        backdropPath = bundle.getString(KEY_BACKDROP_TV_SHOW);
        rate = bundle.getString(KEY_RATE_TV_SHOW);

//        DetailViewModel tvShowDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
//        if (homeTvShows.equals("tvShowsSearchFragment")){
//            tvShowDetailViewModel.getSearchTvShowsDetail().observe(this, getTvShowSearchDetail);
//        }else if (homeTvShows.equals("tvShowsFragment")){
//            tvShowDetailViewModel.getTvShowDetail().observe(this, getTvShowDetail);
//        }else {
//            tvShowDetailViewModel.getSearchTvShowsDetail().observe(this, getTvShowSearchDetail);
//            tvShowDetailViewModel.getTvShowDetail().observe(this, getTvShowDetail);
//        }

        tvPopularity = findViewById(R.id.tv_popularity_tv_show_detail);
        tvTitle = findViewById(R.id.tv_title_tv_show_detail);
        tvDescription = findViewById(R.id.tv_desc_tv_show_detail);
        tvFirstOnAir = findViewById(R.id.tv_first_air_date_tv_show_detail);
        imgPhoto = findViewById(R.id.img_photo_tv_show_detail);
        imgPoster = findViewById(R.id.img_photo_tv_show_poster_detail);
        progressBar = findViewById(R.id.progress_bar_detail_tv_show);
        mReleaseDate = findViewById(R.id.textView_first_air_date);
        mPopularity = findViewById(R.id.textView_popularity_tv_show);
        cardView = findViewById(R.id.cardView_tv_show);
        btnFavTvShow = findViewById(R.id.btn_fav_detail_tv_show);
        btnFavTvShow.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);

        favTvShows = getIntent().getParcelableExtra(EXTRA_FAVORITE_TV_SHOW);
        if (favTvShows != null){
            position = getIntent().getIntExtra(EXTRA_POSITION_TV_SHOW, 0);
            btnFavTvShow.setImageResource(R.drawable.ic_favorite_24dp);
            favIcon = true;

            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(favTvShows.getName());
            }

            tvTitle.setText(favTvShows.getName());
            tvDescription.setText(favTvShows.getOverview());
            tvFirstOnAir.setText(favTvShows.getFirstAirDate());
            tvPopularity.setText(favTvShows.getPopularity());

            Glide.with(this)
                    .load(favTvShows.getPosterPath())
                    .apply(new RequestOptions().override(380, 420))
                    .into(imgPhoto);

            Glide.with(this)
                    .load(favTvShows.getBackdropPath())
                    .apply(new RequestOptions())
                    .into(imgPoster);

            Log.d(TAG, "Ada Data");
        }else{
            favTvShows = new FavoriteTvShowItem();
//            if (homeTvShows.equals("tvShowsSearchFragment")){
//                tvShowDetailViewModel.setSearchTvShowDetail();
//            }else if (homeTvShows.equals("tvShowsFragment")) {
//                tvShowDetailViewModel.setTvShowDetailData();
//            }

            tvTitle.setText(name);
            tvPopularity.setText(popularity);
            tvFirstOnAir.setText(firstOnAir);
            tvDescription.setText(overview);

            Glide.with(this)
                    .load(posterPath)
                    .apply(new RequestOptions().override(380, 420))
                    .into(imgPhoto);

            Glide.with(this)
                    .load(backdropPath)
                    .apply(new RequestOptions())
                    .into(imgPoster);


            Log.d(TAG, "Gaada Data");
        }

        Uri uri = getIntent().getData();

        if (uri != null){
            Cursor cursor = getContentResolver().query(uri,
                    null,
                    null,
                    null,
                    null);

            if (cursor != null){
                if (cursor.moveToFirst()) favTvShows = new FavoriteTvShowItem(cursor);
                cursor.close();
            }
        }
    }

    private Observer<ArrayList<TvShowDetailItems>> getTvShowDetail = new Observer<ArrayList<TvShowDetailItems>>() {
        @Override
        public void onChanged(ArrayList<TvShowDetailItems> tvShowDetailItems) {
            getData(tvShowDetailItems);
            showLoading(false);
        }
    };

    private Observer<ArrayList<SearchTvShowsItems>> getTvShowSearchDetail= new Observer<ArrayList<SearchTvShowsItems>>() {
        @Override
        public void onChanged(ArrayList<SearchTvShowsItems> tvShowSearchDetailItems) {
            getDataSearch(tvShowSearchDetailItems);
            showLoading(false);
        }
    };

    private void getDataSearch(ArrayList<SearchTvShowsItems> items) {
        for(int i=0; i<items.size(); i++) {
            if (items.get(i).getId() == id) {
                String title = items.get(i).getTitleSearch();
                String firstAirDate= items.get(i).getReleaseDateSearch();
                String overview = items.get(i).getOverviewSearch();
                String popularity = items.get(i).getPopularitySearch();
                String posterPath = items.get(i).getPosterPathSearch();
                String backdropPath = items.get(i).getBackdropPathSearch();

                this.name = title;
                this.firstOnAir = firstAirDate;
                this.overview = overview;
                this.popularity = popularity;
                this.posterPath = posterPath;
                this.backdropPath = backdropPath;

                tvTitle.setText(title);
                tvPopularity.setText(popularity);
                tvFirstOnAir.setText(firstAirDate);
                tvDescription.setText(overview);

                Glide.with(this)
                        .load(posterPath)
                        .apply(new RequestOptions().override(380, 420))
                        .into(imgPhoto);

                Glide.with(this)
                        .load(backdropPath)
                        .apply(new RequestOptions())
                        .into(imgPoster);

                Log.d("TvShowDetailActivity", String.valueOf(id));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(ArrayList<TvShowDetailItems> items) {
        for(int i=0; i<items.size(); i++) {
            if (items.get(i).getId() == id) {
                String title = items.get(i).getName();
                String firstAirDate= items.get(i).getFirstAirDate();
                String overview = items.get(i).getOverview();
                String popularity = items.get(i).getPopularity();
                String posterPath = items.get(i).getPosterPath();
                String backdropPath = items.get(i).getBackdropPath();

                this.name = title;
                this.firstOnAir = firstAirDate;
                this.overview = overview;
                this.popularity = popularity;
                this.posterPath = posterPath;
                this.backdropPath = backdropPath;

                tvTitle.setText(title);
                tvPopularity.setText(popularity);
                tvFirstOnAir.setText(firstAirDate);
                tvDescription.setText(overview);

                Glide.with(this)
                        .load(posterPath)
                        .apply(new RequestOptions().override(380, 420))
                        .into(imgPhoto);

                Glide.with(this)
                        .load(backdropPath)
                        .apply(new RequestOptions())
                        .into(imgPoster);

                Log.d("TvShowDetailActivity", String.valueOf(id));
            }
        }
    }

    private void showLoading(boolean state) {
        if(state){
            progressBar.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.GONE);
            tvFirstOnAir.setVisibility(View.GONE);
            tvPopularity.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.GONE);
            imgPoster.setVisibility(View.GONE);
            mPopularity.setVisibility(View.GONE);
            mReleaseDate.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            btnFavTvShow.setVisibility(View.GONE);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle("");
            }
        }else{
            progressBar.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            tvFirstOnAir.setVisibility(View.VISIBLE);
            tvPopularity.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
            imgPhoto.setVisibility(View.VISIBLE);
            imgPoster.setVisibility(View.VISIBLE);
            mPopularity.setVisibility(View.VISIBLE);
            mReleaseDate.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            btnFavTvShow.setVisibility(View.VISIBLE);

            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(titleBar);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_fav_detail_tv_show){

            favTvShows.setName(name);
            favTvShows.setPopularity(popularity);
            favTvShows.setBackdropPath(backdropPath);
            favTvShows.setFirstAirDate(firstOnAir);
            favTvShows.setOverview(overview);
            favTvShows.setPosterPath(posterPath);
            favTvShows.setRating(rate);

            Log.d(TAG, name + ", " + rate);
            boolean isEmpty = false;

            Intent intent = new Intent();
            intent.putExtra(EXTRA_FAVORITE_TV_SHOW, favTvShows);
            intent.putExtra(EXTRA_POSITION_TV_SHOW, position);

            if (!isEmpty){
                ContentValues values = new ContentValues();
                values.put(NAME, name);
                values.put(OVERVIEW, overview);
                values.put(POPULARITY, popularity);
                values.put(BACKDROP_PATH, backdropPath);
                values.put(FIRST_AIR_DATE, firstOnAir);
                values.put(RATING, rate);
                values.put(POSTER_PATH, posterPath);

                if (!favIcon){
                    btnFavTvShow.setImageResource(R.drawable.ic_favorite_24dp);
                    favIcon = true;

                    getContentResolver().insert(CONTENT_URI, values);

                    Toast.makeText(TvShowDetailActivity.this, "Berhasil Ditambahkan Ke Favorit", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Berhasil Ditambah");
                }else{
                    btnFavTvShow.setImageResource(R.drawable.ic_favorite_border_24dp);
                    showAlertDialog();
                    favIcon = false;
                }
            }
        }
    }

    private void showAlertDialog() {
        String dialogTitle = "Hapus Favorite";
        String dialogMessage = "Apakah Anda Yakin Ingin Menghapus Film Dari Favorite?";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_POSITION_TV_SHOW, position);
                        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + favTvShows.getId()),
                                null,
                                null);
                        Toast.makeText(TvShowDetailActivity.this, "Berhasil Dihapus Dari Favorite", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Berhasil Dihapus Dari Favorit");
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
