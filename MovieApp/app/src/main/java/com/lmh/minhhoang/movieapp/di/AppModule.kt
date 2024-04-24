package com.lmh.minhhoang.movieapp.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.lmh.minhhoang.movieapp.movieList.data.local.movie.MovieDatabase
import com.lmh.minhhoang.movieapp.movieList.data.remote.MovieApi
import com.lmh.minhhoang.movieapp.movieList.data.reponsitory.AuthRespositoryImpl
import com.lmh.minhhoang.movieapp.movieList.domain.reponsitory.AuthRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMovieApi() : MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviedb.db"
        ).build()
    }
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()
    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRespository{
        return AuthRespositoryImpl(firebaseAuth)
    }
    @Provides
    @Singleton
    fun provideExoPlayer(app:Application):ExoPlayer{
        return ExoPlayer.Builder(app).build()
    }

}