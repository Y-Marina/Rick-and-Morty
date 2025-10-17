package com.marina.rickandmorty.repository

import com.marina.rickandmorty.R
import com.marina.rickandmorty.data.remote.RickAndMortyApi
import com.marina.rickandmorty.data.remote.responses.CharactersListDto
import com.marina.rickandmorty.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi
) {
    suspend fun getCharacterList(page: Int): Resource<CharactersListDto> {
        val response = try {
            api.getCharacterList(page)
        } catch (e: Exception) {
            return Resource.Error(R.string.error_message.toString())
        }
        return Resource.Success(response)
    }
}