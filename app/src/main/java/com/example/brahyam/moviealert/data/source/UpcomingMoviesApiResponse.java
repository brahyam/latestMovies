package com.example.brahyam.moviealert.data.source;

import com.example.brahyam.moviealert.data.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingMoviesApiResponse {

    private List<Movie> results;

    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public UpcomingMoviesApiResponse(List<Movie> results, int page, int totalResults, int totalPages) {
        this.results = results;
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
