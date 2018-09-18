package com.example.brahyam.moviealert.data.source.local;

import android.support.annotation.NonNull;

import com.example.brahyam.moviealert.data.Movie;
import com.example.brahyam.moviealert.data.source.MoviesDataSource;
import com.example.brahyam.moviealert.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MoviesLocalDataSource implements MoviesDataSource {

    private final MoviesDao moviesDao;

    private final AppExecutors appExecutors;

    @Inject
    public MoviesLocalDataSource(MoviesDao moviesDao, AppExecutors appExecutors) {
        this.moviesDao = moviesDao;
        this.appExecutors = appExecutors;
    }

    @Override
    public void getMovies(@NonNull Integer page, @NonNull final LoadMoviesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Movie> movies = moviesDao.getMovies();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (movies.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onMoviesLoaded(movies, 1, 1);
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getMovie(@NonNull final Integer id, @NonNull final LoadMovieCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Movie movie = moviesDao.getMovieById(id);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (movie == null) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onMovieLoaded(movie);
                        }
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveMovie(@NonNull final Movie movie) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.insertMovie(movie);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void refreshMovies() {
        // Not needed here, handled in MoviesRepository
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllMovies() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovies();
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteMovie(@NonNull final Integer id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                moviesDao.deleteMovieById(id);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }
}
