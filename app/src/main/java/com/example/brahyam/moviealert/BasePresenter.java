package com.example.brahyam.moviealert;

public interface BasePresenter<T> {

    /**
     * Attach view to presenter when initialized
     *
     * @param view
     */
    void takeView(T view);

    /**
     * Remove view from presenter when destroyed
     */
    void dropView();
}
