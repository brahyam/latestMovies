package com.example.brahyam.moviealert.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.brahyam.moviealert.data.Movie;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MoviesDao moviesDao();
}
