package com.lmh.minhhoang.movieapp.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}