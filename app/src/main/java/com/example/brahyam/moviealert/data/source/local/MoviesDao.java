package com.example.brahyam.moviealert.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.brahyam.moviealert.data.Movie;

import java.util.List;

@Dao
public interface MoviesDao {

    /**
     * Select all movies
     *
     * @return all movies
     */
    @Query("SELECT * FROM Movies")
    List<Movie> getMovies();

    /**
     * Select a movie by id
     *
     * @param movieId the movie id
     * @return movie with movieId if found, null otherwise.
     */
    @Query("SELECT * FROM Movies WHERE id = :movieId")
    Movie getMovieById(Integer movieId);

    /**
     * Store movie in DB (Replace if exist previously)
     *
     * @param movie the movie to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    /**
     * Updates movie in DB
     *
     * @param movie the movie to be updated
     */
    @Update
    void updateMovie(Movie movie);

    /**
     * Deletes a movie by id
     *
     * @param movieId the movie id to be deleted
     * @return number of movies deleted. 0 if not found / 1 if found.
     */
    @Query("DELETE FROM Movies WHERE id = :movieId")
    int deleteMovieById(Integer movieId);

    /**
     * Deletes all movies
     */
    @Query("DELETE FROM Movies")
    void deleteMovies();
}
