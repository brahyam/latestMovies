package com.example.brahyam.moviealert;

import com.example.brahyam.moviealert.data.source.MoviesRepository;
import com.example.brahyam.moviealert.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MovieApp extends DaggerApplication {

    @Inject
    MoviesRepository moviesRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
