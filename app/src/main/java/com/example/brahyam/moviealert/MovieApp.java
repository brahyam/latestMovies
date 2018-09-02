package com.example.brahyam.moviealert;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class MovieApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build;
    }
}
