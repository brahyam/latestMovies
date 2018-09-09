package com.example.brahyam.moviealert.di;

import android.app.Application;

import com.example.brahyam.moviealert.MovieApp;
import com.example.brahyam.moviealert.data.source.MoviesRepository;
import com.example.brahyam.moviealert.data.source.MoviesRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        MoviesRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class

})
public interface AppComponent extends AndroidInjector<MovieApp> {

    MoviesRepository getMoviesRepository();

    @Component.Builder
    interface Builder {
        /**
         * Allows doing DaggerAppComponent.builder().application(this).build().inject(this) without
         * specifying modules
         */
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
