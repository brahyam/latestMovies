package com.example.brahyam.moviealert.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.brahyam.moviealert.data.source.local.MoviesDao;
import com.example.brahyam.moviealert.data.source.local.MoviesDatabase;
import com.example.brahyam.moviealert.data.source.local.MoviesLocalDataSource;
import com.example.brahyam.moviealert.data.source.remote.MoviesRemoteDataSource;
import com.example.brahyam.moviealert.util.AppExecutors;
import com.example.brahyam.moviealert.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    @Local
    MoviesDataSource provideMoviesLocalDataSource(MoviesDao moviesDao, AppExecutors executors) {
        return new MoviesLocalDataSource(moviesDao, executors);
    }

    @Singleton
    @Provides
    @Remote
    MoviesDataSource provideMoviesRemoteDataSource() {
        return new MoviesRemoteDataSource();
    }

    @Singleton
    @Provides
    MoviesDatabase provideDB(Application context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                MoviesDatabase.class, "Movies.db").build();
    }

    @Singleton
    @Provides
    MoviesDao provideMoviesDao(MoviesDatabase database) {
        return database.moviesDao();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(
                new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
