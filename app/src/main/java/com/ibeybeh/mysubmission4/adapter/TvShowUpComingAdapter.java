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
import com.ibeybeh.mysubmission4.items.TvShowUpComingItems;

import java.util.ArrayList;

public class TvShowUpComingAdapter extends RecyclerView.Adapter<TvShowUpComingAdapter.TvShowViewHolder> {

    private Context context;
    private ArrayList<TvShowUpComingItems> mData;

    public void setData (ArrayList<TvShowUpComingItems> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<TvShowUpComingItems> getmData() {
        return mData;
    }

    public TvShowUpComingAdapter(Context context, ArrayList<TvShowUpComingItems> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_tv_show, parent, false);
        return new TvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.tvTitleTvShow.setText(getmData().get(position).getName());
        holder.tvRatingTvShow.setText(getmData().get(position).getVoteAverage());

        Glide.with(context)
                .load(getmData().get(position).getPosterPath())
                .apply(new RequestOptions().override(380,420))
                .into(holder.imgPhotoTvShow);
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhotoTvShow;
        TextView tvTitleTvShow, tvRatingTvShow;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhotoTvShow = itemView.findViewById(R.id.img_tv_show);
            tvTitleTvShow = itemView.findViewById(R.id.tv_title_tv_show);
            tvRatingTvShow = itemView.findViewById(R.id.tv_rating_tv_show);
        }
    }
}
