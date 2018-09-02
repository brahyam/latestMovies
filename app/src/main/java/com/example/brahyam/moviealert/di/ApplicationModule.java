package com.example.brahyam.moviealert.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {

    /**
     * Expose application for injecting
     */
    @Binds
    abstract Context bindContext(Application application);

}
