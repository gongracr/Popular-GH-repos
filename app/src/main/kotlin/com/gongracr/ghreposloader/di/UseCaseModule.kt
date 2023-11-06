package com.gongracr.ghreposloader.di

import com.gongracr.core.domain.repository.GHProjectsRemoteRepository
import com.gongracr.core.domain.usecases.GetProjectByIdUseCase
import com.gongracr.core.domain.usecases.GetProjectByIdUseCaseImpl
import com.gongracr.core.domain.usecases.HideGHProjectUseCase
import com.gongracr.core.domain.usecases.HideGHProjectUseCaseImpl
import com.gongracr.core.domain.usecases.ObservePopularGHProjectsUseCase
import com.gongracr.core.domain.usecases.ObservePopularRepositoriesUseCaseImpl
import com.gongracr.core.mappers.GHProjectsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun providesObserveGHProjectsUseCase(
        ghProjectsRepository: GHProjectsRemoteRepository,
        projectsMapper: GHProjectsMapper,
    ): ObservePopularGHProjectsUseCase = ObservePopularRepositoriesUseCaseImpl(
        repository = ghProjectsRepository,
        projectsMapper = projectsMapper
    )

    @ViewModelScoped
    @Provides
    fun providesHideProjectsUseCase(
        ghProjectsRepository: GHProjectsRemoteRepository
    ): HideGHProjectUseCase = HideGHProjectUseCaseImpl(ghProjectsRepository)

    @ViewModelScoped
    @Provides
    fun providesGetProjectByIdUseCase(
        ghProjectsRepository: GHProjectsRemoteRepository,
        projectsMapper: GHProjectsMapper
    ): GetProjectByIdUseCase = GetProjectByIdUseCaseImpl(ghProjectsRepository, projectsMapper)
}