package com.lmh.minhhoang.movieapp.di

import com.lmh.minhhoang.movieapp.movieList.data.reponsitory.MovieListReponsitoryImpl
import com.lmh.minhhoang.movieapp.movieList.domain.reponsitory.MovieListReponsitory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRespository(
        movieListReponsitoryImpl: MovieListReponsitoryImpl
    ):MovieListReponsitory
}