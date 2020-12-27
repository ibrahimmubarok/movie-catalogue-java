package com.ibeybeh.mysubmission4.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ibeybeh.mysubmission4.customclick.CustomOnItemClickListener;
import com.ibeybeh.mysubmission4.activity.MoviesDetailActivity;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.entity.FavoriteMoviesItem;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteMoviesColumns.CONTENT_URI;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder> {

    private ArrayList<FavoriteMoviesItem> listFavMovies = new ArrayList<>();
    private Context context;

    public FavoriteMoviesAdapter(Context context) {
        this.context = context;
    }

    public void setListFavMovies(ArrayList<FavoriteMoviesItem> listMovies){
        this.listFavMovies.clear();
        this.listFavMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    public ArrayList<FavoriteMoviesItem> getListFavMovies(){
        return listFavMovies;
    }

    @NonNull
    @Override
    public FavoriteMoviesAdapter.FavoriteMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movies, parent, false);
        return new FavoriteMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMoviesAdapter.FavoriteMoviesViewHolder holder, int position) {
        holder.tvTitle.setText(getListFavMovies().get(position).getTitle());
        holder.tvRating.setText(getListFavMovies().get(position).getRating());
        Glide.with(context)
                .load(getListFavMovies().get(position).getImgUrl())
                .apply(new RequestOptions().override(380, 420))
                .into(holder.imgPhoto);
        holder.cvFav.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, MoviesDetailActivity.class);

                //content://com.ibeybeh.mysubmission4/favorite_movies/id
                Uri uri = Uri.parse(CONTENT_URI + "/" + getListFavMovies().get(position).getId());
                intent.setData(uri);
                intent.putExtra(MoviesDetailActivity.EXTRA_POSITION_MOVIES, position);
                intent.putExtra(MoviesDetailActivity.EXTRA_FAVORITE_MOVIES, getListFavMovies().get(position));
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listFavMovies.size();
    }

    public class FavoriteMoviesViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvRating;
        final ImageView imgPhoto;
        final CardView cvFav;
        public FavoriteMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_fav_title_movies);
            tvRating = itemView.findViewById(R.id.tv_fav_rating_movies);
            imgPhoto = itemView.findViewById(R.id.img_fav_movies);
            cvFav = itemView.findViewById(R.id.card_view_fav_movies);
        }
    }
}
