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
import com.ibeybeh.mysubmission4.items.SearchMoviesItems;

import java.util.ArrayList;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesAdapter.SearchMoviesViewHolder> {

    public Context context;
    public ArrayList<SearchMoviesItems> mData;

    public void setData(ArrayList<SearchMoviesItems> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<SearchMoviesItems> getmData() {
        return mData;
    }

    public SearchMoviesAdapter(Context context, ArrayList<SearchMoviesItems> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SearchMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_movies, parent, false);
        return new SearchMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMoviesViewHolder holder, int position) {
        holder.tvTitle.setText(getmData().get(position).getTitleSearch());
        holder.tvRating.setText(getmData().get(position).getVoteAverageSearch());

        Glide.with(context)
                .load(getmData().get(position).getPosterPathSearch())
                .apply(new RequestOptions().override(380, 420))
                .into(holder.imgMovies);
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class SearchMoviesViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvRating;
        ImageView imgMovies;

        public SearchMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_search_movies);
            tvRating = itemView.findViewById(R.id.tv_rating_search_movies);
            imgMovies = itemView.findViewById(R.id.img_search_movies);
        }
    }
}
