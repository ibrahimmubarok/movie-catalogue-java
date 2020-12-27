package com.ibeybeh.mysubmission4.adapter;

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
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.items.MoviesUpComingItems;

import java.util.ArrayList;

public class MoviesUpComingAdapter extends RecyclerView.Adapter<MoviesUpComingAdapter.MoviesViewHolder> {

    private Context context;
    private ArrayList<MoviesUpComingItems> mData;

    public void setData(ArrayList<MoviesUpComingItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    private ArrayList<MoviesUpComingItems> getmData(){
        return mData;
    }

    public MoviesUpComingAdapter(Context context, ArrayList<MoviesUpComingItems> mData){
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_movies, parent, false);
        return new MoviesViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.tvTitleMovies.setText(getmData().get(position).getTitle());
        holder.tvRatingMovies.setText(getmData().get(position).getVoteAverage());

        Glide.with(context)
                .load(getmData().get(position).getPosterPath())
                .apply(new RequestOptions().override(380,420))
                .into(holder.imgPhotoMovies);
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhotoMovies;
        TextView tvTitleMovies, tvRatingMovies;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhotoMovies = itemView.findViewById(R.id.img_movies);
            tvTitleMovies = itemView.findViewById(R.id.tv_title_movies);
            tvRatingMovies = itemView.findViewById(R.id.tv_rating_movies);
        }
    }
}
