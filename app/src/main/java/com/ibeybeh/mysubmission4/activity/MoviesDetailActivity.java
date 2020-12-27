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
import com.ibeybeh.mysubmission4.entity.FavoriteMoviesItem;
import com.ibeybeh.mysubmission4.items.MoviesDetailItems;
import com.ibeybeh.mysubmission4.items.SearchMoviesItems;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.DESCRIPTION;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.IMG_URL;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.POPULARITY;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.POSTER_URL;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.RATING;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.RELEASE_DATE;
import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.TITLE;

public class MoviesDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPopularity, tvTitle, tvDescription, tvReleaseDate;
    private ImageView imgPhoto, imgPoster;
    private TextView mReleaseDate, mPopularity;
    private CardView cardView;
    private ProgressBar progressBar;
    private ImageButton btnFavMovies;

    public static String titleBar;
    public static int id;
    public static String homeMovies = "";
    private boolean favIcon = false;

    public static String KEY_TITLE_MOVIE = "key_title_movie";
    public static String KEY_DESC_MOVIE = "key_desc_movie";
    public static String KEY_POPULARITY_MOVIE = "key_popularity_movie";
    public static String KEY_RELEASE_DATE_MOVIE = "key_release_date_movie";
    public static String KEY_POSTER_MOVIE = "key_poster_movie";
    public static String KEY_BACKDROP_MOVIE = "key_backdrop_movie";
    public static String KEY_RATE_MOVIE = "key_rate_movie";

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SEARCH_MOVIE = "extra_search_movie";

    public static final String EXTRA_FAVORITE_MOVIES = "extra_favorite_movies";
    public static final String EXTRA_POSITION_MOVIES = "extra_position_movies";

    private final String TAG = MoviesDetailActivity.class.getSimpleName();

    private FavoriteMoviesItem favMovies;

    private int position;

    private String title, desc, popularity, releaseDate, posterPath, backdropPath, rate;
    public static String rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString(KEY_TITLE_MOVIE);
        desc = bundle.getString(KEY_DESC_MOVIE);
        popularity = bundle.getString(KEY_POPULARITY_MOVIE);
        releaseDate = bundle.getString(KEY_RELEASE_DATE_MOVIE);
        posterPath = bundle.getString(KEY_POSTER_MOVIE);
        backdropPath = bundle.getString(KEY_BACKDROP_MOVIE);
        rate = bundle.getString(KEY_RATE_MOVIE);

//        DetailViewModel moviesDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
//        if (homeMovies.equals("searchMoviesFragment")){
//            moviesDetailViewModel.getSearchMoviesDetail().observe(this, getSearchMoviesDetail);
//        }else if (homeMovies.equals("moviesFragment")){
//            moviesDetailViewModel.getMoviesDetail().observe(this, getMoviesDetail);
//        }else{
//            moviesDetailViewModel.getSearchMoviesDetail().observe(this, getSearchMoviesDetail);
//            moviesDetailViewModel.getMoviesDetail().observe(this, getMoviesDetail);
//        }

        tvPopularity = findViewById(R.id.tv_popularity_movies_detail);
        tvTitle = findViewById(R.id.tv_title_movies_detail);
        tvDescription = findViewById(R.id.tv_desc_movies_detail);
        tvReleaseDate = findViewById(R.id.tv_release_date_movies_detail);
        imgPhoto = findViewById(R.id.img_photo_movies_detail);
        imgPoster = findViewById(R.id.img_photo_movies_poster_detail);
        progressBar = findViewById(R.id.progress_bar_detail_movies);
        mReleaseDate = findViewById(R.id.textView_release_date_movies);
        mPopularity = findViewById(R.id.textView_popularity_movies);
        cardView = findViewById(R.id.cardView_movies);
        btnFavMovies = findViewById(R.id.btn_fav_detail_movies);
        btnFavMovies.setOnClickListener(this);

        favMovies = getIntent().getParcelableExtra(EXTRA_FAVORITE_MOVIES);
        if (favMovies != null){
            position = getIntent().getIntExtra(EXTRA_POSITION_MOVIES, 0);
            btnFavMovies.setImageResource(R.drawable.ic_favorite_24dp);
            favIcon = true;

            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(favMovies.getTitle());
            }

            tvTitle.setText(favMovies.getTitle());
            tvDescription.setText(favMovies.getDesc());
            tvPopularity.setText(favMovies.getPopularity());
            tvReleaseDate.setText(favMovies.getReleaseDate());

            Glide.with(this)
                    .load(favMovies.getImgUrl())
                    .apply(new RequestOptions().override(380, 420))
                    .into(imgPhoto);

            Glide.with(this)
                    .load(favMovies.getPosterUrl())
                    .apply(new RequestOptions())
                    .into(imgPoster);

            Log.d(TAG, "Ada Data");
        }else{
            favMovies = new FavoriteMoviesItem();
//            if (homeMovies.equals("searchMoviesFragment")){
//                moviesDetailViewModel.setSearchMoviesDetail();
//            }else if (homeMovies.equals("moviesFragment")){
//                moviesDetailViewModel.setMoviesDetailData();
//            }

            tvTitle.setText(title);
            tvPopularity.setText(popularity);
            tvReleaseDate.setText(releaseDate);
            tvDescription.setText(desc);

            Glide.with(this)
                    .load(posterPath)
                    .apply(new RequestOptions().override(380, 420))
                    .into(imgPhoto);

            Glide.with(this)
                    .load(backdropPath)
                    .apply(new RequestOptions())
                    .into(imgPoster);

            Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

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
                if (cursor.moveToFirst()) favMovies = new FavoriteMoviesItem(cursor);
                cursor.close();
            }
        }
    }

