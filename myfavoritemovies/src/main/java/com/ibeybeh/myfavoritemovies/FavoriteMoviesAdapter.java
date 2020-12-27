package com.ibeybeh.myfavoritemovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder> {

    private final ArrayList<FavoriteMoviesItem> items = new ArrayList<>();
    private final Context context;

    public FavoriteMoviesAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<FavoriteMoviesItem> getItems() {
        return items;
    }

    public void setList(ArrayList<FavoriteMoviesItem> listItems){
        this.items.clear();
        this.items.addAll(listItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movies, parent, false);
        return new FavoriteMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMoviesViewHolder holder, int position) {
        holder.tvTitle.setText(getItems().get(position).getTitle());
        holder.tvRating.setText(getItems().get(position).getRating());
        Glide.with(context)
                .load(getItems().get(position).getImgUrl())
                .apply(new RequestOptions().override(380, 420))
                .into(holder.imgMovies);
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public class FavoriteMoviesViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvRating;
        final ImageView imgMovies;
        View rootView;

        public FavoriteMoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            rootView = itemView;
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
            imgMovies = itemView.findViewById(R.id.img_movies);
        }
    }
}
