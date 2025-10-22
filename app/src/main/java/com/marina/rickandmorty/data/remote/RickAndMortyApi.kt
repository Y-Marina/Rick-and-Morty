package com.marina.rickandmorty.data.remote

import com.marina.rickandmorty.data.remote.responses.CharacterDto
import com.marina.rickandmorty.data.remote.responses.CharactersListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacterList(
        @Query("page") page: Int,
        @Query("name") name: String = "",
        @Query("status") status: String = "",
        @Query("species") species: String = ""
    ): CharactersListDto

    @GET("character/{id}")
    suspend fun getCharacterInfo(
        @Path("id") id: Int?
    ): CharacterDto
}
