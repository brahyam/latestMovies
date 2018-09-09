package com.example.brahyam.moviealert.util;

import com.example.brahyam.moviealert.data.source.remote.MoviesRemoteDataSource;

public class MovieImageUrlBuilder {

    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w154";
    private static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";

    public static String buildPosterUrl(String path) {
        return POSTER_BASE_URL + path + "?api_key=" + MoviesRemoteDataSource.API_KEY;
    }

    public static String buildBackdropUrl(String path) {
        return BACKDROP_BASE_URL + path + "?api_key=" + MoviesRemoteDataSource.API_KEY;
    }

}
