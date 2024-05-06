package com.tryouts.finalyearproject.di

import com.tryouts.finalyearproject.common.Constants
import com.tryouts.finalyearproject.data.ApiCalls
import com.tryouts.finalyearproject.data.repo.MainRepository
import com.tryouts.finalyearproject.data.repo.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): ApiCalls {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiCalls::class.java)
    }

    @Provides
    @Singleton
    fun provideMainRepository(api: ApiCalls): MainRepository {
        return MainRepositoryImpl(api)
    }

}