package com.ibeybeh.mysubmission4.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.activity.TvShowDetailActivity;
import com.ibeybeh.mysubmission4.customclick.CustomOnItemClickListener;
import com.ibeybeh.mysubmission4.entity.FavoriteTvShowItem;

import java.util.ArrayList;

import static com.ibeybeh.mysubmission4.db.DatabaseContract.FavoriteTvShowColumns.CONTENT_URI;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowViewHolder> {

    private ArrayList<FavoriteTvShowItem> listFavTvShows = new ArrayList<>();
    private Context context;

    public FavoriteTvShowAdapter(Context context){
        this.context = context;
    }

    public void setListFavTvShows(ArrayList<FavoriteTvShowItem> listTvShows){
        this.listFavTvShows.clear();
        this.listFavTvShows.addAll(listTvShows);
        notifyDataSetChanged();
    }

    public ArrayList<FavoriteTvShowItem> getListFavTvShows(){
        return listFavTvShows;
    }

    @NonNull
    @Override
    public FavoriteTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_tv_show, parent, false);
        return new FavoriteTvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvShowViewHolder holder, int position) {
        holder.tvTitle.setText(getListFavTvShows().get(position).getName());
        holder.tvRating.setText(getListFavTvShows().get(position).getRating());
        Glide.with(context)
                .load(getListFavTvShows().get(position).getPosterPath())
                .apply(new RequestOptions().override(380, 420))
                .into(holder.imgPhoto);
        holder.cvFav.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(context, TvShowDetailActivity.class);

                //content://com.ibeybeh.mysubmission4/favorite_tv_shows/id
                Uri uri = Uri.parse(CONTENT_URI + "/" + getListFavTvShows().get(position).getId());
                intent.setData(uri);
                intent.putExtra(TvShowDetailActivity.EXTRA_POSITION_TV_SHOW, position);
                intent.putExtra(TvShowDetailActivity.EXTRA_FAVORITE_TV_SHOW, getListFavTvShows().get(position));
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listFavTvShows.size();
    }

    public class FavoriteTvShowViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvRating;
        final ImageView imgPhoto;
        final CardView cvFav;

        public FavoriteTvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_fav_title_tv_show);
            tvRating = itemView.findViewById(R.id.tv_fav_rating_tv_show);
            imgPhoto = itemView.findViewById(R.id.img_fav_tv_show);
            cvFav = itemView.findViewById(R.id.card_view_fav_tv_show);
        }
    }
}
