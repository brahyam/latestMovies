package com.example.brahyam.moviealert.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.brahyam.moviealert.R;
import com.example.brahyam.moviealert.data.Movie;
import com.example.brahyam.moviealert.di.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class MoviesFragment extends DaggerFragment implements MoviesContract.View {

    private static final String TAG = MoviesFragment.class.getSimpleName();

    @Inject
    MoviesContract.Presenter presenter;

    private RecyclerView recyclerMovies;

    private MoviesAdapter adapter;

    private ProgressBar progressBarMovies;

    private boolean isLoading;

    private boolean isLastPage = false;

    private int currentPage = 1;

    private int totalPages = 0;

    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    private EndlessRecyclerViewScrollListener paginationListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            Log.d(TAG, "RecyclerView requested load page:" + page);
            if (!isLoading) {
                int newPage = page + 1;
                presenter.loadMovies(false, newPage);
            }
        }
    };

    /**
     * Handles clicks on the movies
     */
    private MoviesAdapter.MovieListener listener = new MoviesAdapter.MovieListener() {
        @Override
        public void onItemClickListener(Movie movie) {
            presenter.openMovieDetails(movie);
        }
    };

    @Inject
    public MoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MoviesAdapter(new ArrayList<Movie>(), listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        isLoading = true; // taking the view causes the presenter to load the movies
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movies_fragment, container, false);
        recyclerMovies = root.findViewById(R.id.recyclerMovies);
        recyclerMovies.setHasFixedSize(true);
        recyclerMovies.setLayoutManager(linearLayoutManager);
        recyclerMovies.addOnScrollListener(paginationListener);
        recyclerMovies.setAdapter(adapter);
        progressBarMovies = root.findViewById(R.id.progressBarMovies);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            progressBarMovies.setVisibility(View.VISIBLE);
        } else {
            progressBarMovies.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMovies(List<Movie> movies, Integer pages) {
        totalPages = pages;
        adapter.replaceData(movies);
        setLoadingIndicator(false);
        isLoading = false;
        if (currentPage == totalPages) {
            // TODO: add loading item to list
            isLastPage = true;
        }
    }

    @Override
    public void showMoviesNextPage(List<Movie> movies) {
        adapter.replaceData(movies);
        setLoadingIndicator(false);
        isLoading = false;
        currentPage++;
    }

    @Override
    public void showMovieDetailsUI(Integer movieId) {
        // TODO: Implement details screen
        Toast.makeText(getContext(), getString(R.string.movie_selected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingMovieError() {
        Toast.makeText(getContext(), getString(R.string.movie_list_load_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
