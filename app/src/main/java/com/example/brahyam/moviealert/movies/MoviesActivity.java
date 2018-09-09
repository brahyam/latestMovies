package com.example.brahyam.moviealert.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.brahyam.moviealert.R;
import com.example.brahyam.moviealert.util.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MoviesActivity extends DaggerAppCompatActivity {

    @Inject
    MoviesPresenter moviesPresenter;

    @Inject
    Lazy<MoviesFragment> moviesFragmentProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity);

        MoviesFragment moviesFragment = (MoviesFragment)
                getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (moviesFragment == null) {
            moviesFragment = moviesFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    moviesFragment,
                    R.id.contentFrame);
        }
    }
}
