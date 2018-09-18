package com.example.brahyam.moviealert.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.brahyam.moviealert.data.Movie;
import com.example.brahyam.moviealert.data.source.UpcomingMoviesApiResponse;
import com.example.brahyam.moviealert.data.source.MoviesDataSource;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Singleton
public class MoviesRemoteDataSource implements MoviesDataSource {

    private final static String API_URL = "https://api.themoviedb.org/3/";
    public final static String API_KEY = "97929cc7079a3c3a040c62f8bcd49995";
    private final static String TAG = MoviesRemoteDataSource.class.getSimpleName();
    private MoviesApi moviesApi;

    public MoviesRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesApi = retrofit.create(MoviesApi.class);
    }

    @Override
    public void getMovies(@NonNull Integer page, @NonNull final LoadMoviesCallback callback) {
        Call<UpcomingMoviesApiResponse> call = moviesApi.getUpcomingMovies(API_KEY, page);
        call.enqueue(new Callback<UpcomingMoviesApiResponse>() {
            @Override
            public void onResponse(Call<UpcomingMoviesApiResponse> call, Response<UpcomingMoviesApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onMoviesLoaded(response.body().getResults(), response.body().getPage(), response.body().getTotalPages());
                } else {
                    Log.e(TAG, "response unsuccessful or with empty body ");
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<UpcomingMoviesApiResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMovie(@NonNull Integer id, @NonNull final LoadMovieCallback callback) {
        Call<Movie> call = moviesApi.getMovie(API_KEY, id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onMovieLoaded(response.body());
                } else {
                    Log.e(TAG, "response unsuccessful or with empty body ");
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveMovie(@NonNull Movie movie) {
        // Not needed here, only saved locally
        throw new UnsupportedOperationException();
    }

    @Override
    public void refreshMovies() {
        // Not needed here, handled in MoviesRepository
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllMovies() {
        // Not needed here, handled only locally
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteMovie(@NonNull Integer id) {
        // Not needed here, handled in MoviesRepository
        throw new UnsupportedOperationException();
    }

    public interface MoviesApi {
        @GET("movie/upcoming")
        Call<UpcomingMoviesApiResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

        @GET("movie/{id}")
        Call<Movie> getMovie(@Query("api_key") String apiKey, @Query("id") Integer id);
    }
}