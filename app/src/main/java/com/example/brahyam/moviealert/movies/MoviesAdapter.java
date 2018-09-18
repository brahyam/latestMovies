package com.example.brahyam.moviealert.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brahyam.moviealert.R;
import com.example.brahyam.moviealert.data.Movie;
import com.example.brahyam.moviealert.util.MovieImageUrlBuilder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> movies;
    MovieListener movieListener;

    public MoviesAdapter(List<Movie> movies, MovieListener movieListener) {
        this.movies = movies;
        this.movieListener = movieListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movies_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        Picasso.get()
                .load(MovieImageUrlBuilder.buildPosterUrl(movie.getPosterPath()))
                .placeholder(R.drawable.poster_placeholder)
                .into(holder.imgPoster);
        holder.txtTitle.setText(movie.getTitle());
        holder.txtReleaseDate.setText(
                new SimpleDateFormat(
                        "dd-MM-yyyy",
                        Locale.getDefault())
                        .format(movie.getReleaseDate()));

        if (movieListener != null) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieListener.onItemClickListener(movie);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void replaceData(List<Movie> movies) {
        if (this.movies == null) {
            this.movies = new ArrayList<>();
        }
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ImageView imgPoster;
        TextView txtTitle;
        TextView txtGenres;
        TextView txtReleaseDate;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtGenres = itemView.findViewById(R.id.txtGenres);
            txtReleaseDate = itemView.findViewById(R.id.txtReleaseDate);
        }
    }

    public interface MovieListener {
        void onItemClickListener(Movie movie);
    }
}
