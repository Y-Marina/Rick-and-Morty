package com.marina.rickandmorty.di

import com.marina.rickandmorty.data.remote.RickAndMortyApi
import com.marina.rickandmorty.repository.CharacterRepository
import com.marina.rickandmorty.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideCharacterRepository(
        api: RickAndMortyApi
    ) = CharacterRepository(api)

    @Singleton
    @Provides
    fun provideCharacterApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RickAndMortyApi::class.java)
    }
}