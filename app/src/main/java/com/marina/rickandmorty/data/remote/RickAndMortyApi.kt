package com.marina.rickandmorty.data.remote

import com.marina.rickandmorty.data.remote.responses.CharactersListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacterList(
        @Query("page") page: Int
    ): CharactersListDto
}