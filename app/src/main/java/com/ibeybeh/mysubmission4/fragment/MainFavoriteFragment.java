package com.ibeybeh.mysubmission4.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ibeybeh.mysubmission4.R;
import com.ibeybeh.mysubmission4.ui.SectionsPagerAdapter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFavoriteFragment extends Fragment {


    public MainFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_favorite, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String movies = getResources().getString(R.string.movies);
        String tvShow = getResources().getString(R.string.tvShow);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);

        sectionsPagerAdapter.addFragment(new FavoriteMoviesFragment(), movies);
        sectionsPagerAdapter.addFragment(new FavoriteTvShowsFragment(), tvShow);

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_local_movies_black_24dp);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_tv_black_24dp);
    }
}
