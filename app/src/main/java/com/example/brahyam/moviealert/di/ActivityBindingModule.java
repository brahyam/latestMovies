package com.example.brahyam.moviealert.di;

import com.example.brahyam.moviealert.movies.MoviesActivity;
import com.example.brahyam.moviealert.movies.MoviesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MoviesModule.class)
    abstract MoviesActivity moviesActivity();

}