//    private Observer<ArrayList<MoviesDetailItems>> getMoviesDetail = new Observer<ArrayList<MoviesDetailItems>>() {
//        @Override
//        public void onChanged(ArrayList<MoviesDetailItems> moviesDetailItems) {
//            getData(moviesDetailItems);
//            showLoading(false);
//        }
//    };
//
//    private Observer<ArrayList<SearchMoviesItems>> getSearchMoviesDetail = new Observer<ArrayList<SearchMoviesItems>>() {
//        @Override
//        public void onChanged(ArrayList<SearchMoviesItems> searchMoviesDetailItems) {
//            getDataSearch(searchMoviesDetailItems);
//            showLoading(false);
//        }
//    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void getData(ArrayList<MoviesDetailItems> items){
//        for(int i=0; i<items.size(); i++) {
//            if (items.get(i).getId() == id) {
//                String title = items.get(i).getTitle();
//                String releaseDate = items.get(i).getReleaseDate();
//                String overview = items.get(i).getOverview();
//                String popularity = items.get(i).getPopularity();
//                String posterPath = items.get(i).getPosterPath();
//                String backdropPath = items.get(i).getBackdropPath();
//
//                this.title = title;
//                this.releaseDate = releaseDate;
//                this.desc = overview;
//                this.popularity = popularity;
//                this.posterPath = posterPath;
//                this.backdropPath = backdropPath;
//
//                tvTitle.setText(title);
//                tvPopularity.setText(popularity);
//                tvReleaseDate.setText(releaseDate);
//                tvDescription.setText(overview);
//
//                Glide.with(this)
//                        .load(posterPath)
//                        .apply(new RequestOptions().override(380, 420))
//                        .into(imgPhoto);
//
//                Glide.with(this)
//                        .load(backdropPath)
//                        .apply(new RequestOptions())
//                        .into(imgPoster);
//
//                Log.d("MoviesDetailActivity", String.valueOf(id));
//            }
//        }
//    }

