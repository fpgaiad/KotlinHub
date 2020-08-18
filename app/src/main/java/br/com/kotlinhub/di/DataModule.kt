package br.com.kotlinhub.di

import br.com.kotlinhub.repository.GitRepository
import br.com.kotlinhub.repository.GitRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindGitRepositoryImpl(
        repository: GitRepositoryImpl
    ): GitRepository
}