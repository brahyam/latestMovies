package com.example.brahyam.moviealert.data.source;

import android.support.annotation.NonNull;

import com.example.brahyam.moviealert.data.Movie;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MoviesRepository implements MoviesDataSource {

    private final MoviesDataSource moviesRemoteDataSource;
    private final MoviesDataSource moviesLocalDataSource;

    private int currentPage = -1;
    Map<Integer, Movie> cachedMovies;

    boolean cacheIsDirty = false;

    @Inject
    public MoviesRepository(@Remote MoviesDataSource moviesRemoteDataSource, @Local MoviesDataSource moviesLocalDataSource) {
        this.moviesRemoteDataSource = moviesRemoteDataSource;
        this.moviesLocalDataSource = moviesLocalDataSource;
    }

    @Override
    public void getMovies(@NonNull final Integer page, @NonNull final LoadMoviesCallback callback) {
        if (cachedMovies != null && !cacheIsDirty && page <= currentPage) {
            callback.onMoviesLoaded(new ArrayList<>(cachedMovies.values()));
        }

        if (cacheIsDirty) {
            getMoviesFromRemoteDataSource(page, callback);
        } else {
            moviesLocalDataSource.getMovies(page, new LoadMoviesCallback() {
                @Override
                public void onMoviesLoaded(List<Movie> movies) {
                    refreshCache(movies);
                    callback.onMoviesLoaded(movies);
                }

                @Override
                public void onDataNotAvailable() {
                    getMoviesFromRemoteDataSource(page, callback);
                }
            });
        }
    }

    private void getMoviesFromRemoteDataSource(final Integer page, final LoadMoviesCallback callback) {
        moviesRemoteDataSource.getMovies(page, new LoadMoviesCallback() {
            @Override
            public void onMoviesLoaded(List<Movie> movies) {
                refreshCache(movies);
                refreshLocalDataSource(movies);
                currentPage = page;
                callback.onMoviesLoaded(movies);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Movie> movies) {
        if (cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }
        if (cacheIsDirty) {
            cachedMovies.clear();
        }
        for (Movie movie : movies) {
            cachedMovies.put(movie.getId(), movie);
        }
        cacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Movie> movies) {
        if (cacheIsDirty) {
            moviesLocalDataSource.deleteAllMovies();
        }
        for (Movie movie : movies) {
            moviesLocalDataSource.saveMovie(movie);
        }
    }

    @Override
    public void getMovie(@NonNull final Integer id, @NonNull final LoadMovieCallback callback) {
        // Try cache
        if (cachedMovies != null && !cachedMovies.isEmpty()) {
            final Movie movie = cachedMovies.get(id);
            if (movie != null) {
                callback.onMovieLoaded(movie);
                return;
            }
        }

        // Try local data
        moviesLocalDataSource.getMovie(id, new LoadMovieCallback() {
            @Override
            public void onMovieLoaded(Movie movie) {
                if (cachedMovies == null) {
                    cachedMovies = new LinkedHashMap<>();
                }
                cachedMovies.put(movie.getId(), movie);
                callback.onMovieLoaded(movie);
            }

            @Override
            public void onDataNotAvailable() {
                // Try remote
                moviesRemoteDataSource.getMovie(id, new LoadMovieCallback() {
                    @Override
                    public void onMovieLoaded(Movie movie) {
                        if (cachedMovies == null) {
                            cachedMovies = new LinkedHashMap<>();
                        }
                        cachedMovies.put(movie.getId(), movie);
                        callback.onMovieLoaded(movie);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveMovie(@NonNull Movie movie) {
        moviesLocalDataSource.saveMovie(movie);
        if (cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }
        cachedMovies.put(movie.getId(), movie);
    }

    @Override
    public void refreshMovies() {
        cacheIsDirty = true;
    }

    @Override
    public void deleteAllMovies() {
        moviesLocalDataSource.deleteAllMovies();
        if (cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }
        cachedMovies.clear();
    }

    @Override
    public void deleteMovie(@NonNull Integer id) {
        moviesLocalDataSource.deleteMovie(id);
        if (cachedMovies == null) {
            cachedMovies = new LinkedHashMap<>();
        }
        cachedMovies.remove(id);
    }
}
