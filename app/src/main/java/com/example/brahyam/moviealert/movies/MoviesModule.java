package com.example.brahyam.moviealert.movies;

import com.example.brahyam.moviealert.di.ActivityScoped;
import com.example.brahyam.moviealert.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoviesModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract MoviesFragment moviesFragment();

    @ActivityScoped
    @Binds
    abstract MoviesContract.Presenter moviesPresenter(MoviesPresenter presenter);
}
