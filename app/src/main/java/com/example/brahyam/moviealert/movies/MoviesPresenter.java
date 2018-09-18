package com.example.brahyam.moviealert.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.brahyam.moviealert.data.Movie;
import com.example.brahyam.moviealert.data.source.MoviesDataSource;
import com.example.brahyam.moviealert.data.source.MoviesRepository;
import com.example.brahyam.moviealert.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class MoviesPresenter implements MoviesContract.Presenter {

    private static final Integer DEFAULT_PAGE = 1;
    private static final String TAG = MoviesPresenter.class.getSimpleName();
    private final MoviesRepository moviesRepository;

    @Nullable
    private MoviesContract.View moviesView;

    private boolean firstLoad = true;

    @Inject
    public MoviesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void loadMovies(boolean forceUpdate, final Integer page) {
        Log.d(TAG, "Loading movies page:" + page);
        if (moviesView != null) {
            moviesView.setLoadingIndicator(true);
        }

        if (forceUpdate || firstLoad) {
            moviesRepository.refreshMovies();
        }

        moviesRepository.getMovies(page, new MoviesDataSource.LoadMoviesCallback() {

            @Override
            public void onMoviesLoaded(List<Movie> movies, int loadedPage, int totalPages) {
                if (moviesView == null || !moviesView.isActive()) {
                    return;
                }
                moviesView.setLoadingIndicator(false);
                // TODO: Handle empty movie list
                if (firstLoad) {
                    moviesView.showMovies(movies, totalPages);
                    firstLoad = false;
                } else {
                    moviesView.showMoviesNextPage(movies);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (moviesView == null || !moviesView.isActive()) {
                    return;
                }
                moviesView.showLoadingMovieError();
            }
        });
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {
        if (this.moviesView != null) {
            moviesView.showMovieDetailsUI(movie.getId());
        }
    }

    @Override
    public void takeView(MoviesContract.View view) {
        this.moviesView = view;
        loadMovies(false, DEFAULT_PAGE);
    }

    @Override
    public void dropView() {
        this.moviesView = null;
    }
}
