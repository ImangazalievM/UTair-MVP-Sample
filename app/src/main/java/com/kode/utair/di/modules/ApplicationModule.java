package com.kode.utair.di.modules;

import com.kode.utair.di.qualifiers.JobScheduler;
import com.kode.utair.di.qualifiers.UiScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    @JobScheduler
    Scheduler provideJobScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @UiScheduler
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
