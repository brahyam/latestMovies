package com.example.brahyam.moviealert.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

public class ActivityUtils {

    /**
     * Adds a fragment to the provided frameid
     *
     * @param fragmentManager fragment manager to create the transaction
     * @param fragment        fragment to be added
     * @param frameId         container id where to add the fragment
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
