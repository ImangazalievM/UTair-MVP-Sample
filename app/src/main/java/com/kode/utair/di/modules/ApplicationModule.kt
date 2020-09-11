package com.kode.utair.di.modules

import com.kode.utair.di.qualifiers.JobScheduler
import com.kode.utair.di.qualifiers.UiScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @JobScheduler
    fun provideJobScheduler(): Scheduler {
        return Schedulers.computation()
    }

    @Provides
    @Singleton
    @UiScheduler
    fun provideUiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}