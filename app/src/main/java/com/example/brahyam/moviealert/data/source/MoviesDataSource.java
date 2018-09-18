package com.example.brahyam.moviealert.data.source;

import android.support.annotation.NonNull;

import com.example.brahyam.moviealert.data.Movie;

import java.util.List;

/**
 * Defines the behavior of a movie data source
 */
public interface MoviesDataSource {

    interface LoadMoviesCallback {
        void onMoviesLoaded(List<Movie> movies, int loadedPage, int totalPages);

        void onDataNotAvailable();
    }

    interface LoadMovieCallback {
        void onMovieLoaded(Movie movie);

        void onDataNotAvailable();
    }

    void getMovies(@NonNull Integer page, @NonNull LoadMoviesCallback callback);

    void getMovie(@NonNull Integer id, @NonNull LoadMovieCallback callback);

    void saveMovie(@NonNull Movie movie);

    void refreshMovies();

    void deleteAllMovies();

    void deleteMovie(@NonNull Integer id);

}
