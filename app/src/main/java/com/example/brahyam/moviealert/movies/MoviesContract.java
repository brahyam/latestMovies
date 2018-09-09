package com.example.brahyam.moviealert.movies;

import android.support.annotation.NonNull;

import com.example.brahyam.moviealert.BasePresenter;
import com.example.brahyam.moviealert.BaseView;
import com.example.brahyam.moviealert.data.Movie;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovies(List<Movie> movies, Integer pages);

        void showMoviesNextPage(List<Movie> movies);

        void showMovieDetailsUI(Integer movieId);

        void showLoadingMovieError();

        boolean isActive();

    }

    interface Presenter extends BasePresenter<View> {

        void loadMovies(boolean forceUpdate, Integer page);

        void openMovieDetails(@NonNull Movie movie);
    }
}
