package com.syakirarif.wings.di

import android.content.Context
import com.syakirarif.wings.database.AppDatabase
import com.syakirarif.wings.database.AppDatabaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCacheStorage(@ApplicationContext context: Context): AppDatabase {
        return AppDatabaseImpl(context)
    }
}