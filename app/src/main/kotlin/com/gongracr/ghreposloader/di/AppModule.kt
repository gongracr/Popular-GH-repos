package com.gongracr.ghreposloader.di

import android.content.Context
import com.gongracr.core.data.GHProjectsPager
import com.gongracr.core.data.GHProjectsRemoteRepositoryImpl
import com.gongracr.core.domain.repository.GHProjectsRemoteRepository
import com.gongracr.core.mappers.GHProjectsMapper
import com.gongracr.core.utility.dispatchers.DefaultDispatcherProvider
import com.gongracr.core.utility.dispatchers.DispatcherProvider
import com.gongracr.persistence.dao.GHProjectsDao
import com.gongracr.persistence.database.AppDatabase
import com.gongracr.ghreposloader.presentation.pager.GHProjectsPagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import network.api.GHRemoteApi
import network.datasource.GHProjectsRemoteDataSource
import network.datasource.GHProjectsRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesApplicationContext(@ApplicationContext appContext: Context) = appContext

    @Singleton
    @Provides
    fun provideDefaultDispatchers(): DispatcherProvider = DefaultDispatcherProvider()

    @Singleton
    @Provides
    fun providesGHProjectsRemoteDataSource(api: GHRemoteApi): GHProjectsRemoteDataSource =
        GHProjectsRemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun providesGHProjectsPager(
        ghProjectsDao: GHProjectsDao,
        ghProjectsRemoteDataSource: GHProjectsRemoteDataSource,
        database: AppDatabase,
        ghProjectsMapper: GHProjectsMapper
    ): GHProjectsPager = GHProjectsPagerImpl(
        ghProjectsDao = ghProjectsDao,
        ghProjectsRemoteDataSource = ghProjectsRemoteDataSource,
        database = database,
        projectsMapper = ghProjectsMapper
    )


    @JvmSuppressWildcards
    @Singleton
    @Provides
    fun providesGHProjectsRepository(
        database: AppDatabase,
        pager: GHProjectsPager,
        dispatcherProvider: DispatcherProvider
    ): GHProjectsRemoteRepository = GHProjectsRemoteRepositoryImpl(
        database = database,
        pager = pager,
        ioDispatcher = dispatcherProvider.io()
    )

    @Singleton
    @Provides
    fun providesMapper(): GHProjectsMapper = GHProjectsMapper()

    @Singleton
    @Provides
    fun providesDispatchers(): Dispatchers = Dispatchers
}