//    private void getDataSearch(ArrayList<SearchMoviesItems> items){
//        for(int i=0; i<items.size(); i++) {
//            if (items.get(i).getId() == id) {
//                String title = items.get(i).getTitleSearch();
//                String releaseDate = items.get(i).getReleaseDateSearch();
//                String overview = items.get(i).getOverviewSearch();
//                String popularity = items.get(i).getPopularitySearch();
//                String posterPath = items.get(i).getPosterPathSearch();
//                String backdropPath = items.get(i).getBackdropPathSearch();
//
//                this.title = title;
//                this.releaseDate = releaseDate;
//                this.desc = overview;
//                this.popularity = popularity;
//                this.posterPath = posterPath;
//                this.backdropPath = backdropPath;
//
//                tvTitle.setText(title);
//                tvPopularity.setText(popularity);
//                tvReleaseDate.setText(releaseDate);
//                tvDescription.setText(overview);
//
//                Glide.with(this)
//                        .load(posterPath)
//                        .apply(new RequestOptions().override(380, 420))
//                        .into(imgPhoto);
//
//                Glide.with(this)
//                        .load(backdropPath)
//                        .apply(new RequestOptions())
//                        .into(imgPoster);
//
//                Log.d("MoviesDetailActivity", String.valueOf(id));
//            }
//        }
//    }

//    private void showLoading(boolean state) {
//        if(state){
//            progressBar.setVisibility(View.VISIBLE);
//            tvTitle.setVisibility(View.GONE);
//            tvReleaseDate.setVisibility(View.GONE);
//            tvPopularity.setVisibility(View.GONE);
//            tvDescription.setVisibility(View.GONE);
//            imgPhoto.setVisibility(View.GONE);
//            imgPoster.setVisibility(View.GONE);
//            mPopularity.setVisibility(View.GONE);
//            mReleaseDate.setVisibility(View.GONE);
//            cardView.setVisibility(View.GONE);
//            btnFavMovies.setVisibility(View.GONE);
//
//            if(getSupportActionBar() != null){
//                getSupportActionBar().setTitle("");
//            }
//        }else{
//            progressBar.setVisibility(View.GONE);
//            tvTitle.setVisibility(View.VISIBLE);
//            tvReleaseDate.setVisibility(View.VISIBLE);
//            tvPopularity.setVisibility(View.VISIBLE);
//            tvDescription.setVisibility(View.VISIBLE);
//            imgPhoto.setVisibility(View.VISIBLE);
//            imgPoster.setVisibility(View.VISIBLE);
//            mPopularity.setVisibility(View.VISIBLE);
//            mReleaseDate.setVisibility(View.VISIBLE);
//            cardView.setVisibility(View.VISIBLE);
//            btnFavMovies.setVisibility(View.VISIBLE);
//
//            if(getSupportActionBar() != null){
//                getSupportActionBar().setTitle(titleBar);
//            }
//        }
//    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_fav_detail_movies){

            favMovies.setTitle(title);
            favMovies.setPopularity(popularity);
            favMovies.setReleaseDate(releaseDate);
            favMovies.setDesc(desc);
            favMovies.setImgUrl(posterPath);
            favMovies.setPosterUrl(backdropPath);
            favMovies.setRating(rate);

            boolean isEmpty = false;

            Intent intent = new Intent();
            intent.putExtra(EXTRA_FAVORITE_MOVIES, favMovies);
            intent.putExtra(EXTRA_POSITION_MOVIES, position);

            if (!isEmpty){
                ContentValues values = new ContentValues();
                values.put(TITLE, title);
                values.put(DESCRIPTION, desc);
                values.put(POPULARITY, popularity);
                values.put(RELEASE_DATE, releaseDate);
                values.put(IMG_URL, posterPath);
                values.put(POSTER_URL, backdropPath);
                values.put(RATING, rate);

                if (!favIcon){
                    btnFavMovies.setImageResource(R.drawable.ic_favorite_24dp);
                    favIcon = true;

                    getContentResolver().insert(CONTENT_URI, values);

                    Toast.makeText(MoviesDetailActivity.this, "Berhasil Ditambahkan Ke Favorit", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Berhasil Ditambahkan Ke Favorit");
                }else{
                    btnFavMovies.setImageResource(R.drawable.ic_favorite_border_24dp);
                    showAlertDialog();
                    favIcon = false;
                }

            }
        }
    }

    private void showAlertDialog(){
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
                        intent.putExtra(EXTRA_POSITION_MOVIES, position);
                        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + favMovies.getId()),
                                null,
                                null);
                        Toast.makeText(MoviesDetailActivity.this, "Berhasil Dihapus Dari Favorite", Toast.LENGTH_SHORT).show();
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
