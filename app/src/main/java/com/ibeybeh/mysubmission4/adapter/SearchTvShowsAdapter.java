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
import com.ibeybeh.mysubmission4.items.SearchTvShowsItems;

import java.util.ArrayList;

public class SearchTvShowsAdapter extends RecyclerView.Adapter<SearchTvShowsAdapter.SearchTvShowsViewHolder> {

    private Context context;
    private ArrayList<SearchTvShowsItems> mData;

    public void setData(ArrayList<SearchTvShowsItems> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<SearchTvShowsItems> getmData() {
        return mData;
    }

    public SearchTvShowsAdapter(Context context, ArrayList<SearchTvShowsItems> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public SearchTvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_tv_shows, parent, false);
        return new SearchTvShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvShowsViewHolder holder, int position) {
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

    public class SearchTvShowsViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvRating;
        ImageView imgMovies;

        public SearchTvShowsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_search_tv_shows);
            tvRating = itemView.findViewById(R.id.tv_rating_search_tv_shows);
            imgMovies = itemView.findViewById(R.id.img_search_tv_shows);
        }
    }
}
